#  The name of the file to read transactions from
FILENAME = "transactions.txt"

#  The price of each ticket (in cents to avoid unncessary rounding)
adult_price = 2000
child_price = 1500
concession_price = 1750

def read_sales_data(filename = FILENAME):
    """
    Reads a number of transactions from the given file, with each line starting with the name of a movie,
    followed by a comma (```,```), followed by the number of adult tickets included in the transaction,
    then a comma (,), then the number of child tickets included in the transaction, then a comma (,),
    and finally the number of concession tickets included in that transaction.
    Any line that does not meet this format exactly raises a ValueError.

    Parameters:
        filename:   The name of the file to read the transactions from

    Returns: a dictionary, with the names of the movies as the keys, and the gross sales for each movie
             (i.e., the total amount taken in for the movie) as the associated value.

    Raises:
        ValueError: If any of the lines do not meet the required format
    """
    result = {}
    with open(filename) as file:
        for line in file:
            split_line = line.split(",")
            #  Handle the case where a movie title includes a comma
            if len(split_line) > 4:
                #  The last three entries should be the adult, child, and concession numbers (respectively)
                #  Assume everything else is part of the movie title
                split_line = [",".join(split_line[:-3])] + split_line[-3:]
            movie, adult, child, concession = split_line
            adult = int(adult)
            child = int(child)
            concession = int(concession)
            if adult < 0 or child < 0 or concession < 0:
                raise ValueError("Number of tickets must be non-negative.")
            new_sale = 2000 * adult + 1500 * child + 1750 * concession
            result[movie] = result.get(movie, 0) + new_sale
    return result


def error(filename = FILENAME):
    """ Outputs an error message specifying what should be in the given file """

    print(f"""Error reading data.
Please ensure {filename} exists and each line contains a movie name, followed by a comma, followed by the number of adult tickets bought in the transaction, followed by a comma, followed by the number of child tickets bought in the transaction, followed by a comma, followed by the number of concession tickets bought in the transaction.
All numbers of tickets must be non-negative.
e.g., something like:
Too Many Grandmas,1,2,3""")


def main():
    """ Reads in transaction data and lists movies from highest grossing to lowest """
    try:
        sales = read_sales_data(FILENAME)
        for movie in sorted(sales, key = sales.get, reverse = True):
            print(f"{movie:30}\t${sales[movie]/100:.2f}")
    except (FileNotFoundError, ValueError):
        error(FILENAME)


if __name__ == "__main__":
    main()
