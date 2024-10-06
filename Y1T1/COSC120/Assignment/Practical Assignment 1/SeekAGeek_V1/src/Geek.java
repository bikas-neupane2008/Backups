// Part 1 of the Assignment
public class Geek {
    // 1. Two or more fields, as specified by Greek Geek.
    // fields
    private final String name;
    private final long phone;
    private final String email;

    /**
     * constructor to initialise a Geek object with name, phone and email
     *
     * @param name  the program user's full name
     * @param phone the program user's 10-digit phone number
     * @param email the program user's email
     */
    // 2. A constructor that initialises the fields.
    public Geek(String name, long phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }
    // 3. Getters for each field.
    // getters

    /**
     * @return the program user's name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the program user's 10 digit ph num
     */
    public long getPhone() {
        return phone;
    }

    /**
     * @return the program user's email address
     */
    public String getEmail() {
        return email;
    }
}