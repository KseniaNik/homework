package homework.dao;

import homework.model.Model;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Created on 26.05.2017.
 */
public class DAO<T extends Model> {

    private final Class<T> type;
    protected final EntityManager manager;

    protected DAO(Class<T> type, EntityManager manager) {
        this.manager = manager;
        this.type = type;
    }

    public final T findById(Long id) {
        return manager.find(type, id);
    }

    public final T update(T entity) {
        manager.getTransaction().begin();
        T updated = manager.merge(entity);
        manager.getTransaction().commit();
        return updated;
    }

    public final void delete(T entity) {
        manager.getTransaction().begin();
        manager.remove(entity);
        manager.getTransaction().commit();
    }

    public final void doImport(List<T> values) {
        manager.getTransaction().begin();
        for (T t : values) {
            try {
                manager.persist(t);
            } catch (EntityExistsException e) {
                // TODO: rethrow?
                System.err.println(String.format("Adding entity %s failed: already exists", t.toString()));
            }
        }
        manager.getTransaction().commit();
    }

    public final List<T> doExport() {
        CriteriaQuery<T> criteriaQuery = manager.getCriteriaBuilder().createQuery(type);
        criteriaQuery.select(criteriaQuery.from(type));
        return manager.createQuery(criteriaQuery).getResultList();
    }

    public final void close() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().rollback();
            manager.flush();
        }
        manager.close();
    }

}
