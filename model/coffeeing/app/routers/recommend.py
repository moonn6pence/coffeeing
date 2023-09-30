from fastapi import APIRouter
from fastapi import Depends

from ..recommend.member_recommand import SurveyRecommendBeans
from ..database.connection import get_session

from sqlalchemy.orm import Session

recommend = APIRouter(prefix='/rec')

@recommend.get("/criteria")
def collaborative_filter(isCapsule: bool, machineType: int, roast: float, acidity: float,body: float, flavorNote: str):   
    return {"results":[1,2,3,4]}

@recommend.get("/similar")
def collaborative_filter2(isCapsule: bool, machineType: int, roast: float, acidity: float,body: float, flavorNote: str):   
    return {"results":[1,2,3,4]}

@recommend.get("/preference")
async def content_filter(roast:float, acidity:float, body:float, flavorNote:str,isCapsule: bool, machineType:int, db: Session = Depends(get_session)):
	result = SurveyRecommendBeans(roast, acidity, body, flavorNote, isCapsule, machineType, db)
	return {"results": result}