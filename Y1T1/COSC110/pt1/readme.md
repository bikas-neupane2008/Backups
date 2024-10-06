Codetown Cinema
This Python script is a simple ticket sales tracker that prompts the user for the number of adult, child, and concession tickets sold, calculates the total sale for the transaction, and keeps track of the total sales and number of tickets sold for each ticket type.

Usage
1. Run the script in a Python environment.
2. It will display "Is there a new ticket sale? (yes/no): "
3. As per the entry,
   1. If you enter "no", you will be re-directed to step 6. 
   2. If you enter any other thing rather than "yes", you will be asked to enter either "yes" or "no" only and re-directed to step 2.
   3. If you enter "yes", the program goes to next step.
   4. It will ask for "Number of adult tickets: "
   5. As per entry,
      1. If you enter any value rather than number, it says "Please enter a integer number for adult tickets." and goes to step 3.4
      2. If you enter any number less than 0, it says "Please enter a positive integer or 0 for adult tickets." and goes to step 3.4
      3. If you enter any number >= 0, than it saves the value of adult tickets.
   6. It will ask for "Number of child tickets: "
   7. As per entry,
      1. If you enter any value rather than number, it says "Please enter a integer number for child tickets." and goes to step 3.6
      2. If you enter any number less than 0, it says "Please enter a positive integer or 0 for child tickets." and goes to step 3.6
      3. If you enter any number >= 0, than it saves the value of child tickets.
   8. It will ask for "Number of concession tickets: "
   9. As per entry,
      1. If you enter any value rather than number, it says "Please enter a integer number for concession tickets." and goes to step 3.8
      2. If you enter any number less than 0, it says "Please enter a positive integer or 0 for concession tickets." and goes to step 3.8
      3. If you enter any number >= 0, than it saves the value of adult tickets. 
   10. If you enter 0 for all three tickets values, you will be displayed a message "There must be at least one ticket in the transaction!" and will be redirected to step 3.4.
4. The script will calculate the total sale for the transaction, update the total sales and number of tickets sold for each ticket type, and display the total due for the transaction.
5. Repeat from step 2 until you are finished entering ticket sales. 
6. The script will display the total sales and number of tickets sold for each ticket type.

Variables used in the program
`adult_price`: the price of an adult ticket (integer)
`child_price`: the price of a child ticket (integer)
`concession_price`: the price of a concession ticket (float)
`total_sales`: the total sales (float) formatted to two decimal places
`total_adult_tickets`: the total number of adult tickets sold (integer)
`total_child_tickets`: the total number of child tickets sold (integer)
`total_concession_tickets`: the total number of concession tickets sold (integer)
`adult_tickets` : the number of adult tickets (integer)
`child_tickets` : the number of child tickets (integer)
`concession_tickets` : the number of concession tickets (integer)


Example 1
```
Is there a new ticket sale? (yes/no): yes
Number of adult tickets: 1
Number of child tickets: 1
Number of concession tickets: 1
Total due for transaction: $52.50
Is there a new ticket sale? (yes/no): no
Total sales: $52.50
Adult tickets sold: 1
Child tickets sold: 1
Concession tickets sold: 1
```

Example 2
```
Is there a new ticket sale? (yes/no): yes
Number of adult tickets: 1
Number of child tickets: 0
Number of concession tickets: 0
Total due for transaction: $20.00
Is there a new ticket sale? (yes/no): yes
Number of adult tickets: 1
Number of child tickets: 1
Number of concession tickets: 0
Total due for transaction: $35.00
Is there a new ticket sale? (yes/no): yes
Number of adult tickets: 0
Number of child tickets: 0
Number of concession tickets: 1
Total due for transaction: $17.50
Is there a new ticket sale? (yes/no): no
Total sales: $72.50
Adult tickets sold: 2
Child tickets sold: 1
Concession tickets sold: 1
```
Example 3
```
Is there a new ticket sale? (yes/no): maybe
Please enter "yes" or "no"
Is there a new ticket sale? (yes/no): n
Please enter "yes" or "no"
Is there a new ticket sale? (yes/no): no
Total sales: $0.00
Adult tickets sold: 0
Child tickets sold: 0
Concession tickets sold: 0
```
Example 4
```
Is there a new ticket sale? (yes/no): y
Please enter "yes" or "no"
Is there a new ticket sale? (yes/no): yes
Number of adult tickets: None
Please enter a number for adult tickets.
Number of adult tickets: 
Please enter a number for adult tickets.
Number of adult tickets: -1
Please enter a positive integer or 0 for adult tickets.
Number of adult tickets: 0
Number of child tickets: None
Please enter a number for child tickets.
Number of child tickets: 
Please enter a number for child tickets.
Number of child tickets: -1
Please enter a positive integer or 0 for child tickets.
Number of child tickets: 0
Number of concession tickets: None
Please enter a number for concession tickets.
Number of concession tickets: 
Please enter a number for concession tickets.
Number of concession tickets: -1
Please enter a positive integer or 0 for concession tickets.
Number of concession tickets: 0
There must be at least one ticket in the transaction!
Number of adult tickets: None
Please enter a number for adult tickets.
Number of adult tickets: 
Please enter a number for adult tickets.
Number of adult tickets: -1
Please enter a positive integer or 0 for adult tickets.
Number of adult tickets: 0
Number of child tickets: 
Please enter a number for child tickets.
Number of child tickets: 0
Number of concession tickets: -1
Please enter a positive integer or 0 for concession tickets.
Number of concession tickets: 1
Total due for transaction: $17.50
Is there a new ticket sale? (yes/no): n
Please enter "yes" or "no"
Is there a new ticket sale? (yes/no): no
Total sales: $17.50
Adult tickets sold: 0
Child tickets sold: 0
Concession tickets sold: 1
```