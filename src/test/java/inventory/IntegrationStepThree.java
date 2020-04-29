package inventory.integration;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryCRUD;
import inventory.repository.InventoryRepository;
import inventory.repository.RepositoryException;
import inventory.service.InventoryService;
import inventory.service.validators.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

/**
 * @author Bungardean Tudor-Ionut
 */
public class IntegrationStepThree {

    private InventoryCRUD inventoryCRUD;

    private InventoryRepository inventoryRepository = new InventoryRepository("data/mockTests");

    private InventoryService inventoryService = new InventoryService(inventoryRepository);

    private int size = inventoryService.getAllParts().size();

    @Test
    public void test01_add_part_valid() throws ValidatorException, RepositoryException {
        InhousePart part1 = new InhousePart("part1",10.0,3,1,5,701);
        InhousePart part2 = new InhousePart("part2",13.0,103,100,500,1);
        ObservableList<Part> observableList = FXCollections.observableArrayList(Arrays.asList(part2));

//        Mockito.doNothing().when(this.inventoryRepository).addPart(part1);
//        Mockito.when(this.inventoryRepository.getAllParts()).thenReturn(observableList);

        this.inventoryService.addInhousePart1(part1);
//
//        Mockito.verify(this.inventoryRepository, times(1)).addPart(part1);
//        Mockito.verify(this.inventoryRepository, never()).getAllParts();

        assert size+1 == inventoryService.getAllParts().size();

    }

    @Test
    public void test02_add_part_invalid() throws ValidatorException, RepositoryException {
        InhousePart part1 = new InhousePart("part1",10.0,3,1,5,701);

        this.inventoryService.addInhousePart1(part1);

        Assertions.assertThrows(RepositoryException.class, ()-> this.inventoryService.addInhousePart1(part1));

    }
}
