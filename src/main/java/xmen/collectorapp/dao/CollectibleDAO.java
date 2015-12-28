package xmen.collectorapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.entity.Collectible;

@Repository("collectibleDAO")
public class CollectibleDAO {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Collectible> findAll() {
		Query query = em.createNamedQuery("findAllCollectibles");
		return (ArrayList<Collectible>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Collectible findCollectibleByID(long id) {
		List<Collectible> results = em.createNamedQuery("findCollectibleByID") .setParameter("id", id).getResultList();
		
		return results.size() == 0 ? null : results.get(0);
	}

	@Transactional
	public void save(Collectible collectible) {		
			em.persist(collectible);  	
			em.flush();
			
		}
}
