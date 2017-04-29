package homework.dao;

import homework.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created on 29.04.2017.
 */
public class OrderDAO extends DAO<Order> {

    private final PreparedStatement insertOrderStatement;
    private final PreparedStatement findByIDStatement;
    private final PreparedStatement deleteByIDStatement;

    public OrderDAO(Connection connection)
            throws SQLException {
        super(connection);

        findByIDStatement = connection.prepareStatement(
                "select * from orders where id = ?"
        );
        deleteByIDStatement = connection.prepareStatement(
                "delete from orders where id = ?"
        );
        insertOrderStatement = connection.prepareStatement(
                "insert into orders " +
                        "(first_name, last_name, patr_name, phone_number, order_date, executed, " +
                        "service_id, office_id, employee_id) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
    }

    public Order createOrder(
            String clientName, String clientLastName, String clientPatronymicName,
            String phoneNumber, int serviceId, int officeId, int employeeId) throws SQLException {

        java.sql.Date date = new java.sql.Date(new Date().getTime());

        insertOrderStatement.setString(1, clientName);
        insertOrderStatement.setString(2, clientLastName);
        insertOrderStatement.setString(3, clientPatronymicName);
        insertOrderStatement.setString(4, phoneNumber);
        insertOrderStatement.setDate(5, date);
        insertOrderStatement.setBoolean(6, false);
        insertOrderStatement.setInt(7, serviceId);
        insertOrderStatement.setInt(8, officeId);
        insertOrderStatement.setInt(9, employeeId);

        return new Order(executeInsert(insertOrderStatement),
                clientName, clientLastName, clientPatronymicName, phoneNumber, date,
                false, serviceId, officeId, employeeId);
    }

    @Override
    public Order findByID(int id) throws SQLException {
        findByIDStatement.setInt(1, id);
        return one(findByIDStatement.executeQuery());
    }

    @Override
    public void deleteByID(int id) throws SQLException {
        deleteByIDStatement.setInt(1, id);
        deleteByIDStatement.executeUpdate();
    }

    @Override
    protected Order one(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("patr_name"),
                resultSet.getString("phone_number"),
                resultSet.getDate("order_date"),
                resultSet.getBoolean("executed"),
                resultSet.getInt("service_id"),
                resultSet.getInt("office_id"),
                resultSet.getInt("employee_id")
        );
    }
}
