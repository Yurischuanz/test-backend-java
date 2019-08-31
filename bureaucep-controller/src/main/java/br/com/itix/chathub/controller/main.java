package br.com.itix.chathub.controller;
public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Conexao cnx = new Conexao("PostgreSql","localhost","5432","cep","postgres","123");
		cnx.conect();
		//...
		cnx.disconect();
	}

}
