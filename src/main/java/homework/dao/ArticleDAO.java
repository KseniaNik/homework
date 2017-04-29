package homework.dao;

import homework.model.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created on 29.04.2017.
 */
public class ArticleDAO extends DAO<Article> {

    private final PreparedStatement findByIDStatement;
    private final PreparedStatement deleteByIDStatement;
    private final PreparedStatement insertStatement;
    private final PreparedStatement findByOrderIDStatement;

    public ArticleDAO(Connection connection) throws SQLException {
        super(connection);

        findByIDStatement = connection.prepareStatement(
                "select * from articles where id = ?"
        );
        findByOrderIDStatement = connection.prepareStatement(
                "select * from articles where order_id = ?"
        );
        deleteByIDStatement = connection.prepareStatement(
                "delete from articles where id = ?"
        );
        insertStatement = connection.prepareStatement(
                "insert into articles (order_id, name, color, components) values (?, ?, ?, ?)"
        );
    }

    public List<Article> findByOrderID(int orderId) throws SQLException {
        findByOrderIDStatement.setInt(1, orderId);
        return many(findByOrderIDStatement.executeQuery());
    }

    public void createWithOrder(int orderId, List<Article> articles) throws SQLException {
        insertMultipleWithIDFetch(insertStatement, (statement, value) -> {
            value.setOrderId(orderId);
            statement.setInt(1, orderId);
            statement.setString(2, value.getName());
            statement.setInt(3, value.getColor());
            statement.setString(4, value.getComponents());
        }, articles);
    }

    @Override
    public Article findByID(int id) throws SQLException {
        findByIDStatement.setInt(1, id);
        return one(findByIDStatement.executeQuery());
    }

    @Override
    public void deleteByID(int id) throws SQLException {
        deleteByIDStatement.setInt(1, id);
        deleteByIDStatement.executeUpdate();
    }

    @Override
    protected Article one(ResultSet resultSet) throws SQLException {
        return new Article(
                resultSet.getInt("id"),
                resultSet.getInt("order_id"),
                resultSet.getString("name"),
                resultSet.getInt("color"),
                resultSet.getString("components")
        );
    }
}
