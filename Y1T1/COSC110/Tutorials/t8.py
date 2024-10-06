total_cost = 0
cost_of_nail = 1
for horse_foot in range(4):
    for nail in range(6):
        # TODO: Add price of current nail to total_cost and determine the cost of the next nail
        total_cost += cost_of_nail
        cost_of_nail *=2

print("The total cost to shoe the horse would be ${0:.2f}".format(total_cost / 100))