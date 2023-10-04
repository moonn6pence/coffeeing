from sqlalchemy import Column
from sqlalchemy import BIGINT, Integer, String, DateTime

from ..connection import Base

class CapsuleReview(Base):
    def __getitem__(self, key):
        return getattr(self, key)

    def __setitem__(self, key, value):
        return setattr(self, key, value)

    def keys(self):
        return list(self.__dict__.keys())[1:]

    def values(self):
        return list(self.__dict__.values())[1:]

    __tablename__ = "capsule_review"

    capsule_review_id = Column(BIGINT, primary_key=True, index=True)
    created_at = Column(DateTime())
    modified_at = Column(DateTime())
    content = Column(String(255))
    score = Column(Integer)
    capsule_id = Column(BIGINT)
    member_id = Column(BIGINT)