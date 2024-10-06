public enum AccessoryType {

    CAR_SEAT_COVER, STEERING_WHEEL_COVER, SELECT_ACCESSORY_TYPE;

    /**
     * Returns the string representation of the AccessoryType.
     *
     * @return the string representation
     */
    public String toString() {
        return switch (this) {
            case CAR_SEAT_COVER -> "Car seat cover";
            case STEERING_WHEEL_COVER -> "Steering wheel cover";
            case SELECT_ACCESSORY_TYPE -> "Select Accessory Type";
        };
    }
}
