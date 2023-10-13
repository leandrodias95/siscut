package br.com.siscut.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.siscut.connection.SingletonConnection;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/principal/*"})
public class FilterAutenticacao extends HttpFilter implements Filter {
       
	private static Connection connection;
   
    public FilterAutenticacao() {
        
        
    }

	public void destroy() {
		try {
			connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try {
		HttpServletRequest req = (HttpServletRequest) request; 
		HttpSession session = req.getSession(); //referencia para a sessão do usuario, permitindo que a manipule / cria uma nova sessão para o usuario
		String usuarioLogado = (String) session.getAttribute("usuario");
		String urlParaAutenticar = req.getServletPath();
		
		if(usuarioLogado == null && !urlParaAutenticar.equalsIgnoreCase("principal/UsuarioLogado")) { //não está logado
			RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp?="+urlParaAutenticar);
			request.setAttribute("msg","Por favor realize o login");
			redirecionar.forward(request, response);
		}
		chain.doFilter(request, response);
		connection.commit();
	}catch(Exception e) {
		e.printStackTrace();
		RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
		request.setAttribute("msg",e.getMessage());
		redirecionar.forward(request, response);
		try {
			connection.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
			}
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingletonConnection.getConnection();
	}

}
