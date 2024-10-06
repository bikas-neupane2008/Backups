Introduction:<br>
This program reads sales data from a file named 'transactions.txt' and returns the total sales for each movie in a dictionary.
The program calculates the transaction amount for each line and accumulates the sales data for each movie.
Finally, it sorts the sales data by total sales in descending order and prints it to the console.

How to use:<br>
To use this program, follow the instructions below:
1. Install Python 3.x on your computer if it is not already installed.
2. Open the terminal or command prompt on your computer.
3. Navigate to the directory where the 'transactions.txt' file is located.
4. Run the program by typing the following command:
   `python3 sales.py`
5. The program will read the 'transactions.txt' file and print the sorted sales data to the console.

Function Description:<br>
The program consists of one function named 'read_sales_data', which takes a file name as an argument and returns a dictionary containing the sales data for each movie.
The function reads each line of the file, splits it into separate values, and calculates the transaction amount for each line.
It then accumulates the sales data for each movie and returns the resulting dictionary.
If the file format is incorrect, or if the ticket counts are negative or not integers, the function raises a 'ValueError' with a corresponding error message.

Variables:<br>
The program uses the following variables:<br>
`adult_price`: A float variable representing the price of an adult ticket.<br>
`child_price`: A float variable representing the price of a child ticket.<br>
`concession_price`: A float variable representing the price of a concession ticket.<br>
`movie_name`: A String variable representing the name of the movie.<br>
`transaction_amount`: A float variable representing the calculated transaction amount.<br>
These variables are used to calculate the transaction amount for each movie name in the file.

Error Handling:<br>
If an error occurs while running the program, it will print an error message to the console.
Specifically, if there is an issue with the file format or ticket counts, the function will raise a 'ValueError' with a corresponding error message.
The main program catches any 'Exception' that is raised and prints the error message to the console.

Conclusion:<br>
This program reads sales data from a file, calculates the transaction amount for each line, accumulates the sales data for each movie.
The program also performs error handling by raising 'ValueError' if the file format or ticket counts are incorrect.
