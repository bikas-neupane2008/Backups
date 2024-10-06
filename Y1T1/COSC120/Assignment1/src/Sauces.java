public enum Sauces {
    TOMATO, GARLIC, AIOLI, BIG_MAC, BBQ, CHILLI, RANCH, SPECIAL;

    public String toString() {
        String saucesName = null;
        switch (this) {
            case TOMATO -> saucesName = "Tomato";
            case GARLIC -> saucesName = "Garlic";
            case AIOLI -> saucesName = "Aioli (Vegan friendly)";
            case BIG_MAC -> saucesName = "Big Mac";
            case BBQ -> saucesName = "BBQ";
            case CHILLI -> saucesName = "Chilli";
            case RANCH -> saucesName = "Ranch";
            case SPECIAL -> saucesName = "Special Sauce";
        }
        return saucesName;
    }
}
