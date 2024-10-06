import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Dr Andreas Shepley for COSC120 on 27/02/2023
 */

public class CarAccessorySearcher {

    private static final String filePath = "./inventory_v3.txt";
    private static Inventory inventory;
    private static final String appName = "SuperGeek Auto";

    public static void main(String[] args) {
        inventory = loadInventory(filePath);
        CarAccessorySpecs dreamSeatCover = getFilters();
        processSearchResults(dreamSeatCover);
        System.exit(0);
    }

    public static CarAccessorySpecs getFilters(){
        Map<Filter,Object> filterMap = new HashMap<>();
        AccessoryType accessoryType = (AccessoryType) JOptionPane.showInputDialog(null,"Which car accessory would you like?",appName, JOptionPane.QUESTION_MESSAGE,null, AccessoryType.values(), AccessoryType.CAR_SEAT_COVER);
        if(accessoryType ==null)System.exit(0);
        filterMap.put(Filter.ACCESSORY_TYPE,accessoryType);

        String theme = (String) JOptionPane.showInputDialog(null,"Please select your preferred theme:",appName, JOptionPane.QUESTION_MESSAGE,null,inventory.getAllThemes().toArray(), "");
        if(theme==null)System.exit(0);
        if(!theme.equals("NA")) filterMap.put(Filter.THEME,theme);

        Material material = (Material) JOptionPane.showInputDialog(null,"Please select your preferred material:",appName, JOptionPane.QUESTION_MESSAGE,null, Material.values(), Material.POLYESTER);
        if(material ==null)System.exit(0);
        if(!material.equals(Material.NA)) filterMap.put(Filter.MATERIAL,material);

        if(accessoryType.equals(AccessoryType.CAR_SEAT_COVER)) {
            Type type = (Type) JOptionPane.showInputDialog(null, "Please select your preferred type:", appName, JOptionPane.QUESTION_MESSAGE, null, Type.values(), Type.U30);
            if (type == null) System.exit(0);
            filterMap.put(Filter.TYPE, type);
        }
        else{
            STWDiameter diameter = (STWDiameter) JOptionPane.showInputDialog(null, "Please select your preferred type:", appName, JOptionPane.QUESTION_MESSAGE, null, STWDiameter.values(), STWDiameter._380);
            if (diameter == null) System.exit(0);
            filterMap.put(Filter.DIAMETER, diameter);
        }

        int minPrice=-1,maxPrice = -1;
        while(minPrice<0) {
            String userInput = JOptionPane.showInputDialog(null, "Please enter the lowest price", appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null)System.exit(0);
            try {
                minPrice = Integer.parseInt(userInput);
                if(minPrice<0) JOptionPane.showMessageDialog(null,"Price must be >= 0.",appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        while(maxPrice<minPrice) {
            String userInput = JOptionPane.showInputDialog(null, "Please enter the highest price", appName, JOptionPane.QUESTION_MESSAGE);
            if(userInput==null)System.exit(0);
            try {
                maxPrice = Integer.parseInt(userInput);
                if(maxPrice<minPrice) JOptionPane.showMessageDialog(null,"Price must be >= "+minPrice,appName, JOptionPane.ERROR_MESSAGE);
            }
            catch (NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Please try again.", appName, JOptionPane.ERROR_MESSAGE);
            }
        }
        return new CarAccessorySpecs(filterMap,minPrice,maxPrice);
    }

    public static void processSearchResults(CarAccessorySpecs carAccessorySpecs){
        List<CarAccessory> matchingItems = inventory.findMatch(carAccessorySpecs);
        if(matchingItems.size()>0) {
            Map<String, CarAccessory> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following items meet your criteria: \n");
            for (CarAccessory matching : matchingItems) {
                infoToShow.append(matching.getInformation());
                options.put(matching.getProductName(), matching);
            }
            String choice = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which item you'd like to order:", appName, JOptionPane.INFORMATION_MESSAGE, null, options.keySet().toArray(), "");
            if(choice==null) System.exit(0);
            CarAccessory chosen = options.get(choice);
            submitOrder(getUserContactInfo(),chosen, carAccessorySpecs);
            JOptionPane.showMessageDialog(null,"Thank you! Your order has been submitted. "+
                    "One of our friendly staff will be in touch shortly.",appName, JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null,"Unfortunately none of our car seat covers meet your criteria :("+
                    "\n\tTo exit, click OK.",appName, JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static Geek getUserContactInfo(){
        String name = JOptionPane.showInputDialog(null,"Please enter your full name.",appName, JOptionPane.QUESTION_MESSAGE);
        if(name==null) System.exit(0);
        long phoneNumber=0;
        while(phoneNumber==0) {
            try {
                String userInput = JOptionPane.showInputDialog(null, "Please enter your phone number.", appName, JOptionPane.QUESTION_MESSAGE);
                if(userInput==null) System.exit(0);
                phoneNumber = Long.parseLong(userInput);
            } catch (NumberFormatException e) {
                phoneNumber = Long.parseLong(JOptionPane.showInputDialog(null, "Invalid entry. Please enter your phone number.", appName, JOptionPane.ERROR_MESSAGE));
            }
        }
        return new Geek(name,phoneNumber);
    }

    public static void submitOrder(Geek geek, CarAccessory carAccessory, CarAccessorySpecs dreamCarAccessory) {
        String filePath = geek.getName().replace(" ","_")+"_"+ carAccessory.getProductCode()+".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:\n\t" +
                "Name: "+geek.getName()+
                "\n\tPhone number: 0"+geek.getPhoneNumber()+
                "\n\tItem: "+ carAccessory.getProductName()+" ("+ carAccessory.getProductCode()+")" +
                "\n\tType: "+ dreamCarAccessory.getFilter(Filter.TYPE) +
                "\n\tDiameter: "+ dreamCarAccessory.getFilter(Filter.DIAMETER);
        try {
            Files.writeString(path, lineToWrite);
        }catch (IOException io){
            System.out.println("Order could not be placed. \nError message: "+io.getMessage());
            System.exit(0);
        }
    }

    public static Inventory loadInventory(String filePath) {
        Inventory inventory = new Inventory();
        Path path = Path.of(filePath);
        List<String> fileContents = null;
        try {
            fileContents = Files.readAllLines(path);
        }catch (IOException io){
            System.out.println("File could not be found");
            System.exit(0);
        }

        for(int i=1;i<fileContents.size();i++){
            String[] info = fileContents.get(i).split("\\[");
            String[] singularInfo = info[0].split(",");
            String materialsRaw = info[1].replace("]","");
            String typesRaw = info[2].replace("]","");
            String description = info[3].replace("]","");

            String name = singularInfo[0];

            AccessoryType accessoryType;
            if(name.toUpperCase().contains("STW")) accessoryType = AccessoryType.STEERING_WHEEL_COVER;
            else accessoryType = AccessoryType.CAR_SEAT_COVER;

            long productCode = 0;
            try{
                productCode = Long.parseLong(singularInfo[1]);
            }catch (NumberFormatException n) {
                System.out.println("Error in file. Product code could not be parsed for accessory on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            double price = 0;
            try{
                price = Double.parseDouble(singularInfo[2]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Price could not be parsed for accessory on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            String theme = singularInfo[3];

            STWDiameter diameter = null;
            if(!singularInfo[4].equals("NA")){
                try{
                    diameter = STWDiameter.valueOf("_"+singularInfo[4]);
                }catch (IllegalArgumentException e){
                    System.out.println("Error in file. Diameter could not be parsed for steering wheel on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                    System.exit(0);
            }}
            Set<Material> materials = new HashSet<>();
            for(String s: materialsRaw.split(",")){
                Material material = Material.POLYESTER;
                try {
                    material = Material.valueOf(s.strip().toUpperCase());
                }catch (IllegalArgumentException e){
                    System.out.println("Error in file. Material data could not be parsed for accessory on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                    System.exit(0);
                }
                materials.add(material);
            }

            Set<Type> types = new HashSet<>();
            for(String s: typesRaw.split(",")){
                Type type = null;
                if(!s.equalsIgnoreCase("NA")) {
                    try {
                        type = Type.valueOf(s.toUpperCase());
                    }catch (IllegalArgumentException e){
                        System.out.println("Error in file. Type data could not be parsed for seat cover on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                        System.exit(0);
                    }
                    types.add(type);
                }
            }

            Map<Filter,Object> filterMap = new LinkedHashMap<>();
            filterMap.put(Filter.ACCESSORY_TYPE,accessoryType);
            filterMap.put(Filter.THEME,theme);
            filterMap.put(Filter.MATERIAL,materials);
            if(diameter!=null) filterMap.put(Filter.DIAMETER,diameter);
            if(types.size()!=0) filterMap.put(Filter.TYPE,types);

            CarAccessorySpecs carAccessorySpecs = new CarAccessorySpecs(filterMap);
            CarAccessory carAccessory = new CarAccessory(name,productCode,price,description, carAccessorySpecs);
            inventory.addItem(carAccessory);
        }
        return inventory;
    }



}
















































