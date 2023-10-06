from fastapi import APIRouter

health = APIRouter(prefix='/health')

@health.get('/', tags=["헬스 체크"])
async def health_check():
    return "ok"