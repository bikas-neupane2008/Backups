import java.text.DecimalFormat;
import java.util.Set;
/**

 The CarSeatCover class represents a car seat cover product that includes the product name, product code, price,

 description, and specifications. It also provides a method to retrieve the car seat cover information.
 */
public class CarSeatCover {

    private final String productName;
    private final long productCode;
    private final double price;
    private final String description;
    private final CarSeatCoverSpecs carSeatCoverSpecs;

    /**

     Constructs a CarSeatCover object with the specified parameters.
     @param productName the name of the car seat cover product
     @param productCode the unique code assigned to the product
     @param price the price of the product
     @param theme the theme of the car seat cover
     @param description the description of the car seat cover
     @param seatCoverTypes the set of types of the car seat cover
     */
    public CarSeatCover(String productName, long productCode, double price, String theme, String description, Set<Type> seatCoverTypes) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.description = description;
        this.carSeatCoverSpecs = new CarSeatCoverSpecs(seatCoverTypes, theme, 0, 0);
    }
    /**

     Returns the name of the car seat cover product.
     @return the name of the car seat cover product
     */
    public String getProductName() {
        return productName;
    }
    /**

     Returns the unique code assigned to the car seat cover product.
     @return the product code of the car seat cover product
     */
    public long getProductCode() {
        return productCode;
    }
    /**

     Returns the price of the car seat cover product.
     @return the price of the car seat cover product
     */
    public double getPrice() {
        return price;
    }
    /**

     Returns the description of the car seat cover product.
     @return the description of the car seat cover product
     */
    public String getDescription() {
        return description;
    }
    /**

     Returns the specifications of the car seat cover product.
     @return the specifications of the car seat cover product
     */
    public CarSeatCoverSpecs getCarSeatCoverSpecs() {
        return carSeatCoverSpecs;
    }
    /**

     Returns a string containing the information of the car seat cover product including the name,
     product code, theme, price, and description.
     @return a string containing the information of the car seat cover product
     */
    public String getCarSeatCoverInformation() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "\nItem name: " + this.getProductName() + "\nCaption: " + this.getDescription() + "\n\tProduct code: " + this.getProductCode() + "\n\tTheme: " + this.getCarSeatCoverSpecs().getTheme() + "\n\tPrice: $" + df.format(this.getPrice()) + "\n";
    }
}