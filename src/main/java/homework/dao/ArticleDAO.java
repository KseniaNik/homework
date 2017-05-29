package homework.dao;

import homework.model.Article;
import homework.model.Order;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.SQLException;
import java.util.List;

/**
 * Created on 29.04.2017.
 */
public class ArticleDAO extends DAO<Article> {

    public ArticleDAO(EntityManager manager) {
        super(Article.class, manager);
    }

    public List<Article> findByOrder(Order order) throws SQLException {
        TypedQuery<Article> query = manager.createQuery(
                "select article from Article article where article.order.id = :oid",
                Article.class
        );
        query.setParameter("oid", order.getId());
        return query.getResultList();
    }

}
