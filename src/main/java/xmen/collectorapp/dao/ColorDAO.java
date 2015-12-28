package xmen.collectorapp.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.entity.Color;


@Repository("colorDAO")
public class ColorDAO{
	
	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Color> findAll() {
		Query query = em.createNamedQuery("findAllColors");	
		return (ArrayList<Color>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Color find(String name) {
		Query query = em.createNamedQuery("findColorByName");
		query.setParameter("colorName", name);
		ArrayList<Color> results = (ArrayList<Color>)  query.getResultList();
		if (results.size() == 0)
			return null;
		return results.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Color findColorByID(long id) {
		List<Color> results = em.createNamedQuery("findColorByID") .setParameter("id", id).getResultList();
		
		return results.size() == 0 ? null : results.get(0);
	}
		
	
	@Transactional
	public Color save(Color color) {		
			em.persist(color);  	
			em.flush();
			return color;
		}

	
}
