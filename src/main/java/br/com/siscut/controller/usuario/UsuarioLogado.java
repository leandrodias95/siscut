package br.com.siscut.controller.usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import br.com.siscut.dao.DAOLoginRepository;
import br.com.siscut.model.Usuario;

@WebServlet(name = "UsuarioLogado", urlPatterns = { "/principal/UsuarioLogado", "/UsuarioLogado" })
public class UsuarioLogado extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOLoginRepository daoLoginRepository = new DAOLoginRepository();

	public UsuarioLogado() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");
		if(acao!=null && !acao.isEmpty() && acao.equalsIgnoreCase("logout")) {
			request.getSession().invalidate(); //encerra a sessão
			RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
			redirecionar.forward(request, response);
		}else {
		doPost(request, response);
	}
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String url = request.getParameter("url");

		try {
			if (login != null && !login.isEmpty() && (senha != null && !senha.isEmpty())) {
				Usuario modelUsuario = new Usuario();
				modelUsuario.setLogin(login);
				modelUsuario.setSenha(senha);
				if (daoLoginRepository.validarAutenticacao(modelUsuario)) {
					request.getSession().setAttribute("usuario", modelUsuario.getLogin());
					request.getSession().setAttribute("perfil", modelUsuario.getPerfil());
					request.getSession().setAttribute("fotoPerfil", modelUsuario.getFotouser());
					if (url == null || url.equals("null")) {
						url = "/principal/principal.jsp";
						RequestDispatcher redirecionar = request.getRequestDispatcher(url);
						redirecionar.forward(request, response);
					}
					RequestDispatcher redirecionar = request.getRequestDispatcher("principal/principal.jsp");
					redirecionar.forward(request, response);
				} else {
					RequestDispatcher redirecionar = request.getRequestDispatcher("/index.jsp");
					request.setAttribute("msg", "Informe o login ou senha corretamente!");
					redirecionar.forward(request, response);
				}

			} else {
				RequestDispatcher redirecionar = request.getRequestDispatcher("index.jsp");
				request.setAttribute("msg", "Informe o login ou senha corretamente!");
				redirecionar.forward(request, response);
			}

		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
	}

}
