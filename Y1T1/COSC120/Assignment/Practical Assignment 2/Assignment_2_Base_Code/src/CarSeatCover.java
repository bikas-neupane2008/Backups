import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dr Andreas Shepley for COSC120 on 25/02/2023
 */

public class CarSeatCover {

    private final String productName;
    private final long productCode;
    private final double price;
    private final String theme;
    private final String description;
    private final Set<Type> seatCoverTypes;
    private Type desiredType;
    private double minPrice;
    private double maxPrice;

    public CarSeatCover(String productName, long productCode, double price, String theme, String description, Set<Type> seatCoverTypes) {
        this.productName=productName;
        this.productCode = productCode;
        this.price = price;
        this.theme = theme;
        this.description = description;
        this.seatCoverTypes = new HashSet<>(seatCoverTypes);
    }

    public String getProductName() {
        return productName;
    }
    public long getProductCode() {
        return productCode;
    }
    public double getPrice() {
        return price;
    }
    public String getTheme() {
        return theme;
    }
    public String getDescription() {
        return description;
    }
    public Set<Type> getSeatCoverTypes() {
        return new HashSet<>(seatCoverTypes);
    }
    public Type getDesiredType() {
        return desiredType;
    }
    public double getMinPrice() {
        return minPrice;
    }
    public double getMaxPrice() {
        return maxPrice;
    }

    public void setDesiredType(Type desiredType) {
        this.desiredType = desiredType;
    }
    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getCarSeatCoverInformation(){
        DecimalFormat df = new DecimalFormat("0.00");
        return "\nItem name: "+this.getProductName()+"\nCaption: "+this.getDescription() +
                "\n\tProduct code: "+this.getProductCode()+"\n\tTheme: "
                +this.getTheme()+"\n\tPrice: $"+df.format(this.getPrice())+"\n";
    }
}
