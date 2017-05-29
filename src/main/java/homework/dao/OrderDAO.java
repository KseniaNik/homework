package homework.dao;

import homework.model.*;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static homework.util.Util.toSqlTimestamp;

/**
 * Created on 29.04.2017.
 */
public class OrderDAO extends DAO<Order> {

    public OrderDAO(EntityManager manager) {
        super(Order.class, manager);
    }

    public Order createOrder(
            String clientName, String clientLastName, String clientPatronymicName,
            String phoneNumber,
            Service service, Office office, Employee employee,
            List<Article> articles) {

        Order order = new Order();

        Date now = new Date();
        java.sql.Timestamp timestamp = toSqlTimestamp(now);

        order.setClientFirstName(clientName);
        order.setClientLastName(clientLastName);
        order.setClientPatronymicName(clientPatronymicName);
        order.setPhoneNumber(phoneNumber);
        order.setOrderDate(timestamp);
        order.setExecuted(false);
        order.setService(service);
        order.setOffice(office);
        order.setEmployee(employee);

        for (Article article : articles) {
            article.setOrder(order);
        }
        order.setArticleList(articles);

        manager.getTransaction().begin();
        manager.persist(order);
        manager.getTransaction().commit();

        return order;
    }

}
