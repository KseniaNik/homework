package homework.dao;

import homework.model.Employee;
import homework.model.Office;
import homework.model.Order;
import homework.model.Service;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created on 29.04.2017.
 */
public class TestOrderDAO extends BaseTest {

    @Test
    public void testOrderDAO() throws Exception {
        EmployeeDAO employeeDAO = new EmployeeDAO(connection);
        Employee chosenEmployee = employeeDAO.createEmployee("emp1", "WE", "121",
                new Date(), "empty", 20000);

        OfficeDAO officeDAO = new OfficeDAO(connection);
        Office chosenOffice = officeDAO.createOffice("of3", "moskovskaya, 3");

        ServiceDAO serviceDAO = new ServiceDAO(connection);
        Service chosenService = serviceDAO.createService("ser2", 2.4);

        OrderDAO orderDAO = new OrderDAO(connection);
        Order order = orderDAO.createOrder("me", "", "",
                "PHONE NUMA",
                chosenService.getId(), chosenOffice.getId(), chosenEmployee.getId());

        assertEquals(orderDAO.findByID(order.getId()).getClientFirstName(), "me");
    }

}
