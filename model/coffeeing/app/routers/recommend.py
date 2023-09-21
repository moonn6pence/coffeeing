from fastapi import APIRouter
from ..recommend.member_recommand import SurveyRecommendBeans
recommend = APIRouter(prefix='/rec')

@recommend.get("/collab")
def collaborative_filter(isCapsule: bool, machineType: int, roast: float, acidity: float,body: float, flavorNote: str):   
    return {"results":[1,2,3,4]}

@recommend.post("/content")
async def content_filter(isCapsule: bool, id: int, roasting:float, acidity:float, body:float, flavorNote:list[str]):
	result = await SurveyRecommendBeans(roasting, acidity, body, flavorNote)
	return result