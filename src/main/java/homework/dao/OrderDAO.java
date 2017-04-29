package homework.dao;

import homework.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static homework.util.Util.toSqlDate;
import static homework.util.Util.toSqlTimestamp;

/**
 * Created on 29.04.2017.
 */
public class OrderDAO extends DAO<Order> {

    private final PreparedStatement insertOrderStatement;

    public OrderDAO(Connection connection)
            throws SQLException {
        super(connection, "orders");

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

        Date now = new Date();
        java.sql.Timestamp timestamp = toSqlTimestamp(now);

        insertOrderStatement.setString(1, clientName);
        insertOrderStatement.setString(2, clientLastName);
        insertOrderStatement.setString(3, clientPatronymicName);
        insertOrderStatement.setString(4, phoneNumber);
        insertOrderStatement.setTimestamp(5, timestamp);
        insertOrderStatement.setBoolean(6, false);
        insertOrderStatement.setInt(7, serviceId);
        insertOrderStatement.setInt(8, officeId);
        insertOrderStatement.setInt(9, employeeId);

        return new Order(executeInsert(insertOrderStatement),
                clientName, clientLastName, clientPatronymicName, phoneNumber, now,
                false, serviceId, officeId, employeeId);
    }

    public Map<Integer, Integer> addManyExisting(List<Order> values,
                                   Map<Integer, Integer> serviceIdMap,
                                   Map<Integer, Integer> officeIdMap,
                                   Map<Integer, Integer> employeeIdMap) throws SQLException {
        Map<Integer, Integer> result = new HashMap<>();
        insertMultipleWithIDFetch(insertOrderStatement, (statement, value) -> {
            statement.setString(1, value.getClientFirstName());
            statement.setString(2, value.getClientLastName());
            statement.setString(3, value.getClientPatronymicName());
            statement.setString(4, value.getPhoneNumber());
            statement.setTimestamp(5, toSqlTimestamp(value.getOrderDate()));
            statement.setBoolean(6, value.isExecuted());
            statement.setInt(7, serviceIdMap.get(value.getServiceId()));
            statement.setInt(8, officeIdMap.get(value.getOfficeId()));
            statement.setInt(9, employeeIdMap.get(value.getEmployeeId()));
        }, values, result);
        return result;
    }

    @Override
    protected Order one(ResultSet resultSet) throws SQLException {
        return new Order(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("patr_name"),
                resultSet.getString("phone_number"),
                resultSet.getTimestamp("order_date"),
                resultSet.getBoolean("executed"),
                resultSet.getInt("service_id"),
                resultSet.getInt("office_id"),
                resultSet.getInt("employee_id")
        );
    }
}
