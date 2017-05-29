package homework.dao;

import homework.model.Service;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created on 29.04.2017.
 */
public class TestServiceDAO extends BaseTest {

    @Test
    public void testServiceDAO() throws Exception {

        Service chosenService = serviceDAO.createService("ser123", 2.4);
        serviceDAO.createService("ser42", 2.4);
        serviceDAO.createService("ser1", 2.4);

        assertEquals(serviceDAO.findByName("ser123").getId(), chosenService.getId());
        assertEquals(serviceDAO.findById(chosenService.getId()).getName(), chosenService.getName());

    }

}
