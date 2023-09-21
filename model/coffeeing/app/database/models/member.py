from sqlalchemy import Column, ForeignKey
from sqlalchemy import BIGINT, Integer, String, DateTime
from sqlalchemy.orm import relationship

from ..connection import Base

class Member(Base):
    def __getitem__(self, key):
        return getattr(self, key)

    def __setitem__(self, key, value):
        return setattr(self, key, value)

    def keys(self):
        return list(self.__dict__.keys())[1:]

    def values(self):
        return list(self.__dict__.values())[1:]

    __tablename__ = "member"

    member_id = Column(BIGINT, primary_key=True, index=True)
    created_at = Column(DateTime())
    modified_at = Column(DateTime())
    email = Column(String(320), nullable=False, unique=True)
    state = Column(String(64), nullable=False)
    nickname = Column(String(16))
    gender = Column(Integer)
    age = Column(Integer)
    profile_image = Column(String(512))
    oauth_identifier = Column(String(512))
    experience = Column(Integer, default=0)
    member_level = Column(Integer, default=0)