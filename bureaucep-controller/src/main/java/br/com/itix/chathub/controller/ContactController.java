package br.com.itix.chathub.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	private CepDAO cepDao;

	@Inject
	private LogDAO logDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sendHi() {
		return (new Gson()).toJson("Hello world !!!");
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAllCep")
	public String findAllCep() throws Exception {
		List<Cep> ceps = cepDao.findAll();
		return (new Gson()).toJson(ceps);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findCep/{cep}")
	public String find(@PathParam("cep") String cep) {
		String result = "";
		try {
			result = "";
			if (cep.length() != 8) {
				return "O Cep deve conter 8 Registros!";
			}
			Cep cepRetorno = new Cep();
			cepRetorno = cepDao.find(cep);
			if (cepRetorno != null) {
				return this.verifyCep(cepRetorno);
			} else {
				Cep novoCep = new Cep();
				novoCep = this.getSite(cep);
				if (novoCep != null) {
					novoCep.setCep(novoCep.getCep().replaceAll("[-.]", ""));
					novoCep.setDataIncl(LocalDateTime.now());
					this.salva(novoCep);
					result = (new Gson()).toJson("Registro novo inserido");
					result += "\n" + (new Gson()).toJson(novoCep);
				} else {
					result = "O Cep informado não Existe!";
				}
			}
			result = (new Gson()).toJson(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@POST
	@Path("/salvaCep")
	public void salva(Cep cep) throws Exception {
		cepDao.salva(cep);
	}

	@POST
	@Path("/atualizaCep")
	public void atualiza(Cep cep) throws Exception {
		cepDao.atualiza(cep);
	}

	// log

	@GET
	@Path("/findAllLog")
	public List<Log> findAllLog() throws Exception {
		return logDao.findAll();
	}

	@GET
	@Path("/findLog")
	public Log findLog(long id) throws Exception {
		Log log = new Log();
		log = logDao.find(id);
		return log;
	}

	@POST
	@Path("/salvaLog")
	public void salva(Log log) throws Exception {
		logDao.salva(log);
	}

	public String verifyCep(Cep cep) {
		LocalDateTime dataAtual = LocalDateTime.now();

		String result = "";

		if (cep.getDataIncl() == null || (this.isDataValida(dataAtual, cep.getDataIncl()))) {
			try {
				Cep attCep = new Cep();
				attCep = this.getSite(cep.getCep());
				attCep.setCep(attCep.getCep().replaceAll("[-.]", ""));
				attCep.setDataIncl(LocalDateTime.now());
				this.atualiza(attCep);
				result = (new Gson()).toJson("Cep atualizado, com o prazo de validade renovado!");
				result += "\n" + (new Gson()).toJson(attCep);
			} catch (Exception e) {
				e.printStackTrace();
				e.getMessage();
				result = (new Gson()).toJson("ERRO!");
			}

		} else {
			result = (new Gson()).toJson("registro é valido e existe na base de dados");
			result += "\n" + (new Gson()).toJson(cep);
		}

		return result;
	}

	public boolean isDataValida(LocalDateTime dtAtual, LocalDateTime dtRegistro) {

		return dtRegistro.isBefore(dtAtual.minusMinutes(5));

	}

	public Cep getSite(String cep) throws Exception {
		StringBuilder result = new StringBuilder();
		Cep res = new Cep();

		try {
			String site = " http://viacep.com.br/ws/" + cep + "/json";
			URL url = new URL(site);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}

			Log novoLog = new Log();
			novoLog.setDtIncl(LocalDateTime.now());
			novoLog.setRequest(cep);
			novoLog.setResponse(result.toString());
			this.salva(novoLog);

			if (result.toString().contains("erro")) {
				res = null;
			} else {
				Gson gson = new Gson();
				res = gson.fromJson(result.toString(), Cep.class);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

		return res;
	}

}
