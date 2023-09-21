import sys
import numpy as np
import pandas as pd

from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta

from .crud import get_all

class DataLoader:
    def __init__(self, db: Session):
        self.db = db

    def load_all_data(self, model: DeclarativeMeta):
        db_items = get_all(self.db, model, limit=sys.maxsize - 1)

        if not db_items:
            db_df = pd.DataFrame()
        else:
            db_df = pd.DataFrame(
                data=[user.values() for user in db_items], columns=db_items[0].keys()
            )
            db_df = db_df[list(model.__table__.columns.keys())]

        return db_df