#!/usr/bin/env python3
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
        adult_tickets = -1
        child_tickets = -1
        concession_tickets = -1
        while True:
            try:
                while adult_tickets < 0:
                    try:
                        adult_tickets = int(input("Number of adult tickets: "))
                        if adult_tickets < 0:
                            print("Please enter a positive integer or 0 for adult tickets.")
                    except ValueError:
                        print("Please enter a integer number for adult tickets.")
                while child_tickets < 0:
                    try:
                        child_tickets = int(input("Number of child tickets: "))
                        if child_tickets < 0:
                            print("Please enter a positive integer or 0 for child tickets.")
                    except ValueError:
                        print("Please enter a integer number for child tickets.")
                while concession_tickets < 0:
                    try:
                        concession_tickets = int(input("Number of concession tickets: "))
                        if concession_tickets < 0:
                            print("Please enter a positive integer or 0 for concession tickets.")
                    except ValueError:
                        print("Please enter a integer number for concession tickets.")
                if adult_tickets == 0 and child_tickets == 0 and concession_tickets == 0:
                    raise ValueError
                total_adult_tickets += adult_tickets
                total_child_tickets += child_tickets
                total_concession_tickets += concession_tickets
                sale_total = adult_tickets * adult_price + child_tickets * child_price + concession_tickets * concession_price
                total_sales += sale_total
                print(f"Total due for transaction: ${sale_total:.2f}")
                break
            except ValueError:
                print("There must be at least one ticket in the transaction!")
                adult_tickets = -1
                child_tickets = -1
                concession_tickets = -1
                continue
print(f"Total sales: ${total_sales:.2f}")
print(f"Adult tickets sold: {total_adult_tickets}")
print(f"Child tickets sold: {total_child_tickets}")
print(f"Concession tickets sold: {total_concession_tickets}")
