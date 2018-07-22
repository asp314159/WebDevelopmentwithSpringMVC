package aka.springframework.webdevelopmentwithspringmvc.converters;

import aka.springframework.webdevelopmentwithspringmvc.commands.UnitOfMeasureCommand;
import aka.springframework.webdevelopmentwithspringmvc.domain.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by AP
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long LONG_VALUE = 1L;

    @Autowired
    UnitOfMeasureToUnitOfMeasureCommand converter;

    @Test
    public void testNullObjectConvert() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);
        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }

}