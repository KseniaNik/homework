package homework.dao;

import homework.model.Office;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class OfficeDAO extends DAO<Office> {

    private final PreparedStatement insertOfficeStatement;
    private final PreparedStatement deleteOfficeStatement;
    private final PreparedStatement findOfficeByNameStatement;
    private final PreparedStatement findOfficeByIDStatement;

    public OfficeDAO(Connection connection) throws SQLException {
        super(connection);
        insertOfficeStatement = connection.prepareStatement(
                "insert into offices (name, address) values (?, ?)"
        );
        deleteOfficeStatement = connection.prepareStatement(
                "delete from offices where id = ?"
        );
        findOfficeByIDStatement = connection.prepareStatement(
                "select * from offices where id = ?"
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

    @Override
    public Office findByID(int id) throws SQLException {
        findOfficeByIDStatement.setInt(1, id);
        return one(findOfficeByIDStatement.executeQuery());
    }

    @Override
    public void deleteByID(int id) throws SQLException {
        deleteOfficeStatement.setInt(1, id);
        deleteOfficeStatement.executeUpdate();
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
