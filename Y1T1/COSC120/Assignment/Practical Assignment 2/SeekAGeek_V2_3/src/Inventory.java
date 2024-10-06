import java.util.*;
/**
 * Inheritance is a useful mechanism in object-oriented programming but can lead to design problems
 * such as tight coupling, fragility, limited reusability, and inflexibility.
 * Inheritance creates tight coupling between the base and derived classes,
 * making it difficult to modify the base class without affecting the derived classes.
 * Additionally, inheritance limits the reusability of code and can make it inflexible.
 * And in this assignment, just creating a whole single Object just for 1 value might not be a good choice.
 * i.e. type variable for CarSeatCover Object and diameter variable for WheelSeatCover Object.
 * Instead, we could just manage the variable might be by Mapping could be a good choice.
 * <p>
 * Storing highly-variable accessory-specific data dynamically in a Map data structure can be a better solution
 * due to its flexibility, scalability, ease of maintenance, and reduced code duplication.
 * Map data structures provide a flexible and scalable solution for storing and accessing accessory-specific data,
 * allowing each accessory to have its own set of properties.
 * Additionally, using a Map data structure makes it easier to maintain accessory-specific data, modify it dynamically,
 * and avoid code duplication that may arise from creating multiple classes.
 * Overall, a Map data structure can make it easier to manage accessory-specific data in situations
 * where the data is highly variable and difficult to predict in advance.
 * <p>
 * Storing highly-variable accessory-specific data dynamically in a Map data structure can be a better solution
 * due to its flexibility, scalability, ease of maintenance, and reduced code duplication.
 * Map data structures provide a flexible and scalable solution for storing and accessing accessory-specific data,
 * allowing each accessory to have its own set of properties.
 * Additionally, using a Map data structure makes it easier to maintain accessory-specific data, modify it dynamically,
 * and avoid code duplication that may arise from creating multiple classes.
 * Overall, a Map data structure can make it easier to manage accessory-specific data in situations
 * where the data is highly variable and difficult to predict in advance.
 * <p>
 * Storing highly-variable accessory-specific data dynamically in a Map data structure can be a better solution
 * due to its flexibility, scalability, ease of maintenance, and reduced code duplication.
 * Map data structures provide a flexible and scalable solution for storing and accessing accessory-specific data,
 * allowing each accessory to have its own set of properties.
 * Additionally, using a Map data structure makes it easier to maintain accessory-specific data, modify it dynamically,
 * and avoid code duplication that may arise from creating multiple classes.
 * Overall, a Map data structure can make it easier to manage accessory-specific data in situations
 * where the data is highly variable and difficult to predict in advance.
 */
/**
 * Storing highly-variable accessory-specific data dynamically in a Map data structure can be a better solution
 * due to its flexibility, scalability, ease of maintenance, and reduced code duplication.
 * Map data structures provide a flexible and scalable solution for storing and accessing accessory-specific data,
 * allowing each accessory to have its own set of properties.
 * Additionally, using a Map data structure makes it easier to maintain accessory-specific data, modify it dynamically,
 * and avoid code duplication that may arise from creating multiple classes.
 * Overall, a Map data structure can make it easier to manage accessory-specific data in situations
 * where the data is highly variable and difficult to predict in advance.
 */

/**
 * The Inventory class represents an inventory of CarAccessory objects.
 * It allows adding a new item to the inventory, and finding a list of matching CarAccessory items based on the desired specifications.
 */
public class Inventory {
    /**
     * The set of CarAccessory objects in the inventory.
     */
    private final Set<CarAccessory> inventory = new HashSet<>();

    /**
     * Adds a new CarAccessory item to the inventory.
     * @param carAccessory the CarAccessory item to be added to the inventory.
     */
    public void addItem(CarAccessory carAccessory) {
        this.inventory.add(carAccessory);
    }

    /**
     * Returns a set of all the unique themes available in the inventory.
     * @return a set of Strings representing the unique themes available in the inventory.
     */
    public Set<String> getAllThemes() {
        Set<String> allThemes = new TreeSet<>();
        for (CarAccessory carAccessory : inventory) {
            allThemes.add((String) carAccessory.getCarAccessorySpecs().getValueAtMap(Filter.THEME));
        }
        allThemes.add("NA");
        return allThemes;
    }

    /**
     * Finds and returns a list of CarAccessory items that match the desired specifications.
     * @param desiredCarAccessory a CarAccessorySpecs object representing the desired specifications.
     * @return a list of CarAccessory objects that match the desired specifications.
     */
    public List<CarAccessory> findMatch(CarAccessorySpecs desiredCarAccessory) {
        List<CarAccessory> matchingSeatCovers = new ArrayList<>();
        for (CarAccessory carAccessory : inventory) {
            if (!carAccessory.getCarAccessorySpecs().compareCarAccessorySpecs(desiredCarAccessory)) continue;
            if (carAccessory.getPrice() < desiredCarAccessory.getMinPrice() || carAccessory.getPrice() > desiredCarAccessory.getMaxPrice())
                continue;
            matchingSeatCovers.add(carAccessory);
        }
        return matchingSeatCovers;
    }
}