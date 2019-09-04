package br.com.itix.chathub.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import com.google.gson.Gson;

import br.com.itix.bureaucep.entity.Cep;
import br.com.itix.chathub.controller.ContactController;

public class ContactControllerTest {

	ContactController controller = new ContactController();

	Gson gson = new Gson();
	
	
	@Test
	public void testCelFormatoErrado() throws Exception {
		String cepTeste = "2916325202";
		
		String retornoFind = controller.find(cepTeste);

		assertTrue(retornoFind.equals("O Cep deve conter 8 Registros!"));

	}

	@Test
	public void testFind() throws Exception {
		String cepTeste = "291632520";
		Cep cep = new Cep();

		String retornoFind = controller.find(cepTeste);

		cep = gson.fromJson(retornoFind.toString(), Cep.class);

		assertTrue(cep.getCep().equals("29163250"));
		assertTrue(cep.getLogradouro().equals("Rua Almerinda Alves da Silva"));
		assertTrue(cep.getBairro().equals("São Diogo I"));
		assertTrue(cep.getLocalidade().equals("Serra"));
		assertTrue(cep.getUf().equals("ES"));
	}

}
