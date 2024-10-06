def factorial(n):
    """Calculates the factorial of the given parameter."""
    product = 1
    while n > 0:
        product = product * n
        n = n - 1
    return product
number = int(input("Enter a number to calculate its factorial : "))
factorial = factorial(number)
print("The factorial is of %d is : %d" % (number, factorial))