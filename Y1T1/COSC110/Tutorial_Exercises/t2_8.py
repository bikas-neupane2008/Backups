hours = int(input("How many hours did you work? "))
rate = int(input("How much do you get paid per hour? "))
pay_due = hours * rate
if hours < 10 : pay_due = pay_due + rate
if hours > 100 : pay_due = pay_due + rate * 5
print (pay_due)