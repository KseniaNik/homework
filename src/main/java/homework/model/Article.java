package homework.model;

/**
 * Created on 29.04.2017.
 */
public class Article extends Model {

    private int orderId;
    private String name;
    private int color;
    private String components;

    public Article(String name, int color, String components) {
        this(-1, -1, name, color, components);
    }

    public Article(int id, int orderId, String name, int color, String components) {
        super(id);

        this.orderId = orderId;
        this.name = name;
        this.color = color;
        this.components = components;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
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
        final StringBuffer sb = new StringBuffer("Article{");
        sb.append("id=").append(id);
        sb.append(", orderId=").append(orderId);
        sb.append(", name='").append(name).append('\'');
        sb.append(", color=").append(color);
        sb.append(", components='").append(components).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
