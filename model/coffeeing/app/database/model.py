from .models.member import Member
from .models.capsule import Capsule
from .models.coffee import Coffee

class Model:
    def __getitem__(self, key):
        return getattr(self, key)

    def __setitem__(self, key, value):
        return setattr(self, key, value)

    Member = Member
    Capsule = Capsule
    Coffee = Coffee