package homework.xml;

import homework.model.*;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created on 29.04.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportedDatabase {

    @XmlElementWrapper(name = "services")
    @XmlElement(name = "service")
    private List<Service> services;

    @XmlElementWrapper(name = "offices")
    @XmlElement(name = "office")
    private List<Office> offices;

    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "employee")
    private List<Employee> employees;

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    private List<Order> orders;

    @XmlElementWrapper(name = "articles")
    @XmlElement(name = "article")
    private List<Article> articles;

    private ExportedDatabase() {}

    public ExportedDatabase(List<Service> services,
                            List<Office> offices,
                            List<Employee> employees,
                            List<Order> orders,
                            List<Article> articles) {
        this.services = services;
        this.offices = offices;
        this.employees = employees;
        this.orders = orders;
        this.articles = articles;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Office> getOffices() {
        return offices;
    }

    public void setOffices(List<Office> offices) {
        this.offices = offices;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ExportedDatabase{");
        sb.append("services=").append(services);
        sb.append(", offices=").append(offices);
        sb.append(", employees=").append(employees);
        sb.append(", orders=").append(orders);
        sb.append(", articles=").append(articles);
        sb.append('}');
        return sb.toString();
    }
}
