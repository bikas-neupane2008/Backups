public enum Material {

    POLYURETHANE,LEATHER,POLYESTER,COTTON,ELASTANE,SHEEPSKIN,NA;

    public String toString(){
        return switch (this) {
            case POLYURETHANE -> "Polyurethane (PU)";
            case LEATHER -> "Leather";
            case POLYESTER -> "Polyester";
            case COTTON -> "Cotton";
            case ELASTANE -> "Elastane";
            case SHEEPSKIN -> "Sheepskin";
            default -> "Skip...";
        };
    }



}
