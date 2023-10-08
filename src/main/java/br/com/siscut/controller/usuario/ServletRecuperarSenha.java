package br.com.siscut.controller.usuario;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.siscut.dao.DAOLoginRepository;
import br.com.siscut.dao.DAOUsuarioRepository;
import br.com.siscut.model.Usuario;


public class ServletRecuperarSenha extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public ServletRecuperarSenha() {
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		String recuperaEmail = request.getParameter("recuperaemail");
		String userName="coloque seu e-mail aqui";
		String password="coloque sua senha aqui"; 
		DAOLoginRepository oDaoLoginRepository = new DAOLoginRepository();
		Usuario oUsuario = oDaoLoginRepository.recuperarSenha(recuperaEmail);
			if(recuperaEmail.equals(oUsuario.getEmail())) {
				Properties properties = new Properties();
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls", "true");
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.port", "465");
				properties.put("mail.smtp.socketFactory.port", "465");
				properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				Session session = Session.getInstance(properties, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName,password);
					}
				});
				Address[]toUser=InternetAddress.parse(oUsuario.getEmail());
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(userName));
				message.setRecipients(Message.RecipientType.TO, toUser);
				message.setSubject("Siscut - Redefinição de senha");
				message.setText("Olá senhor(a) "+oUsuario.getNome()+" segue abaixo o login e a senha para acessar o sistema\n"
						+ "Login: "+oUsuario.getLogin()+"\n"+"Senha: "+oUsuario.getSenha()
						+"\nSiscut +55(00)0000-0000");
				Transport.send(message);
				RequestDispatcher retornar = request.getRequestDispatcher("senhaemail.jsp");
				request.setAttribute("msgSuccess", "E-mail enviado!");
				retornar.forward(request, response);
			}else {
				RequestDispatcher retornar = request.getRequestDispatcher("senhaemail.jsp");
				request.setAttribute("msg", "E-mail não cadastrado!");
				retornar.forward(request, response);
			}
		}catch(Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response); 
		}

	}
}
