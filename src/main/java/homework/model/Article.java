package homework.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created on 29.04.2017.
 */
@XmlRootElement
@Entity
@Table(name = "articles")
public class Article implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @XmlTransient
    private Long id;

    // scalar data
    private String name;
    private Integer color;
    private String components;

    @XmlTransient
    @ManyToOne(fetch = FetchType.EAGER)
    private Order order;

    public Article() {
    }

    public Article(String name, Integer color, String components) {
        this.name = name;
        this.color = color;
        this.components = components;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Article{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", color=").append(color);
        sb.append(", components='").append(components).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
