import java.util.*;

public class AllPets {

    ArrayList<Pet> allPets = new ArrayList<>();

    /**
     * method to add a Pet object to ArrayList
     * @param pet a Pet object representing a real pet
     */
    public void addPet(Pet pet){
        this.allPets.add(pet);
    }

    /**
     * a method to return a set of all cat/dog breeds in the dataset (no duplicates)
     * @param type a String representing the type of pet the user is interested in (Cat or Dog)
     * @return Set</String> of available breeds
     */
    public Set<String> getAllBreeds(Type type){
        Set<String> allBreeds = new TreeSet<>();
        for(Pet p: allPets){
            if(type.equals(p.getDreamPet().getCriteria(Criteria.TYPE))) allBreeds.add((String) p.getDreamPet().getCriteria(Criteria.BREED));
        }
        //allBreeds.add("NA"); - no longer needed //Part 1.1
        return allBreeds;
    }

    //EDIT Part 1.3
    /**
     * @return the value of the oldest pet available for adoption
     */
    public int oldestPet(){
        int oldest = 0;
        for(Pet pet: allPets){
            if(pet.getAge()>oldest) oldest = pet.getAge();
        }
        return oldest;
    }

    /**
     * a method used to map pet type to relevant breeds
     * @return a Map<Type,Set</String> containing the mapping
     */
    public Map<Type,Set<String>> getTypeToBreedMapping(){
        Map<Type,Set<String>> typeToBreed = new HashMap<>();
        for(Pet pet: allPets){
            Type type = (Type) pet.getDreamPet().getCriteria(Criteria.TYPE);
            if(!typeToBreed.containsKey(type)) typeToBreed.put(type,this.getAllBreeds(type));
        }
        return typeToBreed;
    }

    /**
     * use to improve functionality by returning ALL compatible pets
     * @param petCriteria a Pet object representing a user's preferred Pet
     * @return an ArrayList</Pet> of compatible Pets
     */
    public ArrayList<Pet> findMatch(DreamPet petCriteria){
        ArrayList<Pet> compatiblePets = new ArrayList<>();
        for(Pet pet: this.allPets){
            if(!pet.getDreamPet().compareDreamPets(petCriteria)) continue;
            if(pet.getAge()<petCriteria.getMinAge() || pet.getAge()> petCriteria.getMaxAge()) continue;
            compatiblePets.add(pet);
        }
        return compatiblePets;
    }

}
