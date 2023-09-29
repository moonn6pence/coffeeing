from sqlalchemy import Column, ForeignKey
from sqlalchemy import BIGINT, Integer, String, DateTime, Double, Text
from sqlalchemy.orm import relationship

from ..connection import Base

class Capsule(Base):
    def __getitem__(self, key):
        return getattr(self, key)

    def __setitem__(self, key, value):
        return setattr(self, key, value)

    def keys(self):
        return list(self.__dict__.keys())[1:]

    def values(self):
        return list(self.__dict__.values())[1:]

    __tablename__ = "capsule"

    capsule_id = Column(BIGINT, primary_key=True, index=True)
    created_at = Column(DateTime())
    modified_at = Column(DateTime())
    brand_eng = Column(String(255))
    brand_kr = Column(String(255))
    capsule_name_eng = Column(String(255))
    capsule_name_kr = Column(String(255))
    acidity = Column(Double)
    body = Column(Double)
    roast = Column(Double)
    flavor_note = Column(String(255))
    machine_type = Column(Integer)
    image_url = Column(Text)
    product_description = Column(Text)
    total_reviewer = Column(Integer)
    total_score = Column(Integer)
    popularity = Column(Integer)