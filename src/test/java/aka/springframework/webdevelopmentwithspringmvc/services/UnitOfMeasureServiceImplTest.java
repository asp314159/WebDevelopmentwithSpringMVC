package aka.springframework.webdevelopmentwithspringmvc.services;

import aka.springframework.webdevelopmentwithspringmvc.commands.UnitOfMeasureCommand;
import aka.springframework.webdevelopmentwithspringmvc.converters.UnitOfMeasureToUnitOfMeasureCommand;
import aka.springframework.webdevelopmentwithspringmvc.repositories.UnitOfMeasureRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by AP
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitOfMeasureServiceImplTest {

    @Autowired
    UnitOfMeasureServiceImpl unitOfMeasureService;

    @Autowired
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    public void listAllUoms() {

        Set<UnitOfMeasureCommand> commands = unitOfMeasureService.listAllUoms();

        assertEquals(8, commands.size());
    }


}