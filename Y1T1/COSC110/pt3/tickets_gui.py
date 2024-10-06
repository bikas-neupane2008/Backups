#!/usr/bin/env python3
import tkinter as tk
import tkinter.messagebox as messagebox

# Define the function to calculate the cost
def calculate_cost():
    # Set the value of each ticket price
    adult_price = 20
    child_price = 15
    concession_price = 17.50

    # Reset Cost to empty
    cost_value.config(text="")

    # Get the number of adult ticket
    try:
        adult_tickets = int(adult_entry.get())
        if adult_tickets < 0:
            messagebox.showerror(title="Adult Ticket Error", message="Please enter value\n greater than or equal to 0\n for adult ticket.")
            adult_entry.focus()
            return
    except ValueError:
        messagebox.showerror(title="Adult Ticket Error", message="Please enter a positive integer\n value for adult tickets.")
        adult_entry.focus()
        return

    # Get the number of child ticket
    try:
        child_tickets = int(child_entry.get())
        if child_tickets < 0:
            messagebox.showerror(title="Child Ticket Error", message="Please enter value\n greater than or equal to 0\n for child ticket.")
            child_entry.focus()
            return
    except ValueError:
        messagebox.showerror(title="Child Ticket Error", message="Please enter a positive integer\n value for child tickets.")
        child_entry.focus()
        return

    # Get the number of child ticket
    try:
        concession_tickets = int(concession_entry.get())
        if concession_tickets < 0:
            messagebox.showerror(title="Concession Ticket Error", message="Please enter value\n greater than or equal to 0\n for concession ticket.")
            concession_entry.focus()
            return
    except ValueError:
        messagebox.showerror(title="Concession Ticket Error", message="Please enter a positive integer\n value for concession tickets.")
        concession_entry.focus()
        return

    # Check if there is any type of ticket sold
    if adult_tickets == 0 and child_tickets == 0 and concession_tickets == 0:
        messagebox.showerror(title="Tickets Error", message="It seems like there is\n no any ticket sold.")
        adult_entry.focus()
        return

    # Calculate the cost of the transaction
    cost = adult_tickets * adult_price + child_tickets * child_price + concession_tickets * concession_price

# Display the cost of the transaction
    cost_value.config(text="$"+str(cost))
    # Set the focus to the calculate_button widget
    calculate_button.focus()


# Define the function to reset the transaction
def reset_transaction():
    # Delete the inputs from the entry boxes
    adult_entry.delete(0, tk.END)
    child_entry.delete(0, tk.END)
    concession_entry.delete(0, tk.END)

    # Resets the entry box to default
    adult_entry.insert(0, "")
    child_entry.insert(0, "")
    concession_entry.insert(0, "")

    # Resets the calculate button to disabled
    calculate_button.config(state="disabled")

    # Reset the cost label
    cost_value.config(text="")

    # Set the focus to the adult_entry widget
    adult_entry.focus()

    # Resets the reset button to disabled
    reset_button.config(state="disabled")

# Define the function to check the input entries
def on_entry(event):
    if not adult_entry.get() or not child_entry.get() or not concession_entry.get():
        calculate_button.config(state="disabled")
        reset_button.config(state="normal")
        if not adult_entry.get() and not child_entry.get() and not concession_entry.get():
            reset_button.config(state="disabled")
    else:
        calculate_button.config(state="normal")

# Define the window
window = tk.Tk()

# Customize the window title
window.title("Code Cinema")

# Set the window size
width=350
height=150
window.geometry(f"{width}x{height}")

# Calculate the screen dimensions
screen_width = window.winfo_screenwidth()
screen_height = window.winfo_screenheight()

# Calculate the coordinates for the center of the screen
x = int((screen_width / 2) - (width / 2))
y = int((screen_height / 2) - (height / 2))

# Set the window position
window.geometry(f"+{x}+{y}")
# window.geometry("+{}+{}".format(x, y))

# Define the labels
adult_label = tk.Label(window, text="Adult Tickets :")
child_label = tk.Label(window, text="Child Tickets :")
concession_label = tk.Label(window, text="Concession Tickets :")
cost_label = tk.Label(window, text="Cost :")
cost_value = tk.Label(window, text="")

# Define the entry boxes
adult_entry = tk.Entry(window)
child_entry = tk.Entry(window)
concession_entry = tk.Entry(window)

# Set default values for the entry boxes
adult_entry.insert(0, "")
child_entry.insert(0, "")
concession_entry.insert(0, "")

# Define the calculate button
calculate_button = tk.Button(window, text="Calculate", command=calculate_cost, state="disabled")

# Define the reset button
reset_button = tk.Button(window, text="Reset", command=reset_transaction, state="disabled")

# Add the labels to the window
adult_label.grid(row=0, column=0, padx=5, pady=5, sticky="e")
child_label.grid(row=1, column=0, padx=5, pady=5, sticky="e")
concession_label.grid(row=2, column=0, padx=5, pady=5, sticky="e")
cost_label.grid(row=3, column=0, padx=5, pady=5, sticky="e")
cost_value.grid(row=3, column=1, padx=5, pady=5, sticky="w")

# Add the entry boxes to the window
adult_entry.grid(row=0, column=1, padx=5, pady=5, sticky="w")
child_entry.grid(row=1, column=1, padx=5, pady=5, sticky="w")
concession_entry.grid(row=2, column=1, padx=5, pady=5, sticky="w")

# Add the calculate button to the window
calculate_button.grid(row=4, column=0, padx=5, pady=5)

# Add the reset button to the window.
reset_button.grid(row=4, column=1, padx=5, pady=5)

adult_entry.bind("<KeyRelease>", on_entry)
child_entry.bind("<KeyRelease>", on_entry)
concession_entry.bind("<KeyRelease>", on_entry)

# Set the focus to the adult_entry widget
adult_entry.focus()

# Configure the rows and columns to resize dynamically
for i in range(5):  # for 5 rows
    window.grid_rowconfigure(i, weight=1)
for i in range(2):  # for 2 columns
    window.grid_columnconfigure(i, weight=1)

# Start the main loop
window.mainloop()
