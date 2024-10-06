import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dr Andreas Shepley for COSC120 on 27/02/2023
 */

public class Inventory {

    private final Set<CarSeatCover> inventory = new HashSet<>();

    public void addItem(CarSeatCover carSeatCover){
        this.inventory.add(carSeatCover);
    }

    public Set<String> getAllThemes(){
        Set<String> allThemes = new HashSet<>();
        for(CarSeatCover carSeatCover: inventory){
            allThemes.add(carSeatCover.getTheme());
        }
        return allThemes;
    }

    public List<CarSeatCover> findMatch(CarSeatCover desiredCarSeatCover){
        List<CarSeatCover> matchingSeatCovers = new ArrayList<>();
        for(CarSeatCover carSeatCover: inventory){
            if(!carSeatCover.getTheme().equals(desiredCarSeatCover.getTheme())) continue;
            if(!carSeatCover.getSeatCoverTypes().contains(desiredCarSeatCover.getDesiredType())) continue;
            if(carSeatCover.getPrice()<desiredCarSeatCover.getMinPrice()||carSeatCover.getPrice()>desiredCarSeatCover.getMaxPrice()) continue;
            matchingSeatCovers.add(carSeatCover);
        }
        return matchingSeatCovers;
    }

}
