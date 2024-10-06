import java.text.DecimalFormat;

/**
 * Created by Dr Andreas Shepley for COSC120 on 25/02/2023
 */

public class CarAccessory {

    private final String productName;
    private final long productCode;
    private final double price;
    private final String description;
    private final CarAccessorySpecs carAccessorySpecs;


    public CarAccessory(String productName, long productCode, double price, String description, CarAccessorySpecs carAccessorySpecs) {
        this.productName=productName;
        this.productCode = productCode;
        this.price = price;
        this.description = description;
        this.carAccessorySpecs = carAccessorySpecs;
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
    public String getDescription() {
        return description;
    }
    public CarAccessorySpecs getCarAccessorySpecs(){return carAccessorySpecs;}

    public String getInformation(){
        DecimalFormat df = new DecimalFormat("0.00");
        return "\nItem name: "+this.getProductName()+ "\n\tProduct code: "+this.getProductCode()+
                getCarAccessorySpecs().getSpecInfo()+
                "\n\tPrice: $"+df.format(this.getPrice())+
                "\nDescription: "+this.getDescription()+"\n";

    }
}
