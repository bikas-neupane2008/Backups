import java.text.DecimalFormat;

public class CarAccessory {

    private final String productName;
    private final long productCode;
    private final double price;
    private final String description;
    private final CarAccessorySpecs carAccessorySpecs;

    /**
     * Constructs a CarAccessory object with the specified details.
     *
     * @param productName      the name of the car accessory
     * @param productCode      the code of the car accessory
     * @param price            the price of the car accessory
     * @param description      the description of the car accessory
     * @param carAccessorySpecs the specifications of the car accessory
     */
    public CarAccessory(String productName, long productCode, double price, String description, CarAccessorySpecs carAccessorySpecs) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.description = description;
        this.carAccessorySpecs = carAccessorySpecs;
    }

    /**
     * Returns the name of the car accessory.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Returns the code of the car accessory.
     *
     * @return the product code
     */
    public long getProductCode() {
        return productCode;
    }

    /**
     * Returns the price of the car accessory.
     *
     * @return the product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the description of the car accessory.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the specifications of the car accessory.
     *
     * @return the car accessory specifications
     */
    public CarAccessorySpecs getCarAccessorySpecs() {
        return carAccessorySpecs;
    }

    /**
     * Returns the information of the car accessory with the specified filters.
     *
     * @param filter the filters to apply
     * @return the information of the car accessory
     */
    public String getInformation(Filter[] filter) {
        DecimalFormat df = new DecimalFormat("0.00");
        return "\nItem name: " + this.getProductName() + "\nProduct code: " + this.getProductCode() + getCarAccessorySpecs().getSpecInfo(filter) + "\nPrice: $" + df.format(this.getPrice()) + "\nDescription: " + this.getDescription() + "\n";

    }
}
