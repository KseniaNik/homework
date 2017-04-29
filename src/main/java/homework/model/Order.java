package homework.model;

import java.sql.Date;
import java.util.List;

/**
 * Created on 29.04.2017.
 */
public class Order extends Model {

    private String clientFirstName;
    private String clientLastName;
    private String clientPatronymicName;
    private String phoneNumber;
    private Date orderDate;
    private boolean executed;
    private int serviceId;
    private int officeId;
    private int employeeId;

    private List<Article> articleList;

    public Order(int id, String clientFirstName, String clientLastName, String clientPatronymicName, String phoneNumber,
                 Date orderDate, boolean executed, int serviceId, int officeId, int employeeId) {
        super(id);
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.clientPatronymicName = clientPatronymicName;
        this.phoneNumber = phoneNumber;
        this.orderDate = orderDate;
        this.executed = executed;
        this.serviceId = serviceId;
        this.officeId = officeId;
        this.employeeId = employeeId;
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

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getOfficeId() {
        return officeId;
    }

    public void setOfficeId(int officeId) {
        this.officeId = officeId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("id=").append(id);
        sb.append(", clientFirstName='").append(clientFirstName).append('\'');
        sb.append(", clientLastName='").append(clientLastName).append('\'');
        sb.append(", clientPatronymicName='").append(clientPatronymicName).append('\'');
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", orderDate=").append(orderDate);
        sb.append(", executed=").append(executed);
        sb.append(", serviceId=").append(serviceId);
        sb.append(", officeId=").append(officeId);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", articleList=").append(articleList);
        sb.append('}');
        return sb.toString();
    }
}

