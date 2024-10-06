import java.util.*;

public class CarAccessorySpecs {

    private final Map<Filter, Object> filterMap;
    private final double minPrice;
    private final double maxPrice;

    /**
     * Constructs CarAccessorySpecs object with the specified filter map, minimum price, and maximum price.
     *
     * @param filterMap the map of filters and their corresponding values
     * @param minPrice  the minimum price
     * @param maxPrice  the maximum price
     */
    public CarAccessorySpecs(Map<Filter, Object> filterMap, double minPrice, double maxPrice) {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.filterMap = new LinkedHashMap<>(filterMap);
    }

    /**
     * Constructs CarAccessorySpecs object with the specified filter map.
     * The minimum price and maximum price are set to -1.
     *
     * @param filterMap the map of filters and their corresponding values
     */
    public CarAccessorySpecs(Map<Filter, Object> filterMap) {
        this.filterMap = new LinkedHashMap<>(filterMap);
        minPrice = -1;
        maxPrice = -1;
    }

    /**
     * Returns a new HashMap containing all the filters and their values.
     *
     * @return the copy of the filter map
     */
    public Map<Filter, Object> getAllFilters() {
        return new HashMap<>(filterMap);
    }

    /**
     * Returns the value associated with the specified filter.
     *
     * @param key the filter to retrieve the value for
     * @return the value of the filter
     */
    public Object getFilter(Filter key) {
        return getAllFilters().get(key);
    }

    /**
     * Returns the minimum price.
     *
     * @return the minimum price
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Returns the maximum price.
     *
     * @return the maximum price
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Generates a string representation of the filters and their values based on the specified filter array.
     *
     * @param filter the array of filters
     * @return the string representation of the filters
     */
    public String getSpecInfo(Filter[] filter) {
        StringBuilder description = new StringBuilder();
        for (Filter key : filter) {
            if (getFilter(key) != null) description.append("\n").append(key).append(": ").append(getFilter(key));
        }
        return description.toString();
    }

    /**
     * Checks if the car accessory specifications match the given car accessory specifications.
     *
     * @param carAccessorySpecs the car accessory specifications to match against
     * @return true if the specifications match, false otherwise
     */
    public boolean matches(CarAccessorySpecs carAccessorySpecs) {
        for (Filter key : carAccessorySpecs.getAllFilters().keySet()) {
            if (this.getAllFilters().containsKey(key)) {
                if (getFilter(key) instanceof Collection<?> && carAccessorySpecs.getFilter(key) instanceof Collection<?>) {
                    Set<Object> intersect = new HashSet<>((Collection<?>) carAccessorySpecs.getFilter(key));
                    intersect.retainAll((Collection<?>) getFilter(key));
                    if (intersect.size() == 0) return false;
                } else if (carAccessorySpecs.getFilter(key) instanceof Collection<?> && !(getFilter(key) instanceof Collection<?>)) {
                    if (!((Collection<?>) carAccessorySpecs.getFilter(key)).contains(getFilter(key))) return false;
                } else if (getFilter(key) instanceof Collection<?>) {
                    Set<Object> items = new HashSet<>((Collection<?>) getFilter(key));
                    if (!items.contains(carAccessorySpecs.getFilter(key))) return false;
                } else if (!getFilter(key).equals(carAccessorySpecs.getFilter(key))) return false;
            }
        }
        return true;
    }
}
