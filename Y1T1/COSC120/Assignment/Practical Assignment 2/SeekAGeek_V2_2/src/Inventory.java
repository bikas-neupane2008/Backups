import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Inheritance is a useful mechanism in object-oriented programming but can lead to design problems
 * such as tight coupling, fragility, limited reusability, and inflexibility.
 * Inheritance creates tight coupling between the base and derived classes,
 * making it difficult to modify the base class without affecting the derived classes.
 * Additionally, inheritance limits the reusability of code and can make it inflexible.
 * Therefore, other design patterns like composition and dependency injection may be more appropriate.
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
 * The Inventory class represents an inventory of CarSeatCover objects.
 * It allows adding a new item to the inventory, and finding a list of matching CarSeatCover items based on the desired specifications.
 */
public class Inventory {
    /**
     * The set of CarSeatCover objects in the inventory.
     */
    private final Set<CarSeatCover> inventory = new HashSet<>();

    /**
     * Adds a new CarSeatCover item to the inventory.
     * @param carSeatCover the CarSeatCover item to be added to the inventory.
     */
    public void addItem(CarSeatCover carSeatCover) {
        this.inventory.add(carSeatCover);
    }

    /**
     * Returns a set of all the unique themes available in the inventory.
     * @return a set of Strings representing the unique themes available in the inventory.
     */
    public Set<String> getAllThemes() {
        Set<String> allThemes = new HashSet<>();
        for (CarSeatCover carSeatCover : inventory) {
            allThemes.add(carSeatCover.getCarSeatCoverSpecs().getTheme());
        }
        return allThemes;
    }

    /**
     * Finds and returns a list of CarSeatCover items that match the desired specifications.
     * @param desiredCarSeatCover a CarSeatCoverSpecs object representing the desired specifications.
     * @return a list of CarSeatCover objects that match the desired specifications.
     */
    public List<CarSeatCover> findMatch(CarSeatCoverSpecs desiredCarSeatCover) {
        List<CarSeatCover> matchingSeatCovers = new ArrayList<>();
        for (CarSeatCover carSeatCover : inventory) {
            if (!carSeatCover.getCarSeatCoverSpecs().compareCarSeatCoverSpecs(desiredCarSeatCover)) continue;
            if (carSeatCover.getPrice() < desiredCarSeatCover.getMinPrice() || carSeatCover.getPrice() > desiredCarSeatCover.getMaxPrice())
                continue;
            matchingSeatCovers.add(carSeatCover);
        }
        return matchingSeatCovers;
    }
}