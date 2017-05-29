package homework.model;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Created on 29.04.2017.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ExportedDatabase {

    @XmlElementWrapper(name = "services")
    @XmlElement(name = "Service")
    private List<Service> services;

    @XmlElementWrapper(name = "offices")
    @XmlElement(name = "Office")
    private List<Office> offices;

    @XmlElementWrapper(name = "employees")
    @XmlElement(name = "Employee")
    private List<Employee> employees;

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "Order")
    private List<Order> orders;

    private ExportedDatabase() {}

    public ExportedDatabase(List<Service> services,
                            List<Office> offices,
                            List<Employee> employees,
                            List<Order> orders) {
        this.services = services;
        this.offices = offices;
        this.employees = employees;
        this.orders = orders;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExportedDatabase{");
        sb.append("services=").append(services);
        sb.append(", offices=").append(offices);
        sb.append(", employees=").append(employees);
        sb.append(", orders=").append(orders);
        sb.append('}');
        return sb.toString();
    }
}
