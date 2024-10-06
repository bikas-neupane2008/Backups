import java.util.HashSet;
import java.util.Set;

/**
 * The CarSeatCoverSpecs class represents the specifications of a car seat cover,
 * including the type(s) of seats it can fit, the theme, the minimum and maximum prices,
 * the materials it is made from, the desired materials from the user and the desired type of seat cover.
 * It contains methods to access and modify these specifications.
 */
public class CarSeatCoverSpecs {
    private final Set<Type> seatCoverTypes;
    /**
     * The ENUM Materials is added in CarSeatCoverSpecs rather than in CarSeatCover
     * because it is a generic field
     * and separating generic fields into a separate class promotes better software design
     * and can help improve code quality, reusability, promoting encapsulation and maintainability, and testing.
     * The class CarSeatCoverSpecs is made to represent the generic fields of the CarSeatCover.
     */
    private final Set<Materials> materials;
    private final String theme;
    private double minPrice;
    private double maxPrice;
    private Type desiredType;
    private Materials desiredMaterials;

    /**
     * Constructs a new CarSeatCoverSpecs object with the given seat cover types, materials, theme, minimum price, and maximum price.
     *
     * @param seatCoverTypes The set of seat cover types that this car seat cover can fit
     * @param materials      The set of materials that this car seat is made from
     * @param theme          The theme of the car seat cover
     */
    public CarSeatCoverSpecs(Set<Type> seatCoverTypes, Set<Materials> materials, String theme) {
        this.seatCoverTypes = new HashSet<>(seatCoverTypes);
        this.materials = new HashSet<>(materials);
        this.theme = theme;
    }

    /**
     * Returns the theme of the car seat cover.
     *
     * @return The theme of the car seat cover
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Returns a new set of the materials that this car seat cover is made from.
     *
     * @return A new set of the materials that this car seat cover is made from
     */
    public Set<Materials> getMaterials() {
        return new HashSet<>(materials);
    }

    /**
     * Returns a new set of the seat cover types that this car seat cover can fit.
     *
     * @return A new set of the seat cover types that this car seat cover can fit
     */
    public Set<Type> getSeatCoverTypes() {
        return new HashSet<>(seatCoverTypes);
    }

    /**
     * Returns the minimum price of the car seat cover.
     *
     * @return The minimum price of the car seat cover
     */
    public double getMinPrice() {
        return minPrice;
    }

    /**
     * Sets the desired minimum price of seat cover for searching
     *
     * @param minPrice the desired minimum price of seat cover for searching
     */
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    /**
     * Returns the maximum price of the car seat cover.
     *
     * @return The maximum price of the car seat cover
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Sets the desired maximum price of seat cover for searching
     *
     * @param maxPrice the desired maximum price of seat cover for searching
     */
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    /**
     * Returns the desired type of seat cover.
     *
     * @return The desired type of seat cover
     */
    public Type getDesiredType() {
        return desiredType;
    }

    /**
     * Sets the desired type of seat cover.
     *
     * @param desiredType The desired type of seat cover
     */
    public void setDesiredType(Type desiredType) {
        this.desiredType = desiredType;
    }

    /**
     * Returns the desired materials of seat cover made from.
     *
     * @return The desired materials of seat cover made from
     */
    public Materials getDesiredMaterials() {
        return desiredMaterials;
    }

    /**
     * Sets the desired materials of seat cover.
     *
     * @param desiredMaterials The desired materials of seat cover
     */
    public void setDesiredMaterials(Materials desiredMaterials) {
        this.desiredMaterials = desiredMaterials;
    }

    /**
     * Returns a formatted description of generic CarSeatCover features
     *
     * @return a formatted description of generic CarSeatCover features
     */
    public String getCarSeatCoverSpecsInfo() {
        return "\n\tTheme: " + this.getTheme() + "\n\tMaterials: " + this.getMaterials();
    }

    /**
     * method to compare CarSeatCoverSpecs objects with the user's criteria
     *
     * @param carSeatCoverSpecsCriteria an imaginary car seat cover representing the user's criteria
     * @return true if matches, false if not
     */
    public boolean compareCarSeatCoverSpecs(CarSeatCoverSpecs carSeatCoverSpecsCriteria) {
        if (!this.getTheme().equals(carSeatCoverSpecsCriteria.getTheme())) return false;
        if (!this.getSeatCoverTypes().contains(carSeatCoverSpecsCriteria.getDesiredType())) return false;
        return this.getMaterials().contains(carSeatCoverSpecsCriteria.getDesiredMaterials());
    }
}