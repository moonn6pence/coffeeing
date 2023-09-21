from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker

from ..constants.constants import DB_URL, DB_SCHMEA, DB_USER, DB_PWD

__DB_CONNECTION_INFO = f'mysql+pymysql://{DB_USER}:{DB_PWD}@{DB_URL}/{DB_SCHMEA}'

engine = create_engine(__DB_CONNECTION_INFO)

__SessionLocal = sessionmaker(bind=engine)

Base = declarative_base()

Base.metadata.bind = engine

def get_session():
    db = __SessionLocal()
    try:
        yield db
    finally:
        db.close()