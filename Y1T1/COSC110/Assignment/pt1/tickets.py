#!/usr/bin/env python3
def tickets_count(ticket_type):
    while True:
        try:
            count = int(input(f"Number of {ticket_type} tickets: "))
            if count < 0:
                print(f"Please enter a positive integer or 0 for {ticket_type} tickets.")
            else:
                return count
        except ValueError:
            print(f"Please enter a integer number for {ticket_type} tickets.")
adult_price = 20
child_price = 15
concession_price = 17.50
total_sales = 0
total_adult_tickets = 0
total_child_tickets = 0
total_concession_tickets = 0
while True:
    new_sale = input("Is there a new ticket sale? (yes/no): ")
    if new_sale.lower() == "no":
        break
    elif new_sale.lower() != "yes":
        print("Please enter \"yes\" or \"no\"")
        continue
    else:
        while True:
                adult_tickets = tickets_count("adult")
                child_tickets = tickets_count("child")
                concession_tickets = tickets_count("concession")
                if adult_tickets == 0 and child_tickets == 0 and concession_tickets == 0:
                    print("There must be at least one ticket in the transaction!")
                    continue
                break
    total_adult_tickets += adult_tickets
    total_child_tickets += child_tickets
    total_concession_tickets += concession_tickets
    sale_total = adult_tickets * adult_price + child_tickets * child_price + concession_tickets * concession_price
    total_sales += sale_total
    print(f"Total due for transaction: ${sale_total:.2f}")
print(f"Total sales: ${total_sales:.2f}")
print(f"Adult tickets sold: {total_adult_tickets}")
print(f"Child tickets sold: {total_child_tickets}")
print(f"Concession tickets sold: {total_concession_tickets}")
