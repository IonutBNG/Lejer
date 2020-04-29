package inventory;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryCRUD;
import inventory.repository.InventoryRepository;
import inventory.repository.RepositoryException;
import inventory.service.InventoryService;
import inventory.service.validators.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.times;

/**
 * @author Bungardean Tudor-Ionut
 */
public class IntegrationStepTwo {

    @Mock
    private InventoryCRUD inventoryCRUD;

    @InjectMocks
    private InventoryRepository inventoryRepository = new InventoryRepository("data/mockTests");

    private InventoryService inventoryService = new InventoryService(inventoryRepository);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void test01_add_part_valid() throws RepositoryException, ValidatorException {
        InhousePart part1 = new InhousePart("part1", 10.0, 3, 1, 5, 701);
        InhousePart part2 = new InhousePart("part2", 13.0, 103, 100, 500, 1);
        ObservableList<Part> observableList = FXCollections.observableArrayList(Arrays.asList(part2));

        Mockito.when(this.inventoryCRUD.getAllParts()).thenReturn(observableList);
        Mockito.doNothing().when(this.inventoryCRUD).addPart(part1);

        this.inventoryService.addInhousePart1(part1);

        Mockito.verify(this.inventoryCRUD, times(1)).addPart(part1);
        Mockito.verify(this.inventoryCRUD, times(2)).getAllParts();

        assert 1 == inventoryService.getAllParts().size();
    }

    @Test
    public void test02_delete_part() {
        InhousePart part1 = new InhousePart("part1", 10.0, 3, 1, 5, 701);

        ObservableList<Part> observableList = FXCollections.observableArrayList(Arrays.asList(part1));

        Mockito.when(this.inventoryCRUD.getAllParts()).thenReturn(observableList);
        Mockito.doNothing().when(this.inventoryCRUD).deletePart(part1);

        this.inventoryService.deletePart(part1);

        Mockito.verify(this.inventoryCRUD, times(1)).deletePart(part1);
        System.out.println(this.inventoryRepository.getAllParts().size());
        assert 1 == this.inventoryService.getAllParts().size();
    }
}
