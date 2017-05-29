package homework.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created on 29.04.2017.
 */
@XmlRootElement
@Entity
@Table(name = "orders")
public class Order implements Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // scalar data
    private String clientFirstName;
    private String clientLastName;
    private String clientPatronymicName;
    private String phoneNumber;
    private Date orderDate;
    private Boolean executed;

    //relations
    @ManyToOne
    private Service service;
    @ManyToOne
    private Office office;
    @ManyToOne
    private Employee employee;
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Article> articleList;

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientPatronymicName() {
        return clientPatronymicName;
    }

    public void setClientPatronymicName(String clientPatronymicName) {
        this.clientPatronymicName = clientPatronymicName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Boolean isExecuted() {
        return executed;
    }

    public void setExecuted(Boolean executed) {
        this.executed = executed;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("id=").append(id);
        sb.append(", clientFirstName='").append(clientFirstName).append('\'');
        sb.append(", clientLastName='").append(clientLastName).append('\'');
        sb.append(", clientPatronymicName='").append(clientPatronymicName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", orderDate=").append(orderDate);
        sb.append(", executed=").append(executed);
        sb.append(", service=").append(service);
        sb.append(", office=").append(office);
        sb.append(", employee=").append(employee);
        sb.append(", articleList=").append(articleList);
        sb.append('}');
        return sb.toString();
    }
}

