import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by Dr Andreas Shepley for COSC120 on 27/02/2023
 */

public class CarSeatCoverSearcher {

    private static final String filePath = "./inventory.txt";
    private static Inventory inventory;
    private static final String appName = "SuperGeek Auto";

    public static void main(String[] args) {
        inventory = loadInventory(filePath);
        CarSeatCover dreamSeatCover = getFilters();
        processSearchResults(dreamSeatCover);
        System.exit(0);
    }

    public static CarSeatCover getFilters(){
        Type type = (Type) JOptionPane.showInputDialog(null,"Please select your preferred type:",appName, JOptionPane.QUESTION_MESSAGE,null, Type.values(), Type.U30);
        if(type ==null)System.exit(0);

        String theme = (String) JOptionPane.showInputDialog(null,"Please select your preferred theme:",appName, JOptionPane.QUESTION_MESSAGE,null,inventory.getAllThemes().toArray(), "");
        if(theme==null)System.exit(0);

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
        CarSeatCover carSeatCover = new CarSeatCover("",0,0,theme,"",new HashSet<>());
        carSeatCover.setDesiredType(type);
        carSeatCover.setMaxPrice(maxPrice);
        carSeatCover.setMinPrice(minPrice);
        return carSeatCover;
    }

    public static void processSearchResults(CarSeatCover dreamCarSeatCover){
        List<CarSeatCover> matchingSeatCovers = inventory.findMatch(dreamCarSeatCover);
        if(matchingSeatCovers.size()>0) {
            Map<String, CarSeatCover> options = new HashMap<>();
            StringBuilder infoToShow = new StringBuilder("Matches found!! The following car seat covers meet your criteria: \n");
            for (CarSeatCover matchingCover : matchingSeatCovers) {
                infoToShow.append(matchingCover.getCarSeatCoverInformation());
                options.put(matchingCover.getProductName(), matchingCover);
            }
            String choice = (String) JOptionPane.showInputDialog(null, infoToShow + "\n\nPlease select which car seat cover you'd like to order:", appName, JOptionPane.INFORMATION_MESSAGE, null, options.keySet().toArray(), "");
            if(choice==null) System.exit(0);
            CarSeatCover chosenCover = options.get(choice);
            submitOrder(getUserContactInfo(),chosenCover,dreamCarSeatCover.getDesiredType().name());
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

    public static void submitOrder(Geek geek, CarSeatCover carSeatCover, String type) {
        String filePath = geek.getName().replace(" ","_")+"_"+carSeatCover.getProductCode()+".txt";
        Path path = Path.of(filePath);
        String lineToWrite = "Order details:\n\t" +
                "Name: "+geek.getName()+
                "\n\tPhone number: 0"+geek.getPhoneNumber()+
                "\n\tItem: "+carSeatCover.getProductName()+" ("+carSeatCover.getProductCode()+")" +
                "\n\tType: "+ type;
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
            String typesRaw = info[1].replace("]","");
            String description = info[2].replace("]","");

            String name = singularInfo[0];

            long productCode = 0;
            try{
                productCode = Long.parseLong(singularInfo[1]);
            }catch (NumberFormatException n) {
                System.out.println("Error in file. Product code could not be parsed for seat cover on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            double price = 0;
            try{
                price = Double.parseDouble(singularInfo[2]);
            }catch (NumberFormatException n){
                System.out.println("Error in file. Price could not be parsed for seat cover on line "+(i+1)+". Terminating. \nError message: "+n.getMessage());
                System.exit(0);
            }

            String theme = singularInfo[3];

            Set<Type> types = new HashSet<>();
            Type type = null;
            for(String s: typesRaw.split(",")){
                try {
                    type = Type.valueOf(s.toUpperCase());
                }catch (IllegalArgumentException e){
                    System.out.println("Error in file. Type data could not be parsed for seat cover on line "+(i+1)+". Terminating. \nError message: "+e.getMessage());
                    System.exit(0);
                }
                types.add(type);
            }

            CarSeatCover carSeatCover = new CarSeatCover(name,productCode,price,theme,description, types);
            inventory.addItem(carSeatCover);
        }
        return inventory;
    }



}
















































