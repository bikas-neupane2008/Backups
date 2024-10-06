import java.util.HashSet;
import java.util.Set;
/**

 The CarSeatCoverSpecs class represents the specifications of a car seat cover, including the type(s) of seats it can fit,

 the theme, the minimum and maximum prices, and the desired type of seat cover. It contains methods to access and modify

 these specifications.
 */
public class CarSeatCoverSpecs {

    // Set of seat cover types that this car seat cover can fit
    private final Set<Type> seatCoverTypes;

    // The theme of the car seat cover
    private final String theme;

    // The minimum price of the car seat cover
    private final double minPrice;

    // The maximum price of the car seat cover
    private final double maxPrice;

    // The desired type of seat cover
    private Type desiredType;

    /**

     Constructs a new CarSeatCoverSpecs object with the given seat cover types, theme, minimum price, and maximum price.
     @param seatCoverTypes The set of seat cover types that this car seat cover can fit
     @param theme The theme of the car seat cover
     @param minPrice The minimum price of the car seat cover
     @param maxPrice The maximum price of the car seat cover
     */
    public CarSeatCoverSpecs(Set<Type> seatCoverTypes, String theme, double minPrice, double maxPrice) {
        this.seatCoverTypes = seatCoverTypes;
        this.theme = theme;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }
    /**

     Returns the theme of the car seat cover.
     @return The theme of the car seat cover
     */
    public String getTheme() {
        return theme;
    }
    /**

     Returns a new set of the seat cover types that this car seat cover can fit.
     @return A new set of the seat cover types that this car seat cover can fit
     */
    public Set<Type> getSeatCoverTypes() {
        return new HashSet<>(seatCoverTypes);
    }
    /**

     Returns the minimum price of the car seat cover.
     @return The minimum price of the car seat cover
     */
    public double getMinPrice() {
        return minPrice;
    }
    /**

     Returns the maximum price of the car seat cover.
     @return The maximum price of the car seat cover
     */
    public double getMaxPrice() {
        return maxPrice;
    }
    /**

     Returns the desired type of seat cover.
     @return The desired type of seat cover
     */
    public Type getDesiredType() {
        return desiredType;
    }
    /**

     Sets the desired type of seat cover.
     @param desiredType The desired type of seat cover
     */
    public void setDesiredType(Type desiredType) {
        this.desiredType = desiredType;
    }
}