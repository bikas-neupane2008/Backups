weight = {"pencil": 10, "pen": 20, "paper": 4, "eraser": 80}  #TODO: Initialise weight with correct values
available = {"pen": 3, "pencil": 5, "eraser": 2, "paper": 10}  # TODO: Initialise available with correct values

overall_weight = 0
#  TODO: Loop through all available items to determine their overall weight
for item in available:
    print (item)
    print (weight[item])
    print (available[item])
    overall_weight += weight[item] * available[item]
print("Overall weight:", overall_weight)