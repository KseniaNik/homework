package homework.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created on 29.04.2017.
 */
@XmlRootElement
public class Employee extends Model {

    private String firstName;
    private String lastName;
    private String patronymicName;
    private Date dateOfBirth;
    private String phoneNumber;
    private Date hireDate;
    private double salary;

    private Employee() {
        //
    }

    public Employee(int id, String firstName, String lastName, String patronymicName,
                    Date dateOfBirth, String phoneNumber, Date hireDate, double salary) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymicName = patronymicName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", patronymicName='").append(patronymicName).append('\'');
        sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", hireDate=").append(hireDate);
        sb.append(", salary=").append(salary);
        sb.append('}');
        return sb.toString();
    }
}
