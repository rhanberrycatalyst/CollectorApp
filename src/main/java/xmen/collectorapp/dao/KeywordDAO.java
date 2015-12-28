package xmen.collectorapp.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xmen.collectorapp.entity.Keyword;
import xmen.collectorapp.util.UpdatingNonExistantEntityException;

@Repository("keywordDAO")
public class KeywordDAO {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Keyword> findAll() {
		Query query = em.createNamedQuery("findAllKeywords");
		return (ArrayList<Keyword>) query.getResultList();
	}
	
	@Transactional
	public void save(Keyword keyword) {		
			em.persist(keyword);  	
			em.flush();
		}
	
	@Transactional
	public void update(Keyword keyword)
	{	
		// null ID can occur if ID field isn't specified in JSON object sent by PUT
		if (keyword.getId() == null || (em.find(Keyword.class, keyword.getId()) == null)   ) 
			throw new UpdatingNonExistantEntityException();
		
		em.merge(keyword);  	
		em.flush();
	}
}
