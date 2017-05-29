package homework.dao;

import homework.model.*;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created on 29.04.2017.
 */
public class TestOrderDAO extends BaseTest {

    @Test
    public void testOrderDAO() throws Exception {
        Employee chosenEmployee = employeeDAO.createEmployee("emp1", "WE", "121",
                new Date(), "empty", 20000);
        Office chosenOffice = officeDAO.createOffice("of3", "moskovskaya, 3");
        Service chosenService = serviceDAO.createService("ser2", 2.4);
        Order order = orderDAO.createOrder("me", "", "",
                "PHONE NUMA",
                chosenService, chosenOffice, chosenEmployee,
                Collections.singletonList(
                        new Article("ball", 0x0, "")
                ));


        orderDAO.createOrder("me12313", "", "",
                "PHONE N2342UMA",
                chosenService, chosenOffice, chosenEmployee,
                Collections.singletonList(
                        new Article("wall", 0x0, "")
                ));


        assertEquals(orderDAO.findById(order.getId()).getClientFirstName(), "me");
    }

}
