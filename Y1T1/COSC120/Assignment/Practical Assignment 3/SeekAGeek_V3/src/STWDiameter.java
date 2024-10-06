public enum STWDiameter {
    _375, _380, _420, SELECT_SIZE;

    /**
     * Returns the string representation of the steering wheel diameter.
     *
     * @return the string representation of the steering wheel diameter
     */
    public String toString() {
        return switch (this) {
            case _375 -> "375mm";
            case _380 -> "380mm";
            case _420 -> "420mm";
            case SELECT_SIZE -> "Select Size";
        };
    }
}
