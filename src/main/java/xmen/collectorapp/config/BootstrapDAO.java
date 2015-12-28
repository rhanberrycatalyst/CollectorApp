package xmen.collectorapp.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("bootstrapDAO")
public class BootstrapDAO {

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void save(Object data) {
		em.persist(data);
		em.flush();

	}
}
