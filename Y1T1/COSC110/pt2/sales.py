#!/usr/bin/env python3
# This function reads sales data from a file and returns the total sales for each movie in a dictionary
def read_sales_data(file_name):
    # Initialize an empty dictionary to hold the sales data
    sales_data = {}
    # Open the file in read mode using a context manager
    with open(file_name) as f:
        # Iterate over each line in the file, keeping track of the line number using 'enumerate'
        for i, line in enumerate(f, start=1):
            # Split the line into separate values using the comma delimiter
            items = line.strip().split(',')
            # Raise a ValueError if the line doesn't have exactly four items
            if len(items) != 4:
                raise ValueError(f"Error in line number {i}. Invalid line format: {line.strip()}")
            # Extract the values from the items list
            movie_name, adult_tickets, child_tickets, concession_tickets = items
            # Convert the adult ticket count to an integer and raise a ValueError if it's negative
            try:
                adult_tickets = int(adult_tickets)
                if adult_tickets < 0:
                    raise ValueError(f"Negative number of ticket count in line {i} for adult tickets in line: {line.strip()}")
            except ValueError:
                raise ValueError(f"Invalid ticket count for adult tickets in line {i}: {line.strip()}")
            # Convert the child ticket count to an integer and raise a ValueError if it's negative
            try:
                child_tickets = int(child_tickets)
                if child_tickets < 0:
                    raise ValueError(f"Negative number of ticket count in line {i} for child tickets in line: {line.strip()}")
            except ValueError:
                raise ValueError(f"Invalid ticket count for child tickets in line {i}: {line.strip()}")
            # Convert the concession ticket count to an integer and raise a ValueError if it's negative
            try:
                concession_tickets = int(concession_tickets)
                if concession_tickets < 0:
                    raise ValueError(f"Negative number of ticket count in line {i} for concession tickets in line: {line.strip()}")
            except ValueError:
                raise ValueError(f"Invalid ticket count for concession tickets in line {i}: {line.strip()}")
            # Calculate the total transaction amount for this line
            transaction_amount = adult_tickets * adult_price + child_tickets * child_price + concession_tickets * concession_price
            # Add the transaction amount to the sales data for this movie
            if movie_name in sales_data:
                sales_data[movie_name] += transaction_amount
            else:
                sales_data[movie_name] = transaction_amount
    # Return the sales data dictionary
    return sales_data

# This function reads sales data from the list and returns list after sorting from highest to lowest
def selection_sort(items):
    # Iterate over the indices of the list
    for i in range(len(items)):
        # Assume the current index has the minimum value
        min_value = i
        # Iterate over the remaining elements in the list
        for j in range(i + 1, len(items)):
            # Compare the second element of the current element with the second element of the assumed minimum value
            if items[j][1] > items[min_value][1]:
                # If the current element's second value is greater, update the index of the minimum value
                min_value = j
        # Swap the elements at the current index and the index of the minimum value
        items[i], items[min_value] = items[min_value], items[i]
    # Return the sorted list
    return items

# Set the prices for adult, child, and concession tickets
adult_price = 20.0
child_price = 15.0
concession_price = 17.5
# Try to read the sales data from the file 'transactions.txt'
try:
    # Read the sales data from 'transactions.txt'
    sales_data = read_sales_data('transactions.txt')
    # Sort the sales data using selection sort
    sorted_sales_data = selection_sort(list(sales_data.items()))
    # Iterate over the sorted sales data
    for movie, amount in sorted_sales_data:
        # Print the sorted sales data to the console
        print(f"{movie:30}: ${amount:.2f}")
# If an error occurs, print a message to the console
except Exception as e:
    print(f"Error occurred: {str(e)}")
