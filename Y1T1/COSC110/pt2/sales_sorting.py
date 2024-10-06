#!/usr/bin/env python3
def read_sales_data(file_name):
    sales_data = {}
    with open(file_name) as f:
        for i, line in enumerate(f, start=1):
            items = line.strip().split(',')
            if len(items) != 4:
                raise ValueError(f"Error in line number {i}. Invalid line format: {line.strip()}")
            movie_name, adult_tickets, child_tickets, concession_tickets = items
            try:
                adult_tickets = int(adult_tickets)
                if adult_tickets < 0:
                    raise ValueError(f"Negative number of ticket count in line {i} for adult tickets in line: {line.strip()}")
            except ValueError:
                raise ValueError(f"Invalid ticket count for adult tickets in line {i}: {line.strip()}")
            try:
                child_tickets = int(child_tickets)
                if child_tickets < 0:
                    raise ValueError(f"Negative number of ticket count in line {i} for child tickets in line: {line.strip()}")
            except ValueError:
                raise ValueError(f"Invalid ticket count for child tickets in line {i}: {line.strip()}")
            try:
                concession_tickets = int(concession_tickets)
                if concession_tickets < 0:
                    raise ValueError(f"Negative number of ticket count in line {i} for concession tickets in line: {line.strip()}")
            except ValueError:
                raise ValueError(f"Invalid ticket count for concession tickets in line {i}: {line.strip()}")
            transaction_amount = adult_tickets * adult_price + child_tickets * child_price + concession_tickets * concession_price
            if movie_name in sales_data:
                sales_data[movie_name] += transaction_amount
            else:
                sales_data[movie_name] = transaction_amount
    return sales_data

def selection_sort(items):
    for i in range(len(items)):
        min_value = i
        for j in range(i + 1, len(items)):
            if items[j][1] > items[min_value][1]:
                min_value = j
        items[i], items[min_value] = items[min_value], items[i]
    return items

adult_price = 20.0
child_price = 15.0
concession_price = 17.5

try:
    sales_data = read_sales_data('transactions.txt')
    sorted_sales_data = selection_sort(list(sales_data.items()))
    for movie, amount in sorted_sales_data:
        print(f"{movie:30}: ${amount:.2f}")
except Exception as e:
    print(f"Error occurred: {str(e)}")
