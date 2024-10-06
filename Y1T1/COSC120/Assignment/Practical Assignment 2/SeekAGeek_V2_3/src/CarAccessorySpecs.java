import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The CarAccessorySpecs class represents the specifications of a car accessory,
 * which includes the type(s) of seats it can fit, the theme, the materials, the size of wheel seat cover in Map and then the minimum and maximum prices.
 * It contains methods to access and modify these specifications.
 */
public class CarAccessorySpecs {
    private final Map<Filter, Object> carAccessoryMap;
    private double minPrice;
    private double maxPrice;

    /**
     * constructor used to create car accessory features as per user criteria
     *
     * @param carAccessoryMap a mapping of filter to values
     */
    public CarAccessorySpecs(Map<Filter, Object> carAccessoryMap, double minPrice, double maxPrice) {
        this.carAccessoryMap = new HashMap<>(carAccessoryMap);
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * constructor used to create real car accessory features
     *
     * @param carAccessoryMap a mapping of filter to values
     */
    public CarAccessorySpecs(Map<Filter, Object> carAccessoryMap) {
        this.carAccessoryMap = new HashMap<>(carAccessoryMap);
    }

    /**
     * Returns the entire map of criteria keys and values
     *
     * @return the entire map of criteria keys and values
     */
    public Map<Filter, Object> getCarAccessoryMap() {
        return new HashMap<>(carAccessoryMap);
    }

    /**
     * provides the individual value of each key
     *
     * @param key a Criteria (enum) constant representing a search criteria
     * @return a value stored in the map at a given key
     */
    public Object getValueAtMap(Filter key) {
        return getCarAccessoryMap().get(key);
    }

    /**
     * Returns the minimum price range of the car accessory
     *
     * @return The minimum price range of the car accessory
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Returns the maximum price of the car accessory
     *
     * @return The maximum price of the car accessory
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * @return a formatted description of generic CarAccessory features
     */
    public String getCarAccessorySpecsDescription() {
        StringBuilder description = new StringBuilder();
        for (Filter key : carAccessoryMap.keySet()) {
            description.append("\n\t").append(key).append(": ").append(getValueAtMap(key));
        }
        return description.toString();
    }

    /**
     * method to compare CarAccessorySpecs objects with user desired criteria against each other for compatibility
     *
     * @param carAccessorySpecsCriteria an imaginary car accessory representing the user's criteria
     * @return true or false as per the match
     */
    public boolean compareCarAccessorySpecs(CarAccessorySpecs carAccessorySpecsCriteria) {
        for (Filter key : carAccessorySpecsCriteria.getCarAccessoryMap().keySet()) {
            if (this.getCarAccessoryMap().containsKey(key)) {
                // if car accessory key have instance of collection
                if (getValueAtMap(key) instanceof Collection<?>) {
                    if (!((Collection<?>) getValueAtMap(key)).contains(carAccessorySpecsCriteria.getValueAtMap(key)))
                        return false;
                }
                // if both car accessory key and user desired accessory criteria key don't have instance of collection
                else if (!getValueAtMap(key).equals(carAccessorySpecsCriteria.getValueAtMap(key))) return false;
            }
        }
        return true;
    }
}