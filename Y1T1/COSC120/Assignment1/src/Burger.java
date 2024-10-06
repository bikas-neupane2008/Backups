import java.text.DecimalFormat;
import java.util.Set;

public class Burger {
    private final String menuItemName;
    private final String menuItemID;
    private final double price;
    private final String bun;
    private final Meat meat;
    private final Set<Sauces> sauces;
    private final boolean cheese;
    private final boolean pickles;
    private final String description;
    private double minPrice;
    private double maxPrice;

    public Burger(String menuItemName, String menuItemID, double price, String bun, Meat meat, Set<Sauces> sauces, boolean cheese, boolean pickles, String description) {
        this.menuItemName = menuItemName;
        this.menuItemID = menuItemID;
        this.price = price;
        this.bun = bun;
        this.meat = meat;
        this.sauces = sauces;
        this.cheese = cheese;
        this.pickles = pickles;
        this.description = description;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public String getMenuItemID() {
        return menuItemID;
    }

    public double getPrice() {
        return price;
    }

    public String getBun() {
        return bun;
    }

    public Meat getPatties() {
        return meat;
    }

    public Set<Sauces> getSauces() {
        return sauces;
    }

    public boolean isCheese() {
        return cheese;
    }

    public boolean isPickles() {
        return pickles;
    }

    public String getDescription() {
        return description;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String productDescription() {
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuilder infoDescription = new StringBuilder("\n" + this.getMenuItemName() + " (" + this.getMenuItemID() + ")\n" + this.getDescription() + "\nIngredients: \nBun Type: " + this.getBun() + "\nMeat : " + this.getPatties() + "\nSauce/s: " + this.getSauces() + "\nOther: ");
        if (this.isCheese()) infoDescription.append("Cheese ");
        if (this.isPickles()) infoDescription.append("Pickle ");
        infoDescription.append("\nPrice : $").append(df.format(this.getPrice())).append("\n");
        return infoDescription.toString();
    }
}