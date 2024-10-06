public enum Sauces {
    TOMATO, GARLIC, AIOLI, BIG_MAC, BBQ, CHILLI, RANCH, SPECIAL;
    public String toString() {
        String saucesName = null;
        switch (this){
            case TOMATO -> saucesName = "Tomato";
            case GARLIC -> saucesName = "Garlic";
            case AIOLI -> saucesName = "AIOLI (vegan friendly)";
            case BIG_MAC -> saucesName = "Big Mac";
            case BBQ -> saucesName = "BBQ";
            case CHILLI -> saucesName = "Chilli";
            case RANCH -> saucesName = "Ranch";
            case SPECIAL -> saucesName = "My House Special Sauce";
        }
        return saucesName;
    }
}
