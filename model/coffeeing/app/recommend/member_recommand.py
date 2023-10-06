from sqlalchemy.orm import Session
from ..database.dataloader import DataLoader
from ..database.model import Model
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
import random
from surprise import SVD, Reader, Dataset

def RecommandBySVD(count: int, is_capsule: bool, member_id: int, db: Session):
    loader = DataLoader(db)
    member_product_matrix = None
    if is_capsule:
        member_product_matrix = loader.load_member_capsule_matrix()
    else:
        member_product_matrix = loader.load_member_coffee_matrix()

    reader = Reader(rating_scale=(1, 5))
    data = Dataset.load_from_df(member_product_matrix[['member_id', 'product_id', 'score']], reader)
    
    trainset = data.build_full_trainset()
    algo = SVD(n_epochs=20, n_factors=50, random_state=0)
    algo.fit(trainset)

    not_eval_products_id = []
    for info in member_product_matrix.values:
        if info[0] == member_id and info[2] == 0.0:
            not_eval_products_id.append(int(info[1]))

    predictions = [algo.predict(str(member_id), str(product_id)) for product_id in not_eval_products_id]
    predictions.sort(key=_sortkey_est, reverse=True)
    top_predictions = predictions[:count]

    result = [int(pred.iid) for pred in top_predictions]
    return result

def _sortkey_est(pred):
    return pred.est

def RecommendByCriteria(count: int, is_capsule: bool, criteria: str, attribute:str, db: Session):
    model = Model()
    loader = DataLoader(db)

    model_name = 'Capsule' if is_capsule else 'Coffee'
    low = None
    high = None

    if(attribute == 'low'): 
        low = 0
        high = 0.4 + (random.random() * 0.1)
    else:
        low = 0.6 - (random.random() * 0.1)
        high = 1.1

    datas = loader.load_data_by_criteria(model[model_name], criteria, count, low, high)
    col_names = list(datas.columns)
    col_names[0] = 'id'
    datas.columns = col_names

    datas = datas.to_dict(orient='records')
    result = []
    for data in datas:
        result.append(data['id'])

    return result

def RecommendByProductId(count: int, is_capsule: bool, id: int, db: Session):
    model = Model()
    loader = DataLoader(db)

    model_name = 'Capsule' if is_capsule else 'Coffee'
    data = loader.load_all_data(model[model_name])
    col_names = list(data.columns)
    col_names[0] = 'id'
    data.columns = col_names

    # target: 선택된 캡슐 또는 원두
    target = data.loc[(data.id == id), :]
    if len(target) != 1:
        return []
    
    # other_products: 선택된 캡슐, 원두를 제외한 다른 상품 목록
    # 캡슐인 경우 머신 타입이 다른 원두는 추천되지 않는다.
    other_products = None
    if is_capsule:
        other_products = data.loc[(data.id != id)&(data.machine_type != target['machine_type'].values[0]), :]
    else:
        other_products = data.loc[(data.id != id), :]

    other_products.reset_index(inplace=True)
    roast = target['roast'].values[0]
    acidity = target['acidity'].values[0]
    body = target['body'].values[0]
    flavor_note = target['flavor_note'].values[0].replace(' ', '')
    return _get_similar_data(count, roast, acidity, body, flavor_note, other_products)

def ProductSimilarityRecommend(count, roast, acidity, body, flavor_note, is_capsule, machine_type, db: Session):
    model = Model()
    loader = DataLoader(db)
    
    data = None
    if is_capsule: 
        data = loader.load_data_by_machine_type(model["Capsule"], machine_type)[['capsule_id','capsule_name_eng', 'roast', 'acidity', 'body', 'flavor_note', 'machine_type']]
        data.rename(columns={'capsule_id': 'id'}, inplace=True)
    else :
        data = loader.load_all_data(model["Coffee"])[['coffee_id', 'coffee_name_eng', 'roast', 'acidity', 'body', 'flavor_note']]
        data.rename(columns={'coffee_id': 'id'}, inplace=True)

    return _get_similar_data(count, roast, acidity, body, flavor_note, data)

def _get_similar_data(count, roast, acidity, body, flavor_note, data):
    # 사용자 설문조사 입력 데이터
    input_roast = roast
    input_acid = acidity
    input_body = body
    input_flavor_notes= set(flavor_note.split(','))

    # 가중치 설정 (임의로 지정)
    weight_cosine = 0.7  # 코사인 유사도의 가중치
    weight_jaccard = 0.3  # 자카드 유사도의 가중치

    # '잘 모르겠어요' 처리 (가중치 수정 + 평균값으로 값 처리)
    if roast==0 or body==0:
        weight_cosine=0.3
        weight_jaccard=0.7
        
    if roast == 0:
        roast_avg = data['roast'].mean()
        roast = roast_avg
    
    if body == 0:
        body_avg = data['body'].mean()
        body = body_avg

    # 입력값을 데이터 프레임으로 변환
    input_data = pd.DataFrame({'roast': [input_roast], 'acidity': [input_acid], 'body': [input_body]})

    # 코사인 유사도
    cosine_sim = cosine_similarity(input_data[['roast', 'acidity', 'body']].values, data[['roast', 'acidity', 'body']].values)
    data['cosine_sim']=cosine_sim[0]
    # 자카드 유사도 계산
    jaccard_sim = []
    for i in range(data.shape[0]):
        product_flaver_notes = set(data.iloc[i]['flavor_note'].split(', '))
        jaccard_similarity = len(input_flavor_notes) / len(product_flaver_notes.union(input_flavor_notes))
        jaccard_sim.append(jaccard_similarity)
    
    data.loc[:, 'jaccard_sim'] = jaccard_sim


    # 유사도를 결합
    combined_sim = (weight_cosine * data['cosine_sim'].values + weight_jaccard * data['jaccard_sim'].values)

    # # 유사도가 높은 행을 추출
    similar_indices = combined_sim.argsort()[::-1]  
    top_n = count
    top_rows = data.iloc[similar_indices[:top_n]]

    ## 유사도가 가장 높은 top_n개의 product_id 리턴
    recommended_indices = top_rows.index.tolist()  
    recommended_products = data.iloc[recommended_indices].to_dict(orient='records')

    result = []
    for recommended_product in recommended_products:
        result.append(recommended_product['id'])

    return result