def is_palindrome(word):
    if len(word) <= 1 : return True
    elif word[0] != word[-1] : return False
    else : return is_palindrome(word[1:-1])
possible_palindrome = input("Enter a word/phrase to check: ")
if is_palindrome(possible_palindrome):
    print(possible_palindrome, "is a palindrome")
else:
    print(possible_palindrome, "is not a palindrome")