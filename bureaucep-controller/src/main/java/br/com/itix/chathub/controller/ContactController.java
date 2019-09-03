package br.com.itix.chathub.controller;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.itix.bureaucep.dao.CepDAO;
import br.com.itix.bureaucep.dao.LogDAO;
import br.com.itix.bureaucep.entity.Cep;
import br.com.itix.bureaucep.entity.Log;

@Path("contact")
public class ContactController {
	
	@Inject
	private CepDAO dao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendHi() {
		return (new Gson()).toJson("Hello world !!!");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllCep")
	public String findAllCep() throws Exception  {
		List<Cep> ceps = dao.findAll();
		return (new Gson()).toJson(ceps);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findCep")
	public String find(String id) throws Exception {
		Cep cep = new Cep();
		cep = dao.find(id);
		this.verifyCep(cep);
		return (new Gson()).toJson("");
	}

	public void verifyCep(Cep cep) {
		Date dataAtual = new Date();
		Date dateAux = new Date();
		
		if (cep != null) {
			dateAux.setMinutes(5);
			dataAtual.setMinutes(dataAtual.getMinutes() - dateAux.getMinutes());
			if (dataAtual.getDate() > cep.getDataIncl().getDate()) {
//				consultar o site http://viacep.com.br/ e atualizar o registro na base de dados
//				renovando o prazo de validade do mesmo
//				inserindo o log quando consultar
			} else {
				System.out.println("registro é valido e existe na base de dados");
			}
			
		} else {
//			efetua a consulta no site http://viacep.com.br/ e salva um novo registro na base de dados
//			inserindo o log na cosulta
		}
	}

	@POST
	@Path("/salvaCep")
	public void salva(Cep cep) throws Exception {
		dao.salva(cep);
	}

	@POST
	@Path("/atualizaCep")
	public void atualiza(Cep cep) throws Exception {
		dao.atualiza(cep);
	}

	@POST
	@Path("/excluiCep")
	public void exclui(Cep cep) throws Exception {
		dao.exclui(cep);
	}

	// log

	@GET
	@Path("/findAllLog")
	public List<Log> findAllLog() throws Exception {
		LogDAO dao = new LogDAO();
		return dao.findAll();
	}

	@GET
	@Path("/findLog")
	public Log findLog(long id) throws Exception {
		LogDAO dao = new LogDAO();
		Log log = new Log();
		log = dao.find(id);
		return log;
	}

	@POST
	@Path("/salvaLog")
	public void salva(Log log) throws Exception {
		LogDAO dao = new LogDAO();
		dao.salva(log);
	}

	@POST
	@Path("/atualizaLog")
	public void atualiza(Log log) throws Exception {
		LogDAO dao = new LogDAO();
		dao.atualiza(log);
	}

	@POST
	@Path("/excluiLog")
	public void exclui(Log log) throws Exception {
		LogDAO dao = new LogDAO();
		dao.exclui(log);
	}

}
