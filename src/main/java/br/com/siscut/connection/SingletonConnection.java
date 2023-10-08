package br.com.siscut.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingletonConnection {
	private static String url="jdbc:postgresql://localhost:5432/siscut?autoReconnect=true";
	private static String usuario="postgres";
	private static String senha="Rock@2002";
	private static Connection connection = null;
	
	static {
		conexao();
	}
	
	public SingletonConnection() {
		conexao();
	}
	
	public static void conexao() {
		try {
			if(connection==null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(url, usuario, senha);
				connection.setAutoCommit(false);
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static Connection getConnection() {
		return connection;
	}
	
	
}

