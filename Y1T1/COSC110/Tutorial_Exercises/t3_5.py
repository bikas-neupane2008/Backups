def has_duplicates(l):
    seen = []
    for item in l:
        if item in seen:
            return True
        seen.append(item)
    return False
x = list(map(str, input("Enter multiple values separated by comma: ").split(",")))
if has_duplicates(x) : print("It has duplicates value.")
else : print("It doesn't have duplicate value.")