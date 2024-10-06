import java.util.HashMap;
import java.util.Map;

public class DreamPet {

    private final Map<Criteria,Object> criteria;
    private final int minAge;
    private final int maxAge;

    /**
     * constructor used to create a user's conceptual 'dream pet', with an age range
     * @param criteria a Map representing the criteria used to compare pets
     * @param minAge lowest age user would be willing to adopt
     * @param maxAge highest age user would be willing to adopt
     */
    public DreamPet(Map<Criteria,Object> criteria,int minAge, int maxAge) {
        if(criteria==null) this.criteria=new HashMap<>();
        else this.criteria = new HashMap<>(criteria);
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    /**
     * create DreamPet object without age-range,representing real pet's generic features, read from file
     * @param criteria a Map representing the criteria used to compare pets
     */
    public DreamPet(Map<Criteria,Object> criteria) {
        if(criteria==null) this.criteria=new HashMap<>();
        else this.criteria = new HashMap<>(criteria);
        this.minAge=-1;
        this.maxAge=-1;
    }

    //getters
    public int getMinAge() {
        return minAge;
    }
    public int getMaxAge() {
        return maxAge;
    }
    public Map<Criteria, Object> getAllCriteria() {
        return new HashMap<>(criteria);
    }
    public Object getCriteria(Criteria key){return getAllCriteria().get(key);}

    /**
     * method to return a description of generic DreamPet features
     * @return a String description of the dream pet criteria
     */
    public String getDreamPetDescription(Criteria[] criteria){
        StringBuilder description= new StringBuilder();
        for(Criteria key: criteria) description.append("\n > ").append(key).append(": ").append(getCriteria(key));
        return description.toString();
    }

    /**
     * method to compare two DreamPet objects against each other for compatibility
     * @param petCriteria an imaginary pet representing the user's criteria
     * @return true if matches, false if not
     */
    public boolean compareDreamPets(DreamPet petCriteria) {
        for(Criteria key : petCriteria.getAllCriteria().keySet()) {
            if(!getCriteria(key).equals(petCriteria.getCriteria(key))) return false;
        }
        return true;
    }

}

