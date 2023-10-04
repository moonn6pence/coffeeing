from .models.member import Member
from .models.capsule import Capsule
from .models.coffee import Coffee
from .models.capsule_review import CapsuleReview
from .models.coffee_review import CoffeeReview

class Model:
    def __getitem__(self, key):
        return getattr(self, key)

    def __setitem__(self, key, value):
        return setattr(self, key, value)

    Member = Member
    Capsule = Capsule
    Coffee = Coffee
    CoffeeReview = CoffeeReview
    CapsuleReview = CapsuleReview