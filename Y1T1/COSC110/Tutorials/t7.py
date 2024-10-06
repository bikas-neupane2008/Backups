import turtle

def square(turtle):
    for i in range(4):
        turtle.fd(100)
        turtle.lt(90)

bob = turtle.Turtle()
square(bob)

# In the browser, you need to use the following rather than turtle.mainloop()
turtle.done() # turtle.done() is used when the program is executed in browser
#turtle.mainloop() #turtle.mainloop() is used when the program is executed in other IDE