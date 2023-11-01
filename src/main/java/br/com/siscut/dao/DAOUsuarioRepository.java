package br.com.siscut.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.siscut.connection.SingletonConnection;
import br.com.siscut.model.Usuario;

public class DAOUsuarioRepository {
	Connection connection;

	public DAOUsuarioRepository() {
		connection = SingletonConnection.getConnection();
	}

	public Usuario gravar(Usuario usuario, Long userLogado) throws SQLException {
		if (usuario.isNovo() == true) {
			try {
				String sql = "insert into model_login(login,senha,email,nome,usuario_id,perfil)values(?,?,?,?,?,?)";
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, usuario.getLogin());
				statement.setString(2, usuario.getSenha());
				statement.setString(3, usuario.getEmail());
				statement.setString(4, usuario.getNome());
				statement.setLong(5, userLogado);
				statement.setString(6, usuario.getPerfil());
				statement.execute();
				connection.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} else if (usuario.isNovo() == false) {
			try {
				String sql = "update model_login set login=?, senha=?, email=?, nome=?, perfil=? where id ="
						+ usuario.getId();
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setString(1, usuario.getLogin());
				statement.setString(2, usuario.getSenha());
				statement.setString(3, usuario.getEmail());
				statement.setString(4, usuario.getNome());
				statement.setString(5, usuario.getPerfil());
				statement.executeUpdate();
				connection.commit();
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return this.consultarUsuario(usuario.getLogin(),userLogado);
	}

	public List<Usuario> consultaPorNome(String nome, Long userLogado)throws SQLException{
		List<Usuario> retorno = new ArrayList<Usuario>();
		String sql ="select * from model_login where upper(nome) like upper(?) and useradmin is false and usuario_id=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%"+nome+"%");
		statement.setLong(2, userLogado);
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) {
			Usuario oUsuario = new Usuario();
			oUsuario.setId(resultado.getLong("id"));
			oUsuario.setNome(resultado.getString("nome"));
			oUsuario.setEmail(resultado.getString("email"));
			oUsuario.setLogin(resultado.getString("login"));
			//oUsuario.setSenha(resultado.getString("senha"));
			retorno.add(oUsuario);
			
		}
		return retorno;
	}
	
	public List<Usuario> consultaLista(Long userLogado)throws SQLException{
		List<Usuario> retorno = new ArrayList<Usuario>();
		String sql ="select * from model_login where useradmin is false and usuario_id ="+userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) {
			Usuario oUsuario = new Usuario();
			oUsuario.setId(resultado.getLong("id"));
			oUsuario.setNome(resultado.getString("nome"));
			retorno.add(oUsuario);
			
		}
		return retorno;
	}
	
	public Usuario consultarUsuarioLogado(String login) throws SQLException {
		Usuario usuario = new Usuario();
		String sql = "select * from model_login where upper(login) = upper('" + login + "')";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) {
		usuario.setId(resultado.getLong("id"));
		usuario.setLogin(resultado.getString("login"));
		usuario.setSenha(resultado.getString("senha"));
		usuario.setEmail(resultado.getString("email"));
		usuario.setNome(resultado.getString("nome"));
		usuario.setUserAdmin(resultado.getBoolean("userAdmin"));
		usuario.setPerfil(resultado.getString("perfil"));
		}
		return usuario;
	}
	
	public Usuario consultarUsuario(String login, Long userLogado) throws SQLException {
		Usuario usuario = new Usuario();
		String sql = "select * from model_login where upper(login) = upper('" + login + "') and useradmin is false and usuario_id="+userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) {
		usuario.setId(resultado.getLong("id"));
		usuario.setLogin(resultado.getString("login"));
		usuario.setSenha(resultado.getString("senha"));
		usuario.setEmail(resultado.getString("email"));
		usuario.setNome(resultado.getString("nome"));
		}
		return usuario;
	}
	
	public Usuario consultarUsuario(String login) throws SQLException {
		Usuario usuario = new Usuario();
		String sql = "select * from model_login where upper(login) = upper('" + login + "') and useradmin is false";
		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();
		while (resultado.next()) {
		usuario.setId(resultado.getLong("id"));
		usuario.setLogin(resultado.getString("login"));
		usuario.setSenha(resultado.getString("senha"));
		usuario.setEmail(resultado.getString("email"));
		usuario.setNome(resultado.getString("nome"));
		}
		return usuario;
	}
	public Usuario consultarUsuarioId(String Id, Long userLogado) throws SQLException {
		Usuario oUsuario = new Usuario();
		String sql = "select * from model_login where id=? and useradmin is false and usuario_id="+userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, Long.parseLong(Id));
		ResultSet resultado = statement.executeQuery();
		while(resultado.next()) {
			oUsuario.setId(resultado.getLong("id"));
			oUsuario.setNome(resultado.getString("nome"));
			oUsuario.setEmail(resultado.getString("email"));
			oUsuario.setLogin(resultado.getString("login"));
			oUsuario.setSenha(resultado.getString("senha"));
			oUsuario.setPerfil(resultado.getString("perfil"));
		}
		return oUsuario;
	}

	public boolean validaLogin(String login) throws Exception {
		String sql="select count(1)>0 as existe from model_login where upper(login)=upper('"+login+"')";
		PreparedStatement retorno = connection.prepareStatement(sql);
		ResultSet resultado = retorno.executeQuery();
		resultado.next(); //entra nos resultados do sql
		return resultado.getBoolean("existe");
	}
	
	public void deletarUsuario(Long id) {
		try {
		String sql = "delete from model_login where id=?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		statement.executeUpdate();
		connection.commit();
		}catch(Exception e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
