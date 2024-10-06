import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**

 The Inventory class represents an inventory of CarSeatCover objects.

 It allows adding a new item to the inventory, and finding a list of matching CarSeatCover items based on the desired specifications.
 */
public class Inventory {

    /**

     The set of CarSeatCover objects in the inventory.
     */
    private final Set<CarSeatCover> inventory = new HashSet<>();
    /**

     Adds a new CarSeatCover item to the inventory.
     @param carSeatCover the CarSeatCover item to be added to the inventory.
     */
    public void addItem(CarSeatCover carSeatCover) {
        this.inventory.add(carSeatCover);
    }
    /**

     Returns a set of all the unique themes available in the inventory.
     @return a set of Strings representing the unique themes available in the inventory.
     */
    public Set<String> getAllThemes() {
        Set<String> allThemes = new HashSet<>();
        for (CarSeatCover carSeatCover : inventory) {
            allThemes.add(carSeatCover.getCarSeatCoverSpecs().getTheme());
        }
        return allThemes;
    }
    /**

     Finds and returns a list of CarSeatCover items that match the desired specifications.
     @param desiredCarSeatCover a CarSeatCoverSpecs object representing the desired specifications.
     @return a list of CarSeatCover objects that match the desired specifications.
     */
    public List<CarSeatCover> findMatch(CarSeatCoverSpecs desiredCarSeatCover) {
        List<CarSeatCover> matchingSeatCovers = new ArrayList<>();
        for (CarSeatCover carSeatCover : inventory) {
            if (!carSeatCover.getCarSeatCoverSpecs().getTheme().equals(desiredCarSeatCover.getTheme())) continue;
            if (!carSeatCover.getCarSeatCoverSpecs().getSeatCoverTypes().contains(desiredCarSeatCover.getDesiredType()))
                continue;
            if (carSeatCover.getPrice() < desiredCarSeatCover.getMinPrice() || carSeatCover.getPrice() > desiredCarSeatCover.getMaxPrice())
                continue;
            matchingSeatCovers.add(carSeatCover);
        }
        return matchingSeatCovers;
    }
}