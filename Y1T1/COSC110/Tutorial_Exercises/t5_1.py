def factorial(n):
    """Calculate the factorial of the given value and return the result.

      The factorial of n is the product of all positive integers less than or equal to n.
      This function does not support negative values - if a negative value is given, this function just returns 1.

      Arguments:
      n -- A positive integer
    """
    result = 1
    while n > 0:
        result = result * n
        n = n - 1
    return result

# Calculate factorial for the first four integers
for i in range(1, 6):
    print('Factorial of', i, 'is', factorial(i))