from sqlalchemy import Column, ForeignKey
from sqlalchemy import BIGINT, Integer, String, DateTime, Double, Text
from sqlalchemy.orm import relationship

from ..connection import Base

class Coffee(Base):
    def __getitem__(self, key):
        return getattr(self, key)

    def __setitem__(self, key, value):
        return setattr(self, key, value)

    def keys(self):
        return list(self.__dict__.keys())[1:]

    def values(self):
        return list(self.__dict__.values())[1:]

    __tablename__ = "coffee"

    coffee_id = Column(BIGINT, primary_key=True, index=True)
    created_at = Column(DateTime())
    modified_at = Column(DateTime())
    acidity = Column(Double)
    body = Column(Double)
    roast = Column(Double)
    coffee_name_eng = Column(String(255))
    coffee_name_kr = Column(String(255))
    flavor_note = Column(String(255))
    image_url = Column(Text)
    product_description = Column(Text)
    region_eng = Column(String(255))
    region_kr = Column(String(255))
    total_reviewer = Column(Integer)
    total_score = Column(Integer)
    popularity = Column(Integer)