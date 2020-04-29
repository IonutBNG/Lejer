package inventory;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.repository.RepositoryException;
import inventory.service.InventoryService;
import inventory.service.validators.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

/**
 * @author Bungardean Tudor-Ionut
 */
public class InventoryServiceMock {

    @Mock
    private InventoryRepository inventoryRepository = new InventoryRepository("data/mockTests");

    @InjectMocks
    private InventoryService inventoryService = new InventoryService(inventoryRepository);


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test01_add_part_valid() throws ValidatorException, RepositoryException {
        InhousePart part1 = new InhousePart("part1",10.0,3,1,5,701);
        InhousePart part2 = new InhousePart("part2",13.0,103,100,500,1);
        ObservableList<Part> observableList = FXCollections.observableArrayList(Arrays.asList(part2));

        Mockito.doNothing().when(this.inventoryRepository).addPart(part1);
        Mockito.when(this.inventoryRepository.getAllParts()).thenReturn(observableList);

        this.inventoryService.addInhousePart1(part1);

        Mockito.verify(this.inventoryRepository, times(1)).addPart(part1);
        Mockito.verify(this.inventoryRepository, never()).getAllParts();

        assert 1 == inventoryService.getAllParts().size();

    }

    @Test
    public void test02_add_part_invalid() throws ValidatorException, RepositoryException {

        InhousePart part1 = new InhousePart("part1",120.0,10,9,51,11);
        Mockito.doThrow(RepositoryException.class).when(this.inventoryRepository).addPart(part1);
        Assertions.assertThrows(RepositoryException.class, ()-> this.inventoryService.addInhousePart1(part1));

    }


}
