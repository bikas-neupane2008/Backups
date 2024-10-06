def calculate_price(price, order):
    total_cost = 0
    for item, quantity in order.items():
        if item not in price: raise KeyError(f"No price found for item: {item}")
        item_price = price[item]
        total_cost += item_price * quantity
    if total_cost > 100: total_cost *= 0.9
    elif total_cost > 50: total_cost *= 0.95
    return round(total_cost, 2)
price = {'book': 10.0, 'magazine': 5.5, 'newspaper': 2.0}
order1 = {'book': 10}
order2 = {'book': 1, 'magazine': 3}
order3 = {'magazine': 5, 'book': 10}
assert(95 == calculate_price(price, order1))
assert(26.5 == calculate_price(price, order2))
assert(114.75 == calculate_price(price, order3))
print("Done")