package inventory.repository;

import inventory.model.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Bungardean Tudor-Ionut
 */
class WBATests {

    private InventoryRepository inventoryRepository;

    @BeforeEach
    void initialize(){
        this.inventoryRepository = new InventoryRepository("data/mockTests");
    }

    @Test
    void F02_TC01() {
        assert(this.inventoryRepository.getInventoryCRUD().lookupProduct("")==null);
    }

    @Test
    void F02_TC02(){
        //assert(this.inventoryRepository.getInventoryCRUD().lookupProduct("prod") instanceof Product);
    }

    @Test
    void F02_TC03(){

        //assert(this.inventoryRepository.getInventoryCRUD().lookupProduct("1") instanceof Product);
    }

    @Test
    void F02_TC04(){
        assert(this.inventoryRepository.getInventoryCRUD().lookupProduct("prodInexistent") == null);
    }


}