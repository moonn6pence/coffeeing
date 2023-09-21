from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta

def get_all(db: Session, model: DeclarativeMeta, skip: int = 0, limit: int = 100):
    return db.query(model).offset(skip).limit(limit).all()