package homework.dao;

import homework.model.Office;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created on 29.04.2017.
 */
public class OfficeDAO extends DAO<Office> {

    private final PreparedStatement insertOfficeStatement;
    private final PreparedStatement findOfficeByNameStatement;

    public OfficeDAO(Connection connection) throws SQLException {
        super(connection, "offices");
        insertOfficeStatement = connection.prepareStatement(
                "insert into offices (name, address) values (?, ?)"
        );
        findOfficeByNameStatement = connection.prepareStatement(
                "select * from offices where name = ?"
        );
    }

    public Office createOffice(String name, String address) throws SQLException {
        insertOfficeStatement.setString(1, name);
        insertOfficeStatement.setString(2, address);

        return new Office(executeInsert(insertOfficeStatement), name, address);
    }

    public Office findByName(String name) throws SQLException {
        findOfficeByNameStatement.setString(1, name);
        return one(findOfficeByNameStatement.executeQuery());
    }

    public Map<Integer, Integer> addManyExisting(List<Office> values) throws SQLException {
        return insertMultipleWithIDFetch(insertOfficeStatement, (statement, value) -> {
            statement.setString(1, value.getName());
            statement.setString(2, value.getAddress());
        }, values);
    }

    @Override
    protected Office one(ResultSet resultSet) throws SQLException {
        return new Office(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("address")
        );
    }
}
