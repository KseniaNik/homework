package homework.dao;

import homework.model.Article;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created on 29.04.2017.
 */
public class ArticleDAO extends DAO<Article> {

    private final PreparedStatement insertStatement;
    private final PreparedStatement findByOrderIDStatement;

    public ArticleDAO(Connection connection) throws SQLException {
        super(connection, "articles");

        findByOrderIDStatement = connection.prepareStatement(
                "select * from articles where order_id = ?"
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
        }, articles, null);
    }

    public Map<Integer, Integer> addManyExisting(List<Article> values,
                                                 Map<Integer, Integer> orderIdMap) throws SQLException {
        return insertMultipleWithIDFetch(insertStatement, (s, v) -> {
            s.setInt(1, orderIdMap.get(v.getOrderId()));
            s.setString(2, v.getName());
            s.setInt(3, v.getColor());
            s.setString(4, v.getComponents());
        }, values);
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
