from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta

def get_all(db: Session, model: DeclarativeMeta, skip: int = 0, limit: int = 3000):
    return db.query(model).offset(skip).limit(limit).all()

def get_by_machine_type(db: Session, model: DeclarativeMeta, machine_type: int):
    return db.query(model).filter(model.machine_type == machine_type).all()

def get_by_id(db: Session, model: DeclarativeMeta, id_column_name:str, id: int):
    column_attr = getattr(model, id_column_name, None)
    filter_condition = (column_attr == id)
    return db.query(model).filter(filter_condition).first()