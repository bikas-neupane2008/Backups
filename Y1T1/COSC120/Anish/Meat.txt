public enum Meat {
    BEEF, CHICKEN, VEGAN;

    public String toString() {
        String pattiesName = null;
        switch (this) {
            case BEEF -> pattiesName = "Beef";
            case CHICKEN -> pattiesName = "Chicken";
            case VEGAN -> pattiesName = "Vegan";
        }
        return pattiesName;
    }
}
