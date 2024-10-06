public enum Filter {

    ACCESSORY_TYPE, THEME, MATERIAL, TYPE, DIAMETER;

    /**
     * Returns the string representation of the Filter.
     *
     * @return the string representation of the Filter
     */
    public String toString() {
        return switch (this) {
            case ACCESSORY_TYPE -> "Type of car accessory";
            case THEME -> "Theme";
            case MATERIAL -> "Material";
            case TYPE -> "Type of car seat cover";
            case DIAMETER -> "Diameter of steering wheel";
        };
    }

}
