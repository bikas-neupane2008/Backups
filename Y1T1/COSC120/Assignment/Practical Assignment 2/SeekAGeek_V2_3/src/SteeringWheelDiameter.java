/**
 * The SteeringWheelDiameter enum represents the different size a car wheel cover.
 */
public enum SteeringWheelDiameter {
    _375, _380, _420, _NA;

    /**
     * Returns the steering wheel diameter of the car.
     *
     * @return the steering wheel diameter of the car
     */
    public String toString() {
        return switch (this) {
            case _NA -> "NA";
            case _375 -> "375";
            case _380 -> "380";
            case _420 -> "420";
        };
    }
}
