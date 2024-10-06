import math
def factorial(n):
    """Calculate the factorial of the given value and return the result.

    The factorial of n is the product of all positive integers less than or equal to n.
    This function does not support negative values - if a negative value is given, this function just returns 1.

    Arguments:
    n -- A positive integer
    """
    assert isinstance(n, int) and n >= 0, "Input must be a non-negative integer"
    result = 1
    while n > 0:
        result = result * n
        n = n - 1
    return result

# Calculate factorial for the first four integers
for i in range(1,6):
    assert factorial(i) == math.factorial(i), f"Factorial of {i} is incorrect"
    print('Factorial of', i, 'is', factorial(i))
