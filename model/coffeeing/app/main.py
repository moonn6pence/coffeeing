from fastapi import FastAPI
from .routers.health import health
from .routers.recommend import recommend
from .recommend.member_recommand import dbConnectionSample
from sqlalchemy.orm import Session
from fastapi import Depends
from .database.connection import get_session

app = FastAPI()

app.include_router(health)
app.include_router(recommend)

@app.get("/")
async def root(db: Session = Depends(get_session)):
    member_sample_query_result = await dbConnectionSample(db)
    print(member_sample_query_result)
    return "coffeeing"