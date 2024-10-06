import java.util.ArrayList;
import java.util.List;
public class Menu {
    private final List<Burger> allBurgers = new ArrayList<>();
    public void addCarSeatCover(Burger burger) {
        this.allBurgers.add(burger);
    }
    public List<Burger> findMatch(Burger burgerCriteria) {
        List<Burger> compatibleBurgers = new ArrayList<>();
        for (Burger burger : this.allBurgers) {
            if (!burger.getMeat().equals(burgerCriteria.getMeat())) {
                continue;
            }
            if (!burger.getBun().equals(burgerCriteria.getBun())) {
                continue;
            }
            if (burger.getPrice() < burgerCriteria.getMinPrice() || burger.getPrice() > burgerCriteria.getMaxPrice()) {
                continue;
            }
            compatibleBurgers.add(burger);
        }
        return compatibleBurgers;
    }
}