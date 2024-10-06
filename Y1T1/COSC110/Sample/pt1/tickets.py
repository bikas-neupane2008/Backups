#  The price of each ticket (in cents to avoid unncessary rounding)
adult_price = 2000
child_price = 1500
concession_price = 1750

#  The number of each ticket type that has been sold
adult_sales = 0
child_sales = 0
concession_sales = 0


def get_choice(options, prompt = "Choose: ",
               error = "Please enter one of: "):
    """
    Prompts the user to enter one of the given options.

    Parameters:
    options: A tuple/list of options to choose from. They should all be strings.
    prompt: The prompt to display before listing the options.

    Returns: The selected option
    """
    choice_string = "/".join(options)
    option_string = ", ".join(options)

    result = input(f"{prompt} ({choice_string}) ")
    while result not in options:
        print(error + option_string)
        result = input(f"{prompt} ({choice_string}) ")
    return result


def get_integer(prompt = "Please enter an integer: ",
                error = "Please enter an integer"):
    """
    Prompts the user to enter an integer.

    Parameters:
    prompt: The prompt to display when asking for the value
    error: The message to display if a valid integer isn't entered

    Returns: The integer entered by the user
    """
    while True:
        result = input(f"{prompt} ")
        try:
            return int(result)
        except ValueError:
            print(error)


def get_non_negative_integer(prompt = "Please enter a non-negative integer: ",
                             error = "Please enter a non-negative integer"):
    """
    Prompts the user to enter a non-negative integer.

    Parameters:
    prompt: The prompt to display when asking for the value
    error: The message to display if a valid non-negative integer isn't entered

    Returns: The non-negative integer entered by the user
    """
    result = get_integer(prompt, error)
    while result < 0:
        print(error)
        result = get_integer(prompt, error)
    return result


new_sale = get_choice(("yes", "no"), "Is there a new ticket sale?")
while new_sale == "yes":

    #  Process transaction
    while True:
        adult_tickets = get_non_negative_integer("Number of adult tickets:")
        child_tickets = get_non_negative_integer("Number of child tickets:")
        concession_tickets = get_non_negative_integer("Number of concession tickets:")
        if adult_tickets + child_tickets + concession_tickets == 0:
            print("There must be at least one ticket in the transaction!")
        else:
            break

    # Display cost of transaction
    sale_total = adult_tickets * adult_price + child_tickets * child_price + concession_tickets * concession_price
    print(f"Total due: ${sale_total/100:.2f}")

    #  Add transaction to system
    adult_sales += adult_tickets
    child_sales += child_tickets
    concession_sales += concession_tickets

    #  Move to next sale, if necessary
    print()
    new_sale = get_choice(("yes", "no"), "Is there a new ticket sale?")

#  Calculate and display totals
total_sales = adult_sales * adult_price + child_sales * child_price + concession_sales * concession_price
print()
print(f"Total sales: ${total_sales/100:.2f}")
print(f"Adult tickets sold: {adult_sales}")
print(f"Child tickets sold: {child_sales}")
print(f"Concession tickets sold: {concession_sales}")
