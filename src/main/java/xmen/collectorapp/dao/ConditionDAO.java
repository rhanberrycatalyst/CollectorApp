package xmen.collectorapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.entity.Condition;

@Repository("conditionDAO")
public class ConditionDAO {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Condition> findAllConditions() {
		Query query = em.createNamedQuery("findAllConditions");
		return (ArrayList<Condition>) query.getResultList();
	}
	
	@Transactional
	public void save(Condition condition) {		
			em.persist(condition);  	
			em.flush();
		}
	
	
}
