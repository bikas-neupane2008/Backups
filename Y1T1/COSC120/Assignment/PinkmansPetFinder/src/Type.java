public enum Type {
    CAT,DOG,GUINEA_PIG,SELECT_TYPE;

    public String toString(){
        return switch (this) {
            case CAT -> "Cat";
            case DOG -> "Dog";
            case GUINEA_PIG -> "Guinea pig";
            case SELECT_TYPE -> "Select pet type";
        };
    }
}
