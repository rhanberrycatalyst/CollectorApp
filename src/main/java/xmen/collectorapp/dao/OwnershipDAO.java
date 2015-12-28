package xmen.collectorapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.entity.Ownership;

@Repository("ownershipDAO")
public class OwnershipDAO {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Ownership> findAll() {
		Query query = em.createNamedQuery("findAllOwnerships");
		return (ArrayList<Ownership>) query.getResultList();
	}
	
	
	@Transactional
	public void save(Ownership ownership) {		
			em.persist(ownership);  	
			em.flush();
		}
	
}
