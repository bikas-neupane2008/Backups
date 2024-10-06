import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Inventory {

    private static final Set<CarAccessory> inventory = new HashSet<>();

    /**
     * Retrieves a set of all themes available in the inventory.
     *
     * @return the set of all themes
     */
    public static Set<String> getAllThemes() {
        Set<String> allThemes = new HashSet<>();
        for (CarAccessory carAccessory : inventory) {
            allThemes.add((String) carAccessory.getCarAccessorySpecs().getFilter(Filter.THEME)); //change to access via filter
        }
        return allThemes;
    }

    /**
     * Retrieves the maximum price among all car accessories in the inventory.
     *
     * @return the maximum price
     */
    public static double maximumPrice() {
        double maxPrice = 0;
        for (CarAccessory carAccessory : inventory) {
            if (carAccessory.getPrice() > maxPrice) maxPrice = carAccessory.getPrice();
        }
        return maxPrice;
    }

    /**
     * Retrieves the minimum price among all car accessories in the inventory.
     *
     * @return the minimum price
     */
    public static double minimumPrice() {
        double minPrice = Double.POSITIVE_INFINITY;
        for (CarAccessory carAccessory : inventory) {
            if (carAccessory.getPrice() < minPrice) minPrice = carAccessory.getPrice();
        }
        return minPrice;
    }

    /**
     * Adds a car accessory to the inventory.
     *
     * @param carAccessory the car accessory to add
     */
    public void addItem(CarAccessory carAccessory) {
        inventory.add(carAccessory);
    }

    /**
     * Finds all car accessories that match the desired car seat cover specifications.
     *
     * @param desiredCarSeatCover the desired car seat cover specifications
     * @return a list of matching car accessories
     */
    public List<CarAccessory> findMatch(CarAccessorySpecs desiredCarSeatCover) {
        List<CarAccessory> matchingSeatCovers = new ArrayList<>();
        for (CarAccessory carAccessory : inventory) {
            if (!desiredCarSeatCover.matches(carAccessory.getCarAccessorySpecs())) continue;
            if (carAccessory.getPrice() < desiredCarSeatCover.getMinPrice() || carAccessory.getPrice() > desiredCarSeatCover.getMaxPrice())
                continue;
            matchingSeatCovers.add(carAccessory);
        }
        return matchingSeatCovers;
    }

}
