package homework.dao;

import homework.model.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class ServiceDAO extends DAO<Service> {

    public ServiceDAO(EntityManager manager) {
        super(Service.class, manager);
    }

    public Service createService(String name, double price) throws SQLException {
        Service service = new Service();
        service.setName(name);
        service.setPrice(price);

        manager.getTransaction().begin();
        manager.persist(service);
        manager.getTransaction().commit();

        return service;
    }

    public Service findByName(String name) {
        TypedQuery<Service> query = manager.createQuery(
                "select service from Service service where service.name = :name", Service.class)
                .setParameter("name", name);
        return query.getSingleResult();
    }

}
