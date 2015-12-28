package xmen.collectorapp.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.entity.Category;


@Repository("categoryDAO")
public class CategoryDAO {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Category> findAllCategory() {
		Query query = em.createNamedQuery("findAllCategories");
		return (ArrayList<Category>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Category find(String name) {
		Query query = em.createNamedQuery("findCategoryByName");
		query.setParameter("categoryName", name);
		ArrayList<Category> results = (ArrayList<Category>)  query.getResultList();
		if (results.size() == 0)
			return null;
		return results.get(0);
	}

	@Transactional
	public Category save(Category category) {		
			em.persist(category);  	
			em.flush();
			return category;
		}

}
