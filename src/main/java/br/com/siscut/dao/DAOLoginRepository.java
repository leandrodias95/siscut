package br.com.siscut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.siscut.connection.SingletonConnection;
import br.com.siscut.model.Usuario;

public class DAOLoginRepository {
	private Connection connection;

	public DAOLoginRepository() {
		connection = SingletonConnection.getConnection();
	}

	public boolean validarAutenticacao(Usuario oUsuario) throws Exception {

		String sql = "select * from model_login where login=? and senha=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, oUsuario.getLogin());
		statement.setString(2, oUsuario.getSenha());
		ResultSet resultado = statement.executeQuery();
		if (resultado.next()) {
			oUsuario.setPerfil(resultado.getString("perfil"));
			return true;
		}
		return false;
	}

	public Usuario recuperarSenha(String email) throws Exception {
		Usuario oUsuario = new Usuario();
		String sql = "select login, senha, nome, email from model_login where email=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, email);
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) { // retorna apenas um ou nenhum
				oUsuario.setLogin(resultado.getString("login"));
				oUsuario.setSenha(resultado.getString("senha"));
				oUsuario.setNome(resultado.getString("nome"));
				oUsuario.setEmail(resultado.getString("email"));
		}
		return oUsuario;
	}

}
