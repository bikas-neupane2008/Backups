/**
 * The AccessoryType enum represents the different types of accessory that are available.
 */
public enum AccessoryType {
    CAR_SEAT_COVER, STEERING_WHEEL_COVER;

    /**
     * Returns the accessory type of the car.
     *
     * @return the accessory type of the car
     */
    public String toString() {
        return switch (this) {
            case CAR_SEAT_COVER -> "Car Seat Cover";
            case STEERING_WHEEL_COVER -> "Steering Wheel Cover";
        };
    }
}
