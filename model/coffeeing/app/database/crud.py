from sqlalchemy.orm import Session
from sqlalchemy.orm.decl_api import DeclarativeMeta
from sqlalchemy import func
from sqlalchemy.sql import text
from .model import Model

def get_all(db: Session, model: DeclarativeMeta, skip: int = 0, limit: int = 3000):
    return db.query(model).offset(skip).limit(limit).all()

def get_by_machine_type(db: Session, model: DeclarativeMeta, machine_type: int):
    return db.query(model).filter(model.machine_type == machine_type).all()

def get_by_id(db: Session, model: DeclarativeMeta, id_column_name:str, id: int):
    column_attr = getattr(model, id_column_name, None)
    filter_condition = (column_attr == id)
    return db.query(model).filter(filter_condition).first()


def get_by_criteria_range(db: Session, model: DeclarativeMeta, criteria: str, count:int, row: float, high: float):
    column_attr = getattr(model, criteria, None)
    filter_condition = (column_attr >= row) & (column_attr < high)
    return db.query(model).filter(filter_condition).order_by(func.random()).limit(count).all()

def get_member_capsule_matrix(db: Session):       
    capsule_query = """
        SELECT m.member_id, c.capsule_id AS product_id, COALESCE(cr.score, 0) AS score
        FROM member m
        CROSS JOIN capsule c
        LEFT JOIN capsule_review cr ON c.capsule_id = cr.capsule_id AND m.member_id = cr.member_id;
    """

    return db.execute(text(capsule_query))

def get_member_coffee_matrix(db: Session):
    coffee_query = """
        SELECT m.member_id, c.coffee_id AS product_id, COALESCE(cr.score, 0) AS score
        FROM member m
        CROSS JOIN coffee c
        LEFT JOIN coffee_review cr ON c.coffee_id = cr.coffee_id AND m.member_id = cr.member_id;
    """

    return db.execute(text(coffee_query))