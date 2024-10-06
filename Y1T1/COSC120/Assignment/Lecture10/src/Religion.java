/**
 * enum of the top 7 religions + unaffiliated
 */
public enum Religion{
    CHRISTIAN,MUSLIM,UNAFFILIATED,HINDU,BUDDHIST,SIKH,JEWISH,BAHAI,NA;

    /**
     * @return a prettified description of the range of religions
     */
    public String toString() {
        return switch (this) {
            case CHRISTIAN -> "Christian";
            case MUSLIM -> "Muslim";
            case UNAFFILIATED -> "Unaffiliated";
            case HINDU -> "Hindu";
            case BUDDHIST -> "Buddhist";
            case SIKH -> "Sikh";
            case JEWISH -> "Jewish";
            case BAHAI -> "Bahai";
            case NA -> "Any religion will do!";
        };
    }
}
