package homework.dao;

import homework.model.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 29.04.2017.
 */
public abstract class DAO<T extends Model> {

    private final PreparedStatement getLastIDStatement;
    protected Connection connection;

    public DAO(Connection connection) throws SQLException {
        this.connection = connection;
        getLastIDStatement = connection.prepareStatement(
                "select last_insert_rowid()"
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
                                         List<T> values) throws SQLException {
        doInTransaction(() -> {
            for (T value : values) {
                updateAction.perform(statement, value);
                statement.executeUpdate();
                value.setId(
                        getLastIDStatement.executeQuery().getInt(1)
                );
            }
            return Void.TYPE;
        });
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

    public abstract T findByID(int id) throws SQLException;

    public abstract void deleteByID(int id) throws SQLException;

    protected abstract T one(ResultSet resultSet) throws SQLException;
}
