from fastapi import APIRouter

recommend = APIRouter(prefix='/rec')

@recommend.get("/collab")
def collaborative_filter(isCapsule: bool, machineType: int, roast: float, acidity: float,body: float, flavorNote: str):   
    return {"results":[1,2,3,4]}

@recommend.get("/content")
def content_filter(isCapsule: bool, id: int):   
    return {"results":[1,2]}