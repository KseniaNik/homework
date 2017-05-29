package homework.dao;

import homework.model.Office;

import javax.persistence.EntityManager;
import java.sql.SQLException;

/**
 * Created on 29.04.2017.
 */
public class OfficeDAO extends DAO<Office> {

    public OfficeDAO(EntityManager manager) {
        super(Office.class, manager);
    }

    public Office createOffice(String name, String address) throws SQLException {
        Office office = new Office();
        office.setAddress(address);
        office.setName(name);

        manager.getTransaction().begin();
        manager.persist(office);
        manager.getTransaction().commit();

        return office;
    }
}
