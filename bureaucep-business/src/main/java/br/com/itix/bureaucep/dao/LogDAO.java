package br.com.itix.bureaucep.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.itix.bureaucep.entity.Log;

public class LogDAO {

	@PersistenceContext(unitName = "sysDefault")
	private EntityManager em;

	public void salva(Log log) throws Exception {
		try {

			em.persist(log);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public void atualiza(Log log) throws Exception {

		try {

			em.merge(log);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}

	}

	public void exclui(Log log) throws Exception {

		try {

			em.remove(log);

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
