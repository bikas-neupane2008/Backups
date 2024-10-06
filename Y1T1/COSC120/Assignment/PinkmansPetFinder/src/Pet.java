import java.text.DecimalFormat;

public class Pet {

    //fields
    private final String name;
    private final long microchipNumber;
    private final int age;
    private final DreamPet dreamPet;
    private final double adoptionFee;

    /**
     * constructor takes parameters to initialise the Pet object
     * @param name String representing the pet's name
     * @param microchipNumber unique 9 digit number
     * @param age int representing pet's age
     * @param adoptionFee a double representing the adoption fee for this pet
     * @param dreamPet a DreamPet object (could be cat or dog) representing this Pet's generic features
     */
    public Pet(String name, long microchipNumber, int age, double adoptionFee, DreamPet dreamPet){
        this.name=name;
        this.microchipNumber=microchipNumber;
        this.age=age;
        this.adoptionFee=adoptionFee;
        this.dreamPet=dreamPet;
    }

    //getters to access the fields
    public String getName() {
        return name;
    }
    public long getMicrochipNumber() {
        return microchipNumber;
    }
    public int getAge() {
        return age;
    }
    public DreamPet getDreamPet() {
        return dreamPet;
    }
    public double getAdoptionFee() {
        return adoptionFee;
    }

    /**
     * method to return a description of a Pet, including all its unique and generic features
     * @return String
     */
    public String getPetDescription(Criteria[] criteria){
        DecimalFormat df = new DecimalFormat("0.00");
        return this.getName()+" ("+this.getMicrochipNumber()+ ") is "+this.getAge()+" years old."+
                this.getDreamPet().getDreamPetDescription(criteria)+ ". \nAdoption fee: $"+df.format(this.getAdoptionFee())+"\n";
    }
}
