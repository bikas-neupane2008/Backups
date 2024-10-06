// Part 2 of the Assignment
// At the top of Type, in a block comment,
// explain why an Enum data type is the best way to represent the type values.

/*
 * Enums in Java are the best way to represent a fixed set of values
 * because they provide readability, type safety, constant values, switch statements, and namespace control.
 * They ensure code consistency and prevent errors by allowing
 * descriptive names for values, being type-safe, and grouping related constants together.
 */

// A constant representing each available type, e.g., U30, U60 etc..
public enum Type {
    U30, U60, U06, U06H, U90, U301_A, U401_B;
    // A method that returns an informative and visually pleasing String representation of each constant.
    public String toString() {
        String typeName = null;
        switch (this){
            case U30 -> typeName = "Universal Type 30 (front) - has a separate piece for headrest allowing headrest to remain adjustable.";
            case U60 -> typeName = "Universal Type 60 (front) - one piece seat cover that does not allow the headrest to be adjusted after it is fitted.";
            case U06 -> typeName = "Universal Type 06 (rear) - does not allow for the headrests to be adjusted. Comes in two pieces (back and base).";
            case U06H -> typeName = "Universal Type 06H (rear) - allows for headrests to be adjusted. It comes in two pieces (back and base).";
            case U90 -> typeName = "Universal Type 90 (front) - suited to utes and vans with a front high back bench style seat.";
            case U301_A -> typeName = "Universal Type A or 301 (front) - suited to utes and vans with a front bucket seat and 3/4 bench (no gear stick cut-away).";
            case U401_B -> typeName = "Universal Type B or 401 (front) - suited to utes and vans with a front bucket seat and 3/4 bench (gear stick cut-away).";
        }
        return name() + " : " + typeName;
    }
}
