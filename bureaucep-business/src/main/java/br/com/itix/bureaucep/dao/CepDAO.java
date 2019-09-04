package br.com.itix.bureaucep.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import br.com.itix.bureaucep.entity.Cep;

@RequestScoped
public class CepDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Transactional
	public void salva(Cep cep) throws Exception {
		try {
			
			em.persist(cep);
			

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@Transactional
	public void atualiza(Cep cep) throws Exception {
		try {

			em.merge(cep);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cep> findAll() throws Exception {
		try {
			return em.createQuery("from Cep").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public Cep find(String id) throws Exception {
		try {

			return em.find(Cep.class, id);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

}
