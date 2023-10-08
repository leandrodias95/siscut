package br.com.siscut.controller.usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import br.com.siscut.dao.DAOUsuarioRepository;
import br.com.siscut.model.Usuario;


public class ServletUsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ServletUsuarioController() {
     
      
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String msg = "Operação realizada com sucesso!";
		DAOUsuarioRepository oDAOUsuarioRepository = new DAOUsuarioRepository();
		Usuario oUsuario = new Usuario();
		oUsuario.setId(id!=null && !id.isEmpty()? Long.parseLong(id):null);
		oUsuario.setNome(nome);
		oUsuario.setEmail(email);
		oUsuario.setLogin(login);
		oUsuario.setSenha(senha);
		if(oDAOUsuarioRepository.validaLogin(oUsuario.getLogin()) && oUsuario.getId()==null){
			msg= "Já existe usuário com o mesmo login informe outro login";	
		}else {
			if(oUsuario.isNovo()==true) {
				msg="Gravado com sucesso!";	
				} else {
				msg="Atualizado com sucesso!";	
			
				}
			oUsuario = oDAOUsuarioRepository.gravar(oUsuario);
		}
		request.setAttribute("msg", msg);
		request.setAttribute("ousuario", oUsuario);
		request.getRequestDispatcher("/principal/usuario.jsp").forward(request, response);
		
		}catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}
	
	}

}
