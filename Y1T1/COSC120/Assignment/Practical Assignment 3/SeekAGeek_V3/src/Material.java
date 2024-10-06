public enum Material {
    POLYURETHANE, LEATHER, POLYESTER, COTTON, ELASTANE, SHEEPSKIN, SELECT_MATERIAL;

    /**
     * Returns the string representation of the material.
     *
     * @return the string representation of the material
     */
    public String toString() {
        return switch (this) {
            case POLYURETHANE -> "Polyurethane (PU)";
            case LEATHER -> "Leather";
            case POLYESTER -> "Polyester";
            case COTTON -> "Cotton";
            case ELASTANE -> "Elastane";
            case SHEEPSKIN -> "Sheepskin";
            case SELECT_MATERIAL -> "Select Material";
        };
    }
}
