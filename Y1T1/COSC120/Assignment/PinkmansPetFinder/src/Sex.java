public enum Sex {
    MALE,FEMALE,NA;
    public String toString() {
        switch (this) {
            case MALE -> {
                return "Male";
            }
            case FEMALE -> {
                return "Female";
            }
            default -> {
                return "NA";
            }
        }
    }
}
