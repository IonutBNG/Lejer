package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.service.validators.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

/**
 * @author Bungardean Tudor-Ionut
 */
public class InventoryRepositoryMock {

    @Mock
    private InventoryCRUD inventoryCRUD = new InventoryCRUD();

    @InjectMocks
    private InventoryRepository inventoryRepository = new InventoryRepository("data/mockTests", inventoryCRUD);

    private int size = inventoryCRUD.getAllParts().size();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void test01_add_part_valid() throws RepositoryException {
        InhousePart part1 = new InhousePart("part1",10.0,3,1,5,701);
        InhousePart part2 = new InhousePart("part2",13.0,103,100,500,1);
        ObservableList<Part> observableList = FXCollections.observableArrayList(Arrays.asList(part2));

        Mockito.when(this.inventoryCRUD.getAllParts()).thenReturn(observableList);
        Mockito.doNothing().when(this.inventoryCRUD).addPart(part1);

        this.inventoryRepository.addPart(part1);

        Mockito.verify(this.inventoryCRUD, times(1)).addPart(part1);
        Mockito.verify(this.inventoryCRUD, times(2)).getAllParts();

        assert 1 == inventoryRepository.getAllParts().size();
    }


    @Test
    public void test02_delete_part() {
        InhousePart part1 = new InhousePart("part1",10.0,3,1,5,701);

        ObservableList<Part> observableList = FXCollections.observableArrayList(Arrays.asList(part1));

        Mockito.when(this.inventoryCRUD.getAllParts()).thenReturn(observableList);
        Mockito.doNothing().when(this.inventoryCRUD).deletePart(part1);

        this.inventoryRepository.deletePart(part1);

        Mockito.verify(this.inventoryCRUD, times(1)).deletePart(part1);
        System.out.println(this.inventoryRepository.getAllParts().size());
        assert 1== this.inventoryCRUD.getAllParts().size();
    }
}
