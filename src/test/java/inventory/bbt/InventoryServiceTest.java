package inventory.bbt;

import inventory.model.InhousePart;
import inventory.repository.InventoryRepository;
import inventory.repository.RepositoryException;
import inventory.service.InventoryService;
import inventory.service.validators.ValidatorException;
import org.junit.jupiter.api.*;

/**
 * @author Bungardean Tudor-Ionut
 */

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class InventoryServiceTest {

    private InhousePart inhousePart;
    private InventoryService inventoryService;
    private InventoryRepository inventoryRepository;
    private int itemsNo;


    @BeforeEach
    void init() {
        this.inventoryRepository = new InventoryRepository("data/mockTests");
        this.inventoryService = new InventoryService(this.inventoryRepository);
        this.itemsNo = this.inventoryRepository.getInventoryCRUD().getAllParts().size();
    }

    @AfterEach
    void destroy(){
        this.inhousePart = null;
    }


    @RepeatedTest(value = 3, name = "{displayName} {currentRepetition}/{totalRepetitions}")
    @DisplayName("Happy flow")
    void TC1_ECP() throws ValidatorException, RepositoryException {
        this.inventoryService.addInhousePart( "Aleena", 12, 30, 5, 35, 20);
        assert(this.itemsNo!=this.inventoryRepository.getInventoryCRUD().getAllParts().size());
    }

    @Test
    @Tag("TC2")
    void TC2_ECP() {
        Assertions.assertThrows(ValidatorException.class, ()-> this.inventoryService.addInhousePart( "", 12, 30, 5, 35, 20));
    }

    @Test
    void TC3_ECP() {
        Assertions.assertThrows(NumberFormatException.class, ()-> this.inventoryService.addInhousePart( "Piesa", Integer.parseInt("2q"), 30, 5, 35, 20));
    }

    @Test
    void TC4_ECP() {
        Assertions.assertThrows(ValidatorException.class, ()-> this.inventoryService.addInhousePart( "Piesa3", 0, 30, 5, 35, 20));
    }

    @Test
    @Disabled
    void TC5_ECP() throws ValidatorException, RepositoryException {
        this.inventoryService.addInhousePart( "Aleena", 12, 22, 2, 25, 15);
        assert(this.itemsNo!=this.inventoryRepository.getInventoryCRUD().getAllParts().size());

    }

    @Test
    void TC3_BVA() throws ValidatorException, RepositoryException {
        this.inventoryService.addInhousePart("M", 33, 4, 1, 10, 28);
        assert(this.itemsNo!=this.inventoryRepository.getInventoryCRUD().getAllParts().size());
    }

    @Test
    void TC4_BVA() throws ValidatorException, RepositoryException {
        this.inventoryService.addInhousePart(this.getAlphaNumericString(255), 12, 1,1,3,10);
        assert(this.itemsNo!=this.inventoryRepository.getInventoryCRUD().getAllParts().size());
    }

    @Test
    void TC5_BVA() throws ValidatorException, RepositoryException {
        this.inventoryService.addInhousePart(this.getAlphaNumericString(254), 12, 1,1,3,10);
        assert(this.itemsNo!=this.inventoryRepository.getInventoryCRUD().getAllParts().size());
    }

    @Test
    void TC6_BVA() {
        Assertions.assertThrows(ValidatorException.class, ()-> this.inventoryService.addInhousePart(this.getAlphaNumericString(256), 12, 1,1,3,10));
    }

    @Test
    void TC7_BVA() throws ValidatorException, RepositoryException {
        this.inventoryService.addInhousePart("piesa", 0.01, 1,1,3,10);
        assert(this.itemsNo!=this.inventoryRepository.getInventoryCRUD().getAllParts().size());
    }

    @Test
    void TC9_BVA() throws ValidatorException, RepositoryException {
        this.inventoryService.addInhousePart("piesa", 1, 1,1,3,10);
        assert(this.itemsNo!=this.inventoryRepository.getInventoryCRUD().getAllParts().size());
    }



    private String getAlphaNumericString(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            int index = (int)(AlphaNumericString.length() * Math.random());

            sb.append(AlphaNumericString.charAt(index));
        }

        return sb.toString();
    }




}