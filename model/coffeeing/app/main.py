from fastapi import FastAPI
from .routers.health import health
from .routers.recommend import recommend

app = FastAPI()

app.include_router(health)
app.include_router(recommend)

@app.get("/")
async def root():
    return "coffeeing"