from fastapi import APIRouter
from ..recommend.member_recommand import SurveyRecommendBeans
recommend = APIRouter(prefix='/rec')

@recommend.get("/collab")
def collaborative_filter(isCapsule: bool, machineType: int, roast: float, acidity: float,body: float, flavorNote: str):   
    return {"results":[1,2,3,4]}

@recommend.post("/survey/recommend")
async def content_filter(roast:float, acidity:float, body:float, flavorNote:str,isCapsule: bool, machineType:int,):
	result = await SurveyRecommendBeans(roast, acidity, body, flavorNote, isCapsule, machineType)
	return result