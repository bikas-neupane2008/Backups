/**
 * The Type enum represents the different types of car seat covers that are available.
 */
public enum Type {
    U30, U60, U06, U06H, U90, U301_A, U401_B, NA;

    /**
     * Returns the description of the car seat cover type.
     *
     * @return the description of the car seat cover type
     */
    public String toString() {
        return switch (this) {
            case NA -> "NA";
            case U30 ->
                    "Universal Type 30 (front) - has a separate piece for headrest allowing headrest to remain adjustable.";
            case U60 ->
                    "Universal Type 60 (front) - one piece seat cover that does not allow the headrest to be adjusted after it is fitted.";
            case U06 ->
                    "Universal Type 06 (rear) - does not allow for the headrests to be adjusted. Comes in two pieces (back and base).";
            case U06H ->
                    "Universal Type 06H (rear) - allows for headrests to be adjusted. It comes in two pieces (back and base).";
            case U90 -> "Universal Type 90 (front) - suited to utes and vans with a front high back bench style seat.";
            case U301_A ->
                    "Universal Type A or 301 (front) - suited to utes and vans with a front bucket seat and 3/4 bench (no gear stick cut-away).";
            case U401_B ->
                    "Universal Type B or 401 (front) - suited to utes and vans with a front bucket seat and 3/4 bench (gear stick cut-away).";
        };
    }
}