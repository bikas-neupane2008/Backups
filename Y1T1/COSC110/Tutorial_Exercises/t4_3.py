available = {"pen": 3, "pencil": 15, "eraser": 2, "paper": 10}  # TODO: Initialise available with correct values
total_items = 0
#  TODO: Loop through all available items to determine their overall weight
for item in available:
    total_items = total_items + available[item]
print("Total items:", total_items)