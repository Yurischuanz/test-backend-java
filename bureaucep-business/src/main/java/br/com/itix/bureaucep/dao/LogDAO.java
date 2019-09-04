package br.com.itix.bureaucep.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.itix.bureaucep.entity.Log;

@RequestScoped
public class LogDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void salva(Log log) throws Exception {
		try {

			em.persist(log);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public List<Log> findAll() throws Exception {

		try {

			return em.createQuery("from Log").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}

	public Log find(long id) throws Exception {

		try {

			return em.find(Log.class, id);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}

}
