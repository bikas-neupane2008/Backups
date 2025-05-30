class Person:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def __str__(self):
        return f"{self.name} is {self.age} years old."

    def get_name(self):
        return self.name

    def get_age(self):
        return self.age

    def set_name(self, new_name):
        self.name = new_name

    def set_age(self, new_age):
        self.age = new_age

    def is_older_than(self, other):
        return self.age > other.age
alice = Person("Alice", 18)
bob = Person("Bob", 19)
if bob.is_older_than(alice):
    print("{0} is older than {1}".format(bob.get_name(), alice.get_name()))
else:
    print("{0} is older than {1}".format(alice.get_name(), bob.get_name()))
