// Part 4 of the Assignment
import java.util.ArrayList;
import java.util.List;

public class Inventory {
    // A field that stores a collection of CarSeatCover objects
    private final List<CarSeatCover> allCarSeatCover = new ArrayList<>();

    // A method to add a CarSeatCover to the field.

    /**
     * method to add a CarSeatCover object to the field
     *
     * @param carSeatCover a CarSeatCover object
     */
    public void addCarSeatCover(CarSeatCover carSeatCover) {
        this.allCarSeatCover.add(carSeatCover);
    }

    // A method to find which car seat covers in the inventory match the user's 'dream' car seat cover.
    // This method should have a CarSeatCover parameter, and search the field (collection of CarSeatCover objects)
    // for CarSeatCovers that match the parameter.
    // It should return a collection of matching CarSeatCovers.

    /**
     * returns a collection of CarSeatCover objects that meet all the user's requirements
     *
     * @param carSeatCoverCriteria a CarSeatCover object representing a user's preferred car seat cover
     * @return a CarSeatCover object
     */
    public List<CarSeatCover> findMatch(CarSeatCover carSeatCoverCriteria) {
        List<CarSeatCover> compatibleCarSeatCovers = new ArrayList<>();
        for (CarSeatCover carSeatCover : this.allCarSeatCover) {
            if (!carSeatCover.getTypes().equals(carSeatCoverCriteria.getTypes())) {
                continue;
            }
            if (!carSeatCover.getTheme().equals(carSeatCoverCriteria.getTheme())) {
                continue;
            }
            if (carSeatCover.getPrice() < carSeatCoverCriteria.getMinPrice() || carSeatCover.getPrice() > carSeatCoverCriteria.getMaxPrice()) {
                continue;
            }
            compatibleCarSeatCovers.add(carSeatCover);
        }
        return compatibleCarSeatCovers;
    }
}