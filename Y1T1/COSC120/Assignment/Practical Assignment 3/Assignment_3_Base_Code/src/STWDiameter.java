public enum STWDiameter {

    _375,_380,_420;

    public String toString() {
        return switch (this){
            case _375 -> "375mm";
            case _380 -> "380mm";
            case _420 -> "420mm";
        };
    }

}
