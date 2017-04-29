package homework.dao;

import homework.model.Employee;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.assertEquals;

/**
 * Created on 29.04.2017.
 */
public class TestEmployeeDAO extends BaseTest {

    @Test
    public void testEmployeeDAO() throws Exception {
        EmployeeDAO employeeDAO = new EmployeeDAO(connection);
        employeeDAO.createEmployee("emp1", "WE", "121",
                new Date(), "empty", 20000);
        Employee chosenEmployee = employeeDAO.createEmployee("emp2", "WE", "121",
                new Date(), "empty", 50000);
        employeeDAO.createEmployee("emp3", "WE", "121",
                new Date(), "empty", 20000);
        employeeDAO.createEmployee("emp4", "WE", "121",
                new Date(), "empty", 20000);

        assertEquals(employeeDAO.getMostPaidEmployee().getId(), chosenEmployee.getId());
        assertEquals(employeeDAO.findByID(chosenEmployee.getId()).getFirstName(), chosenEmployee.getFirstName());
    }
}
