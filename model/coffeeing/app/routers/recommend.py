from fastapi import APIRouter
from fastapi import Depends

from ..recommend.member_recommand import ProductSimilarityRecommend
from ..recommend.member_recommand import RecommendByProductId
from ..database.connection import get_session

from sqlalchemy.orm import Session

recommend = APIRouter(prefix='/rec')

#  원두 / 캡슐의 특성을 기준에 따라 추천한다. 
#  (1) 특성의 종류(criteria): roast, body, acidity (로스팅, 바디감, 산도)
#  (2) 추천 기준(attribute) : low, high
#    2-1 : low
#    2-2 : high
@recommend.get("/criteria")
async def item_characteristics_base_recommand(count: int, isCapsule: bool, criteria: str, attribute:str, db: Session = Depends(get_session)):   
    return {"results":[1,2,3,4]}

# 원두, 캡슐의 id가 주어진 경우 해당 아이템과 유사한 상품을 추천한다.
@recommend.get("/similar")
async def suvey_base_recommand(count: int, isCapsule: bool, id: int, db: Session = Depends(get_session)):
    result = RecommendByProductId(count, isCapsule, id, db)
    return {"results": result}

#  사용자 설문 결과를 기반으로 다음의 두 가지 유사도를 이용하여 상품을 추천한다.
#  (1) 설문 결과로 나온 원두의 특성(roast, body, acidity)와 상품들의 코사인 유사도
#  (2) 설문 결과로 나온 flaver_note와 상품들의 자카드 유사도 
@recommend.get("/preference")
async def suvey_base_recommand(count: int, isCapsule: bool, machineType:int, roast:float, acidity:float, body:float, flavorNote:str, db: Session = Depends(get_session)):
	result = ProductSimilarityRecommend(count, roast, acidity, body, flavorNote, isCapsule, machineType, db)
	return {"results": result}