package homework.dao;

import homework.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 29.04.2017.
 */
public class ServiceDAO extends DAO<Service> {

    private final PreparedStatement findByNameStatement;
    private final PreparedStatement insertStatement;

    public ServiceDAO(Connection connection) throws SQLException {
        super(connection, "services");

        insertStatement = connection.prepareStatement(
                "insert into services (name, price) values (?, ?)"
        );
        findByNameStatement = connection.prepareStatement(
                "select * from services where name = ?"
        );
    }

    public Service createService(String name, double price) throws SQLException {
        insertStatement.setString(1, name);
        insertStatement.setDouble(2, price);

        return new Service(executeInsert(insertStatement), name, price);
    }

    public Service findByName(String name) throws SQLException {
        findByNameStatement.setString(1, name);
        return one(findByNameStatement.executeQuery());
    }

    public Map<Integer, Integer> addManyExisting(List<Service> values) throws SQLException {
        return insertMultipleWithIDFetch(insertStatement, (statement, value) -> {
            statement.setString(1, value.getName());
            statement.setDouble(2, value.getPrice());
        }, values);
    }

    @Override
    protected Service one(ResultSet resultSet) throws SQLException {
        return new Service(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDouble("price")
        );
    }
}
