from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta
from ..database.dataloader import DataLoader
from ..database.model import Model
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity

def SurveyRecommendBeans(count, roast, acidity, body, flavor_note, is_capsule, machine_type, db: Session):
    model = Model()
    loader = DataLoader(db)
    
    data = None
    if is_capsule: 
        data = loader.load_data_by_machine_type(model["Capsule"], machine_type)[['capsule_id','capsule_name_eng', 'roast', 'acidity', 'body', 'flavor_note', 'machine_type']]
        data.rename(columns={'capsule_id': 'id'}, inplace=True)
    else :
        data = loader.load_all_data(model["Coffee"])[['coffee_id', 'coffee_name_eng', 'roast', 'acidity', 'body', 'flavor_note']]
        data.rename(columns={'coffee_id': 'id'}, inplace=True)

    # 사용자 설문조사 입력 데이터
    input_roast = roast
    input_acid = acidity
    input_body = body
    input_flavor_notes= set(flavor_note.split(','))

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

    # # 가중치 설정 (임의로 지정)
    weight_cosine = 0.7  # 코사인 유사도의 가중치
    weight_jaccard = 0.3  # 자카드 유사도의 가중치

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
    print(result)
    return result