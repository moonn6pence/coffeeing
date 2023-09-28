from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta
from ..database.dataloader import DataLoader
from ..database.model import Model
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity
import os
currentPath = os.getcwd()

async def dbConnectionSample(db: Session):
    model = Model()
    loader = DataLoader(db)

    return loader.load_all_data(model["Member"])

async def SurveyRecommendBeans(roast, acidity, body, flavorNote, isCapsule, machineType):
	# 일단 csv로 진행
	data = pd.read_csv(currentPath+'\\app\\recommend\\beans_utf-8_230920.csv', encoding='utf-8')
	
	# 필요한 열만 선택 
	selected_data = data[['name', 'roast', 'acid', 'body', 'new_keyword']]
	# selected_data = data[['name', 'roast', 'acid', 'body', 'new_keyword', 'machine']]

	# 캡슐 일 때
	if isCapsule:
		selected_data = selected_data[selected_data['machine']==machineType]
  
	# 사용자 입력 값
	input_roast = roast
	input_acid = acidity
	input_body = body
	input_flavorNote= flavorNote

  # 입력값을 데이터 프레임으로 변환
	input_data = pd.DataFrame({'roast': [input_roast], 'acid': [input_acid], 'body': [input_body]})
	
	# 코사인 유사도
	cosine_sim = cosine_similarity(input_data[['roast', 'acid', 'body']].values, selected_data[['roast', 'acid', 'body']].values)
	selected_data['cosine_sim']=cosine_sim[0]

	# 자카드 유사도 계산
	jaccard_sim = []
	input_set = set(input_flavorNote.split(','))

	for i in range(selected_data.shape[0]):
		# new_keyword 이름 추후 수정 필요
		set1 = set(selected_data.iloc[i]['new_keyword'].split(', '))
		jaccard_similarity = len(set1.intersection(input_set)) / len(set1.union(input_set))
		jaccard_sim.append(jaccard_similarity)
		selected_data.loc[:, 'jaccard_sim'] = jaccard_sim
	# print(selected_data['jaccard_sim'], selected_data['cosine_sim'])

	# 가중치 설정 (임의로 지정)
	weight_cosine = 0.7  # 코사인 유사도의 가중치
	weight_jaccard = 0.3  # 자카드 유사도의 가중치

	# 유사도를 결합
	combined_sim = (weight_cosine * selected_data['cosine_sim'].values + weight_jaccard * selected_data['jaccard_sim'].values)

	# 유사도가 높은 행을 추출
	similar_indices = combined_sim.argsort()[::-1]  
	top_n = 4 
	top_rows = selected_data.iloc[similar_indices[:top_n]]

	# 추천 결과 출력 --> 추후에 보낼 내용 수정 -> product id?
	recommended_indices = top_rows.index.tolist()  
	recommended_beans = selected_data.iloc[recommended_indices][['name', 'roast', 'acid', 'body', 'new_keyword']].to_dict(orient='records')
	
	return recommended_beans