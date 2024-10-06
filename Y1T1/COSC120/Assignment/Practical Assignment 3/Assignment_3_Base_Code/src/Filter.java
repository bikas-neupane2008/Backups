public enum Filter {

    ACCESSORY_TYPE, THEME, MATERIAL, TYPE, DIAMETER;

    public String toString() {
        return switch (this){
            case ACCESSORY_TYPE -> "Type of car accessory";
            case THEME -> "Theme";
            case MATERIAL -> "Material";
            case TYPE -> "Type of car seat cover";
            case DIAMETER -> "Diameter of steering wheel";
        };
    }

}
