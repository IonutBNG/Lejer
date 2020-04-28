package inventory.service.validators;

import inventory.model.Part;

public class PartValidator {

    public PartValidator(){}

    public String isValid(String name, double price, int inStock, int min, int max) {

        String errorMessage="";
        if(name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if(name.length()>255){
            errorMessage += "Name must be under 255 characters. ";
        }
        if(price < 0.01) {
            errorMessage += "The price must be equal to or greater than to 0.01. ";
        }
        if(inStock < 1) {
            errorMessage += "Inventory level must be greater than 0. ";
        }
        if(min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        return errorMessage;
    }
    public String isInhousePartValid(Part part) {
        String name = part.getName();
        double price = part.getPrice();
        int inStock = part.getInStock();
        int min = part.getMin();
        int max = part.getMax();
        String errorMessage="";
        if(name.equals("")) {
            errorMessage += "A name has not been entered. ";
        }
        if(name.length()>255){
            errorMessage += "Name must be under 255 characters. ";
        }
        if(price < 0.01) {
            errorMessage += "The price must be equal to or greater than to 0.01. ";
        }
        if(inStock < 1) {
            errorMessage += "Inventory level must be greater than 0. ";
        }
        if(min > max) {
            errorMessage += "The Min value must be less than the Max value. ";
        }
        if(inStock < min) {
            errorMessage += "Inventory level is lower than minimum value. ";
        }
        if(inStock > max) {
            errorMessage += "Inventory level is higher than the maximum value. ";
        }
        return errorMessage;
    }


}
