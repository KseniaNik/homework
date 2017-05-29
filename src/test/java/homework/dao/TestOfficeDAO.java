package homework.dao;

import homework.model.Office;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created on 29.04.2017.
 */
public class TestOfficeDAO extends BaseTest {

    @Test
    public void testOfficeDAO() throws Exception {

        officeDAO.createOffice("of1", "moskovskaya, 1");
        Office chosen = officeDAO.createOffice("of2", "moskovskaya, 2");

        Office found = officeDAO.findById(chosen.getId());

        assertEquals(chosen.getId(), found.getId());
        assertEquals(found.getName(), "of2");
    }
}
