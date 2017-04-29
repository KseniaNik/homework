package homework.dao;

import homework.model.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class ServiceDAO extends DAO<Service> {

    private final PreparedStatement findByNameStatement;
    private final PreparedStatement findByIDStatement;
    private final PreparedStatement insertStatement;
    private final PreparedStatement deleteByIDStatement;

    public ServiceDAO(Connection connection) throws SQLException {
        super(connection);

        findByIDStatement = connection.prepareStatement(
                "select * from services where id = ?"
        );
        insertStatement = connection.prepareStatement(
                "insert into services (name, price) values (?, ?)"
        );
        deleteByIDStatement = connection.prepareStatement(
                "delete from services where id = ?"
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

    @Override
    public Service findByID(int id) throws SQLException {
        findByIDStatement.setInt(1, id);
        return one(findByIDStatement.executeQuery());
    }

    @Override
    public void deleteByID(int id) throws SQLException {
        deleteByIDStatement.setInt(1, id);
        deleteByIDStatement.executeUpdate();
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
