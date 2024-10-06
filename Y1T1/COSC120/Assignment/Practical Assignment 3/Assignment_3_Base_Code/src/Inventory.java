import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dr Andreas Shepley for COSC120 on 27/02/2023
 */

public class Inventory {

    private final Set<CarAccessory> inventory = new HashSet<>();

    public void addItem(CarAccessory carAccessory){
        this.inventory.add(carAccessory);
    }

    public Set<String> getAllThemes(){
        Set<String> allThemes = new HashSet<>();
        for(CarAccessory carAccessory : inventory){
            allThemes.add((String) carAccessory.getCarAccessorySpecs().getFilter(Filter.THEME)); //change to access via filter
        }
        allThemes.add("NA");
        return allThemes;
    }

    public List<CarAccessory> findMatch(CarAccessorySpecs desiredCarSeatCover){
        List<CarAccessory> matchingSeatCovers = new ArrayList<>();
        for(CarAccessory carAccessory : inventory){
            if(!desiredCarSeatCover.matches(carAccessory.getCarAccessorySpecs())) continue;
            if(carAccessory.getPrice()<desiredCarSeatCover.getMinPrice()|| carAccessory.getPrice()>desiredCarSeatCover.getMaxPrice()) continue;
            matchingSeatCovers.add(carAccessory);
        }
        return matchingSeatCovers;
    }

}
