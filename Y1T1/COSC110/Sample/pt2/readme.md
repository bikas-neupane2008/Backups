# Sales

A program to tally ticket sales for Codetown Cinema.
Developed to fulfill the requirements of Programming Task 2 for COSC110, T1 2023.

## Usage

```bash
python3 sales.py
```

## Example interaction

The program reads information from a file named ```transactions.txt```.
It then sorts the movies listed in the file from those that grossed the highest amount of sales.

e.g., If ```transactions.txt``` contains:


```
The Stockholm Affair,2,3,2
Pandora Strain,1,0,1
Pandora Strain,2,2,0
The Stockholm Affair,1,1,1
Pandora Strain,0,0,2
Pandora Strain,0,0,3
The Momentum Of Things,1,0,0
Pandora Strain,4,0,0
The Stockholm Affair,2,3,2
```

Then the program will output:

```
The Stockholm Affair            $292.50
Pandora Strain                  $275.00
The Momentum Of Things          $20.00
```

## Contributors

David Paul [David.Paul@une.edu.au](David.Paul@une.edu.au)
