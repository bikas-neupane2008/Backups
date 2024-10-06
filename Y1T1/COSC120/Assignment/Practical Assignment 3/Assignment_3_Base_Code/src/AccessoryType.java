public enum AccessoryType {

    CAR_SEAT_COVER,STEERING_WHEEL_COVER;

    public String toString() {
        return switch (this){
            case CAR_SEAT_COVER -> "Car seat cover";
            case STEERING_WHEEL_COVER -> "Steering wheel cover";
        };
    }
}
