import sys
import numpy as np
import pandas as pd

from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta

from .crud import get_all
from .crud import get_by_machine_type
from .crud import get_by_id

class DataLoader:
    def __init__(self, db: Session):
        self.db = db

    def load_all_data(self, model: DeclarativeMeta):
        db_items = get_all(self.db, model, limit=sys.maxsize - 1)

        if not db_items:
            db_df = pd.DataFrame()
        else:
            db_df = pd.DataFrame(
                data=[item.values() for item in db_items], columns=db_items[0].keys()
            )
            db_df = db_df[list(model.__table__.columns.keys())]

        return db_df

    def load_data_by_machine_type(self, model: DeclarativeMeta, machine_type: int):
        db_items = get_by_machine_type(self.db, model, machine_type)
        if not db_items:
            db_df = pd.DataFrame()
        else:
            db_df = pd.DataFrame(
                data=[item.values() for item in db_items], columns=db_items[0].keys()
            )
            db_df = db_df[list(model.__table__.columns.keys())]
        return db_df
    
    def load_data_by_pk(self, model: DeclarativeMeta, id_column_name:str, id: int):
        db_item = get_by_id(self.db, model, id_column_name, id)
        if not db_item:
            db_df = pd.DataFrame()
        else:
            db_df = pd.DataFrame(data=[db_item.values()], columns=db_item.keys())
            db_df = db_df[list(model.__table__.columns.keys())]
        return db_df