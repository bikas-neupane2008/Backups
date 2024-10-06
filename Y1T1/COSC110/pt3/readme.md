<div align = "justify"><H2>Introduction:</H2>This code is an implementation of a graphical user interface (GUI) for a cinema ticket booking system. The GUI is created using the tkinter library in Python. The user can input the number of tickets for each ticket type (adult, child, and concession) and the cost of the transaction is calculated based on the ticket prices and the number of tickets sold. The GUI also has a reset button to clear the input fields and a calculate button to perform the cost calculation.
<H3>Instructions:</H3>
Before running the program,
<li>Install Python in the system</li>
<li>Import tkinter</li>
<li>Import messagebox</li>
<H3>To run the program:</H3>
Enter the below code in bash/terminal/command prompt.<br>
python3 tickets_gui.py<br>

<H3>How to Use:</H3>
<ol>
<li>Upon launching the application, a window titled "Code Cinema" will appear by default in 350X150 size.</li>
<li>Enter the number of adult, child, and concession tickets in the respective entry fields.</li>
<li>The "Calculate" button will be enabled only when all entry fields are filled.</li>
<li>The "Reset" button will be enabled only if there is any contents inside any entry fields.</li>
<li>Click the "Calculate" button to calculate the total cost of the tickets.</li>
<li>The calculated cost will be displayed after the "Cost" label.
<li>The focus is set to "Calculate" button to ensure there is no unintended input after the calculate button is pressed.</li>
<li>To reset the transaction, click the "Reset" button. It will clear the entry fields and cost value and disable the "Calculate" button and "Reset" button.</li>
<li>You can resize the application window as per your choice.</li>
<li>You can exit the application by closing the window or pressing Ctrl+C in the terminal/command prompt.</li>
</ol>
<B>Note:</B><br>
<ul>
<li>The application validates the input to ensure that the number of tickets is a positive integer or zero.<br>
If invalid input is entered, appropriate error messages will be displayed.<br></li>
<li>The application validates the input to ensure that there is at least one of the tickets are sold.<br>If all entry is 0 then, error message saying "It seems like there is no any ticket sold." will be displayed.</li>
</ul>
Overall, this code is a simple implementation of a "Code Cinema" ticket booking system using GUI in Python with tkinter.
</div>