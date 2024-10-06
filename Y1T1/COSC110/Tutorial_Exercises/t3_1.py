def right_justify(input_string):
    if len(input_string) > 79: print(input_string)
    else: print(' ' * (80-len(input_string)) + input_string)
value = input("Enter string to justify: ")
right_justify(value)