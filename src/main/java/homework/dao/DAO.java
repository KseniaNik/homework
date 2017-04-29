package homework.dao;

import homework.model.Model;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 29.04.2017.
 */
public abstract class DAO<T extends Model> {

    private final String tableName;

    private final PreparedStatement getLastIDStatement;
    private final PreparedStatement findByIDStatement;
    private final PreparedStatement getAllStatement;
    private final PreparedStatement deleteStatement;

    protected Connection connection;

    public DAO(Connection connection, String tableName) throws SQLException {
        this.connection = connection;
        this.tableName = tableName;

        getLastIDStatement = connection.prepareStatement(
                "select last_insert_rowid()"
        );
        deleteStatement = connection.prepareStatement(
                String.format("delete from %s where id = ?", tableName)
        );
        findByIDStatement = connection.prepareStatement(
                String.format("select * from %s where id = ?", tableName)
        );
        getAllStatement = connection.prepareStatement(
                String.format("select * from %s", tableName)
        );
    }

    List<T> many(ResultSet resultSet) throws SQLException {
        List<T> result = new ArrayList<>();
        while (resultSet.next()) {
            result.add(one(resultSet));
        }
        return result;
    }

    public interface Action<R> {
        R perform() throws SQLException;
    }

    final <R> R doInTransaction(Action<R> action) throws SQLException {
        boolean ac = connection.getAutoCommit();
        connection.setAutoCommit(false);
        R result;

        try {
            result = action.perform();
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(ac);
            throw new SQLException("transaction exception", e);
        }

        connection.commit();
        connection.setAutoCommit(ac);
        return result;
    }

    public interface StatementUpdateAction<T> {
        void perform(PreparedStatement statement, T value) throws SQLException;
    }

    /**
     * Insert multiple + fetch ids; ! IS SLOW ! - not using batch
     */
    final void insertMultipleWithIDFetch(PreparedStatement statement,
                                         StatementUpdateAction<T> updateAction,
                                         List<T> values,
                                         Map<Integer, Integer> idMap) throws SQLException {
        doInTransaction(() -> {
            for (T value : values) {
                updateAction.perform(statement, value);
                statement.executeUpdate();
                int newId = getLastIDStatement.executeQuery().getInt(1);
                if (idMap != null) {
                    idMap.put(value.getId(), newId);
                }
                value.setId(newId);
            }
            return Void.TYPE;
        });
    }

    final Map<Integer, Integer> insertMultipleWithIDFetch(PreparedStatement statement,
                                                          StatementUpdateAction<T> updateAction,
                                                          List<T> values) throws SQLException {
        Map<Integer, Integer> result = new HashMap<>();
        insertMultipleWithIDFetch(statement, updateAction, values, result);
        return result;
    }

    /**
     * Insert one + fetch id IN transaction
     */
    final int executeInsert(PreparedStatement statement) throws SQLException {
        return doInTransaction(() -> {
            statement.executeUpdate();
            return getLastIDStatement.executeQuery().getInt(1);
        });
    }

    public final List<T> retrieveAll() throws SQLException {
        return many(getAllStatement.executeQuery());
    }

    public final T findByID(int id) throws SQLException {
        findByIDStatement.setInt(1, id);
        return one(findByIDStatement.executeQuery());
    }

    public final void deleteByID(int id) throws SQLException {
        deleteStatement.setInt(1, id);
        deleteStatement.executeUpdate();
    }

    protected abstract T one(ResultSet resultSet) throws SQLException;
}
