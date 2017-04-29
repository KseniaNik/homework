package homework.dao;

import homework.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static homework.util.Util.toSqlDate;

/**
 * Created on 29.04.2017.
 */
public class EmployeeDAO extends DAO<Employee> {

    private final PreparedStatement updateSalaryStatement;
    private final PreparedStatement getMostPaidEmployeeStatement;
    private final PreparedStatement insertEmployeeStatement;

    public EmployeeDAO(Connection connection) throws SQLException {
        super(connection, "employees");

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
    }

    public Employee createEmployee(String name, String lastName, String patronymicName,
                                   Date dateOfBirth,
                                   String phoneNumber,
                                   double salary) throws SQLException {
        java.sql.Date dob = toSqlDate(dateOfBirth);
        java.sql.Date hiredAt = toSqlDate(new Date());

        insertEmployeeStatement.setString(1, name);
        insertEmployeeStatement.setString(2, lastName);
        insertEmployeeStatement.setString(3, patronymicName);
        insertEmployeeStatement.setDate(4, dob);
        insertEmployeeStatement.setString(5, phoneNumber);
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

    public Map<Integer, Integer> addManyExisting(List<Employee> values) throws SQLException {
        return insertMultipleWithIDFetch(insertEmployeeStatement, (statement, value) -> {
            insertEmployeeStatement.setString(1, value.getFirstName());
            insertEmployeeStatement.setString(2, value.getLastName());
            insertEmployeeStatement.setString(3, value.getPatronymicName());
            insertEmployeeStatement.setDate(4, toSqlDate(value.getDateOfBirth()));
            insertEmployeeStatement.setString(5, value.getPhoneNumber());
            insertEmployeeStatement.setDate(6, toSqlDate(value.getHireDate()));
            insertEmployeeStatement.setDouble(7, value.getSalary());
        }, values);
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
