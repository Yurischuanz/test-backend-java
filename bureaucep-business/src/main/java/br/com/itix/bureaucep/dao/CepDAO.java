package br.com.itix.bureaucep.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.itix.bureaucep.entity.Cep;

@RequestScoped
public class CepDAO {
	
	@PersistenceContext(unitName = "sysDefault")
	private EntityManager em;

	public void salva(Cep cep) throws Exception {
		try {

			em.persist(cep);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public void atualiza(Cep cep) throws Exception {
		try {

			em.merge(cep);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public void exclui(Cep cep) throws Exception {
		try {

			em.remove(cep);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked" })
	public List<Cep> findAll() throws Exception {
		try {
			return em.createQuery("from Cep").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	public Cep find(long id) throws Exception {
		try {

			return em.find(Cep.class, id);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

}
