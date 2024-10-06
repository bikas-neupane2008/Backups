import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {
    private final List<Burger> allBurgers = new ArrayList<>();

    public void addBurger(Burger burger) {
        this.allBurgers.add(burger);
    }

    public Set<String> getAllBunTypes() {
        Set<String> allBunTypes = new HashSet<>();
        for (Burger b : allBurgers) {
            allBunTypes.add(b.getBun());
        }
        return allBunTypes;
    }

    public List<Burger> findMatch(Burger burgerCriteria) {
        List<Burger> compatibleBurgers = new ArrayList<>();
        for (Burger burger : this.allBurgers) {
            if (!burger.getBun().equals(burgerCriteria.getBun())) {
                continue;
            }
            if (!burger.getPatties().equals(burgerCriteria.getPatties())) {
                continue;
            }
            if (!burger.getSauces().containsAll(burgerCriteria.getSauces())) {
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