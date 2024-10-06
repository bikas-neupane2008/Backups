
public enum Gender {
    MALE,FEMALE,OTHER,NA;

    /**
     * @return prettified representation of gender enum constants
     */
    public String toString(){
        return switch (this) {
            case FEMALE -> "Female";
            case MALE -> "Male";
            case OTHER -> "Other";
            case NA -> "No preference";
        };
    }
}
