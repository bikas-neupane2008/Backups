/**
 * Enums and arrays/collections of strings serve different purposes and have their own advantages and disadvantages.
 * Enums are a way of defining a fixed set of named values, where each value represents a distinct member of the group.
 * They are typically used to represent a limited number of options or choices, such as days of the week, colors, or types of cars.
 * Enums provide the following advantages over arrays or collections of strings:
 * 1. Type safety: Enums are strongly typed, which means that the compiler will enforce the type of the value being assigned to an enum.
 * This helps prevent errors that can arise from using the wrong data type or a misspelled string.
 * 2. Readability: Enums provide clear and descriptive names for each value, which makes the code more readable and self-documenting.
 * 3. Maintenance: Enums can help reduce maintenance by centralizing all the values in one place.
 * If a value needs to be added or removed, it only needs to be changed in one location, rather than searching through all instances of an array or collection.
 * <p>
 * Arrays or collections of strings, on the other hand, are useful when you need to store a variable number of string values, such as a list of names or addresses.
 * They provide the following advantages over enums:
 * 1. Flexibility: Arrays or collections of strings allow for more flexibility in the number of values that can be stored.
 * Enums, by definition, are limited to a fixed set of values.
 * 2. Ease of use: Arrays or collections of strings are easier to use when the values are not known at compile-time, such as when reading from a file or user input.
 * 3. Localization: Arrays or collections of strings can be used for localization, where the same program can be used with different languages by storing the translated strings in different arrays or collections.
 * In summary, enums are better than arrays or collections of strings when you need a fixed set of named values with type safety and better readability,
 * while arrays or collections of strings are better when you need more flexibility in the number of values or when dealing with user input or localization.
 * <p>
 * Arrays or collections of strings, on the other hand, are useful when you need to store a variable number of string values, such as a list of names or addresses.
 * They provide the following advantages over enums:
 * 1. Flexibility: Arrays or collections of strings allow for more flexibility in the number of values that can be stored.
 * Enums, by definition, are limited to a fixed set of values.
 * 2. Ease of use: Arrays or collections of strings are easier to use when the values are not known at compile-time, such as when reading from a file or user input.
 * 3. Localization: Arrays or collections of strings can be used for localization, where the same program can be used with different languages by storing the translated strings in different arrays or collections.
 * In summary, enums are better than arrays or collections of strings when you need a fixed set of named values with type safety and better readability,
 * while arrays or collections of strings are better when you need more flexibility in the number of values or when dealing with user input or localization.
 * <p>
 * Arrays or collections of strings, on the other hand, are useful when you need to store a variable number of string values, such as a list of names or addresses.
 * They provide the following advantages over enums:
 * 1. Flexibility: Arrays or collections of strings allow for more flexibility in the number of values that can be stored.
 * Enums, by definition, are limited to a fixed set of values.
 * 2. Ease of use: Arrays or collections of strings are easier to use when the values are not known at compile-time, such as when reading from a file or user input.
 * 3. Localization: Arrays or collections of strings can be used for localization, where the same program can be used with different languages by storing the translated strings in different arrays or collections.
 * In summary, enums are better than arrays or collections of strings when you need a fixed set of named values with type safety and better readability,
 * while arrays or collections of strings are better when you need more flexibility in the number of values or when dealing with user input or localization.
 * <p>
 * Arrays or collections of strings, on the other hand, are useful when you need to store a variable number of string values, such as a list of names or addresses.
 * They provide the following advantages over enums:
 * 1. Flexibility: Arrays or collections of strings allow for more flexibility in the number of values that can be stored.
 * Enums, by definition, are limited to a fixed set of values.
 * 2. Ease of use: Arrays or collections of strings are easier to use when the values are not known at compile-time, such as when reading from a file or user input.
 * 3. Localization: Arrays or collections of strings can be used for localization, where the same program can be used with different languages by storing the translated strings in different arrays or collections.
 * In summary, enums are better than arrays or collections of strings when you need a fixed set of named values with type safety and better readability,
 * while arrays or collections of strings are better when you need more flexibility in the number of values or when dealing with user input or localization.
 */
/**
 * Arrays or collections of strings, on the other hand, are useful when you need to store a variable number of string values, such as a list of names or addresses.
 * They provide the following advantages over enums:
 * 1. Flexibility: Arrays or collections of strings allow for more flexibility in the number of values that can be stored.
 * Enums, by definition, are limited to a fixed set of values.
 * 2. Ease of use: Arrays or collections of strings are easier to use when the values are not known at compile-time, such as when reading from a file or user input.
 * 3. Localization: Arrays or collections of strings can be used for localization, where the same program can be used with different languages by storing the translated strings in different arrays or collections.
 * In summary, enums are better than arrays or collections of strings when you need a fixed set of named values with type safety and better readability,
 * while arrays or collections of strings are better when you need more flexibility in the number of values or when dealing with user input or localization.
 */

/**
 * The materials enum represents the different materials a car seat cover is made from.
 */
public enum Materials {
    POLYURETHANE, LEATHER, POLYESTER, COTTON, ELASTANE, SHEEPSKIN;

    /**
     * Returns the materials of the car seat cover made from.
     * @return the materials of the car seat cover made from
     */
    public String toString() {
        return switch (this) {
            case POLYURETHANE -> "Polyurethane";
            case LEATHER -> "Leather";
            case POLYESTER -> "Polyester";
            case COTTON -> "Cotton";
            case ELASTANE -> "Elastane";
            case SHEEPSKIN -> "Sheepskin";
        };
    }
}