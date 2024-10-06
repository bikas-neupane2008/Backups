import java.util.*;

/**
 * Created by Dr Andreas Shepley for COSC120 on 29/03/2023
 */

public class CarAccessorySpecs {

    private final Map<Filter,Object> filterMap;
    private final double minPrice;
    private final double maxPrice;

    public CarAccessorySpecs(Map<Filter,Object> filterMap, double minPrice, double maxPrice) {
        this.minPrice=minPrice;
        this.maxPrice=maxPrice;
        this.filterMap=new LinkedHashMap<>(filterMap);
    }

    public CarAccessorySpecs(Map<Filter,Object> filterMap) {
        this.filterMap=new LinkedHashMap<>(filterMap);
        minPrice = -1;
        maxPrice = -1;
    }

    public Map<Filter, Object> getAllFilters() {
        return new HashMap<>(filterMap);
    }
    public Object getFilter(Filter key){return getAllFilters().get(key);}

    public double getMinPrice() {
        return minPrice;
    }
    public double getMaxPrice() {
        return maxPrice;
    }

    public String getSpecInfo(){
        StringBuilder description = new StringBuilder();
        for(Filter key: filterMap.keySet()) {
            description.append("\n").append(key).append(": ").append(getFilter(key));
        }
        return description.toString();
    }

    public boolean matches(CarAccessorySpecs carAccessorySpecs){
        for(Filter key : carAccessorySpecs.getAllFilters().keySet()) {
            if(this.getAllFilters().containsKey(key)){
                if(getFilter(key) instanceof Collection<?> && carAccessorySpecs.getFilter(key) instanceof Collection<?>){
                    Set<Object> intersect = new HashSet<>((Collection<?>) carAccessorySpecs.getFilter(key));
                    intersect.retainAll((Collection<?>) getFilter(key));
                    if(intersect.size()==0) return false;
                }
                else if(carAccessorySpecs.getFilter(key) instanceof Collection<?>){
                    Set<Object> items = new HashSet<>((Collection<?>) carAccessorySpecs.getFilter(key));
                    if(!items.contains(this.getFilter(key))) return false;
                }
                else if(!getFilter(key).equals(carAccessorySpecs.getFilter(key))) return false;
            }

        }
        return true;
    }

}
