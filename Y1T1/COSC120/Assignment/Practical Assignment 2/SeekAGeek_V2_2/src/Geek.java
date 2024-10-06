/**
 * The Geek class represents a geek object that includes a name and a phone number.
 */
public class Geek {
    private final String name;
    private final long phoneNumber;

    /**
     * Constructs a Geek object with the specified name and phone number.
     *
     * @param name        the name of the geek
     * @param phoneNumber the phone number of the geek
     */
    public Geek(String name, long phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns the name of the geek.
     *
     * @return the name of the geek
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number of the geek.
     *
     * @return the phone number of the geek
     */
    public long getPhoneNumber() {
        return phoneNumber;
    }
}