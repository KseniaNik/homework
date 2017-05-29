package homework.dao;

import homework.model.Employee;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;

import static homework.util.Util.toSqlDate;

/**
 * Created on 29.04.2017.
 */
public class EmployeeDAO extends DAO<Employee> {

    public EmployeeDAO(EntityManager manager) {
        super(Employee.class, manager);
    }

    public Employee createEmployee(String name, String lastName, String patronymicName,
                                   Date dateOfBirth,
                                   String phoneNumber,
                                   double salary) {
        Employee employee = new Employee();

        java.sql.Date dob = toSqlDate(dateOfBirth);
        java.sql.Date hiredAt = toSqlDate(new Date());

        employee.setFirstName(name);
        employee.setLastName(lastName);
        employee.setPatronymicName(patronymicName);
        employee.setDateOfBirth(dob);
        employee.setPhoneNumber(phoneNumber);
        employee.setHireDate(hiredAt);
        employee.setSalary(salary);

        manager.getTransaction().begin();
        manager.persist(employee);
        manager.getTransaction().commit();

        return employee;
    }

    public Employee findMostPaidEmployee() {
        TypedQuery<Employee> query = manager.createQuery(
                "select employee from Employee employee where employee.salary = (" +
                        "   select max(emp.salary) from Employee emp" +
                        ")",
                Employee.class
        );
        return query.getSingleResult();
    }

}
