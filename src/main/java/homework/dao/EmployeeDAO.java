package homework.dao;

import homework.model.Employee;

import java.sql.*;

/**
 * Created on 29.04.2017.
 */
public class EmployeeDAO extends DAO<Employee> {

    private final PreparedStatement updateSalaryStatement;
    private final PreparedStatement getMostPaidEmployeeStatement;
    private final PreparedStatement insertEmployeeStatement;
    private final PreparedStatement deleteEmployeeStatement;
    private final PreparedStatement findEmployeeByIDStatement;

    public EmployeeDAO(Connection connection) throws SQLException {
        super(connection);

        getMostPaidEmployeeStatement = connection.prepareStatement(
                "select * from employees where (salary = (select max(salary) from employees))"
        );
        updateSalaryStatement = connection.prepareStatement(
                "update employees set salary = ? where id = ?"
        );
        insertEmployeeStatement = connection.prepareStatement(
                "insert into employees " +
                        "(first_name, last_name, patr_name, dob, phone_number, hired_at, salary) " +
                        "values (?, ?, ?, ?, ?, ?, ?)"
        );
        deleteEmployeeStatement = connection.prepareStatement(
                "delete from employees where id = ?"
        );
        findEmployeeByIDStatement = connection.prepareStatement(
                "select * from employees where id = ?"
        );
    }

    public Employee createEmployee(String name, String lastName, String patronymicName,
                                   java.util.Date dateOfBirth,
                                   String phoneNumber,
                                   double salary) throws SQLException {
        Date dob = new Date(dateOfBirth.getTime());

        insertEmployeeStatement.setString(1, name);
        insertEmployeeStatement.setString(2, lastName);
        insertEmployeeStatement.setString(3, patronymicName);
        insertEmployeeStatement.setDate(4, dob);
        insertEmployeeStatement.setString(5, phoneNumber);
        Date hiredAt = new Date(new java.util.Date().getTime());
        insertEmployeeStatement.setDate(6, hiredAt);
        insertEmployeeStatement.setDouble(7, salary);

        return new Employee(executeInsert(insertEmployeeStatement),
                name, lastName, patronymicName, dob, phoneNumber, hiredAt, salary);
    }

    public Employee getMostPaidEmployee() throws SQLException {
        return one(getMostPaidEmployeeStatement.executeQuery());
    }

    public void updateSalary(int id, double newSalary) throws SQLException {
        updateSalaryStatement.setDouble(1, newSalary);
        updateSalaryStatement.setInt(2, id);
        updateSalaryStatement.executeUpdate();
    }

    @Override
    public Employee findByID(int id) throws SQLException {
        findEmployeeByIDStatement.setInt(1, id);
        return one(findEmployeeByIDStatement.executeQuery());
    }

    @Override
    public void deleteByID(int id) throws SQLException {
        deleteEmployeeStatement.setInt(1, id);
        deleteEmployeeStatement.executeUpdate();
    }

    @Override
    protected Employee one(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("patr_name"),
                resultSet.getDate("dob"),
                resultSet.getString("phone_number"),
                resultSet.getDate("hired_at"),
                resultSet.getDouble("salary")
        );
    }
}
