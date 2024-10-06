/**
 * The materials enum represents the different materials a car seat cover is made from.
 */

/**
 * To use materials features in the program, Materials are used as ENUM instead of array or Collection of Strings.
 * It's because enums have several advantages over string arrays and collections, including type safety, readability, maintainability, and performance.
 * So if you have a set of constants that are related to each other, it's usually a good idea to use enums.
 */
public enum Materials {
    NA, POLYURETHANE, LEATHER, POLYESTER, COTTON, ELASTANE, SHEEPSKIN;

    /**
     * Returns the materials of the car seat cover made from.
     *
     * @return the materials of the car seat cover made from
     */
    public String toString() {
        return switch (this) {
            case NA -> "NA";
            case POLYURETHANE -> "Polyurethane";
            case LEATHER -> "Leather";
            case POLYESTER -> "Polyester";
            case COTTON -> "Cotton";
            case ELASTANE -> "Elastane";
            case SHEEPSKIN -> "Sheepskin";
        };
    }
}