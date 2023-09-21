from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta

from ..database.dataloader import DataLoader
from ..database.model import Model

async def dbConnectionSample(db: Session):
    model = Model()
    loader = DataLoader(db)

    return loader.load_all_data(model["Member"])