def draw_grid(width,height):
    # display top and bottom by tb
    tb = "+"
    # display inside the grid
    inside = "|"
    for i in range(width):
        tb = tb + "-"
        inside = inside + " "
    tb = tb + "+"
    inside = inside + "|"

    # print out the required grid
    print(tb)

    for i in range(height):
        print(inside)

    print(tb)

draw_grid(3,4)