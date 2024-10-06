/**
 * The Filter enum represents the accessory type, material, theme, type and size.
 */
public enum Filter {
    ACCESSORY_TYPE, MATERIAL, THEME, TYPE, SIZE;

    /**
     * Returns the filter of the car accessory for user criteria.
     *
     * @return the filter of the car accessory for user criteria
     */
    public String toString() {
        return switch (this) {
            case ACCESSORY_TYPE -> "Accessory Type";
            case MATERIAL -> "Material";
            case THEME -> "Theme";
            case TYPE -> "Type";
            case SIZE -> "Size";
        };
    }
}
