package inventory.service;

import inventory.model.*;
import inventory.repository.InventoryRepository;
import inventory.repository.RepositoryException;
import inventory.service.validators.PartValidator;
import inventory.service.validators.ValidatorException;
import javafx.collections.ObservableList;

public class InventoryService {

    private InventoryRepository repo;
    private PartValidator partValidator;

    public InventoryService(InventoryRepository repo){
        this.repo =repo;
        this.partValidator = new PartValidator();
    }


    public void addInhousePart(String name, double price, int inStock, int min, int  max, int partDynamicValue) throws ValidatorException, RepositoryException {
        String error = partValidator.isValid(name, price, inStock, min, max);

        if(error.equals("")) {
            InhousePart inhousePart = new InhousePart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);
            repo.addPart(inhousePart);
        }
        else{
            throw new ValidatorException(error);
        }
    }
    public void addInhousePart1(InhousePart part) throws ValidatorException, RepositoryException {

        String error = partValidator.isInhousePartValid(part);

        if(error.equals("")) {
            repo.addPart(part);
        }
        else{
            throw new ValidatorException(error);
        }
    }

    public void addOutsourcePart(String name, double price, int inStock, int min, int  max, String partDynamicValue) throws RepositoryException {
        OutsourcedPart outsourcedPart = new OutsourcedPart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);
        repo.addPart(outsourcedPart);
    }

    public void addProduct(String name, double price, int inStock, int min, int  max, ObservableList<Part> addParts){
        Product product = new Product(repo.getAutoProductId(), name, price, inStock, min, max, addParts);
        repo.addProduct(product);
    }

    public ObservableList<Part> getAllParts() {
        return repo.getAllParts();
    }

    public ObservableList<Product> getAllProducts() {
        return repo.getAllProducts();
    }

    public Part lookupPart(String search) {
        return repo.lookupPart(search);
    }

    public Product lookupProduct(String search) {
        return repo.lookupProduct(search);
    }

    public void updateInhousePart(int partIndex, String name, double price, int inStock, int min, int max, int partDynamicValue) throws ValidatorException {
        String error = partValidator.isValid(name, price, inStock, min, max);
        if(error.equals("")) {
            InhousePart inhousePart = new InhousePart(name, price, inStock, min, max, partDynamicValue);
            repo.updatePart(partIndex, inhousePart);
        }
        else{
            throw new ValidatorException(error);
        }

    }

    public void updateOutsourcedPart(int partIndex, String name, double price, int inStock, int min, int max, String partDynamicValue){
        OutsourcedPart outsourcedPart = new OutsourcedPart(name, price, inStock, min, max, partDynamicValue);
        repo.updatePart(partIndex, outsourcedPart);
    }

    public void updateProduct(int productIndex, String name, double price, int inStock, int min, int max, ObservableList<Part> addParts){
        Product product = new Product(name, price, inStock, min, max, addParts);
        repo.updateProduct(productIndex, product);
    }

    public void deletePart(Part part){
        repo.deletePart(part);
    }

    public void deleteProduct(Product product){
        repo.deleteProduct(product);
    }

}
