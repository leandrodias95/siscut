package br.com.siscut.controller.usuario;

import java.io.IOException;
import java.util.List;

import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.siscut.dao.DAOUsuarioRepository;
import br.com.siscut.model.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@MultipartConfig
public class ServletUsuarioController extends ServletGenericUtil {
	private static final long serialVersionUID = 1L;

	public ServletUsuarioController() {

	}

	private DAOUsuarioRepository oDAOUsuarioRepository = new DAOUsuarioRepository();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String acao = request.getParameter("acao");
			if (acao != null && !acao.isEmpty() && acao.equals("deletar")) {
				String idUser = request.getParameter("id");
				oDAOUsuarioRepository.deletarUsuario(Long.parseLong(idUser));
				request.setAttribute("msg", "Excluído com sucesso!");
				List<Usuario>oUsuarios = oDAOUsuarioRepository.consultaLista(super.getUserLogado(request));
				request.setAttribute("ousuarios", oUsuarios);
				request.setAttribute("totalPagina", oDAOUsuarioRepository.totalPagina(super.getUserLogado(request))); //retorna o total da página
				request.getRequestDispatcher("/principal/usuario.jsp").forward(request, response);
			}else if(acao != null && !acao.isEmpty() && acao.equals("deletarAjax")) {
				String idUser = request.getParameter("id");
				oDAOUsuarioRepository.deletarUsuario(Long.parseLong(idUser));
				response.getWriter().write("Usuário excluído com sucesso");				
			} 
			else if(acao != null && !acao.isEmpty() && acao.equals("buscarNomeAjax")) {
				String nomeUser = request.getParameter("buscaNome");
				List<Usuario> dadosJson = oDAOUsuarioRepository.consultaPorNome(nomeUser,super.getUserLogado(request));
				ObjectMapper mapper = new ObjectMapper();
				String jsonUserNome = mapper.writeValueAsString(dadosJson);
				response.getWriter().write(jsonUserNome);
			}
			else if(acao !=null && !acao.isEmpty() && acao.equals("buscarEditar")) {
				String idUser = request.getParameter("id");
				Usuario oUsuario  = oDAOUsuarioRepository.consultarUsuarioId(idUser,super.getUserLogado(request));
				List<Usuario>oUsuarios = oDAOUsuarioRepository.consultaLista(super.getUserLogado(request));
				request.setAttribute("ousuarios", oUsuarios);
				request.setAttribute("msg", "Usuario em Edição");
				request.setAttribute("ousuario", oUsuario); //armazena os dados na variavel para carregar na tela usuario.jsp
				request.setAttribute("totalPagina", oDAOUsuarioRepository.totalPagina(super.getUserLogado(request))); //retorna o total da página
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			else if(acao !=null && !acao.isEmpty() && acao.equals("listarUsuarios")) {
				List<Usuario>oUsuarios = oDAOUsuarioRepository.consultaLista(super.getUserLogado(request));
				request.setAttribute("ousuarios", oUsuarios);
				request.setAttribute("msg", "Usuarios carregados");
				request.setAttribute("totalPagina", oDAOUsuarioRepository.totalPagina(super.getUserLogado(request))); //retorna o total da página
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			
			else if(acao!=null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
				String idUser = request.getParameter("id");
				Usuario usuario = oDAOUsuarioRepository.consultarUsuarioId(idUser, super.getUserLogado(request));
				if(usuario.getFotouser()!=null && !usuario.getFotouser().isEmpty()) {
					response.setHeader("Content-Disposition", "attachement;filename=arquivo."+usuario.getExtensaofotouser()); //nome do arquivo e cocatena com a extensão
					response.getOutputStream().write(new Base64().decodeBase64(usuario.getFotouser().split("\\,")[1])); //decodifica a string base64, traz a foto e separa o "data"
				}
			}
			else if(acao!=null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
				Integer offset = Integer.parseInt(request.getParameter("pagina"));
				List<Usuario>oUsuarios = oDAOUsuarioRepository.consultaListaPaginado(super.getUserLogado(request), offset);
				request.setAttribute("ousuarios", oUsuarios);
				request.setAttribute("totalPagina", oDAOUsuarioRepository.totalPagina(super.getUserLogado(request))); //retorna o total da página
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
			else {
				List<Usuario>oUsuarios = oDAOUsuarioRepository.consultaLista(super.getUserLogado(request));
				request.setAttribute("ousuarios", oUsuarios);
				request.setAttribute("totalPagina", oDAOUsuarioRepository.totalPagina(super.getUserLogado(request))); //retorna o total da página
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher retornar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			retornar.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String login = request.getParameter("login");
			String senha = request.getParameter("senha");
			String perfil = request.getParameter("perfil");
			String sexo = request.getParameter("sexo");
			String cep = request.getParameter("cep");
			String logradouro = request.getParameter("logradouro");
			String bairro = request.getParameter("bairro");
			String localidade = request.getParameter("localidade");
			String uf = request.getParameter("uf");
			String numero = request.getParameter("numero");
			String msg = "Operação realizada com sucesso!";

			Usuario oUsuario = new Usuario();
			oUsuario.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
			oUsuario.setNome(nome);
			oUsuario.setEmail(email);
			oUsuario.setLogin(login);
			oUsuario.setSenha(senha);
			oUsuario.setPerfil(perfil);
			oUsuario.setSexo(sexo);
			oUsuario.setCep(cep);
			oUsuario.setLogradouro(logradouro);
			oUsuario.setBairro(bairro);
			oUsuario.setLocalidade(localidade);
			oUsuario.setUf(uf);
			oUsuario.setNumero(numero);
			
			if(JakartaServletFileUpload.isMultipartContent(request)) {
				Part part = request.getPart("filefoto"); 
				if(part.getSize()>0) {
					byte[] foto = IOUtils.toByteArray(part.getInputStream()); //converte imagem para byte
					String imagemBase64 = "data:" + part.getContentType().split("\\/")[1]+";base64," + new Base64().encodeBase64String(foto);
					oUsuario.setFotouser(imagemBase64);
					oUsuario.setExtensaofotouser(part.getContentType().split("\\/")[1]);
					
				}
			}
			if (oDAOUsuarioRepository.validaLogin(oUsuario.getLogin()) && oUsuario.getId() == null) {
				msg = "Já existe usuário com o mesmo login informe outro login";
			} else {
				if (oUsuario.isNovo() == true) {
					msg = "Gravado com sucesso!";
				} else {
					msg = "Atualizado com sucesso!";

				}
				oUsuario = oDAOUsuarioRepository.gravar(oUsuario,super.getUserLogado(request));
			}
			List<Usuario>oUsuarios = oDAOUsuarioRepository.consultaLista(super.getUserLogado(request));
			request.setAttribute("ousuarios", oUsuarios);
			request.setAttribute("msg", msg);
			request.setAttribute("ousuario", oUsuario);
			request.setAttribute("totalPagina", oDAOUsuarioRepository.totalPagina(super.getUserLogado(request))); //retorna o total da página
			request.getRequestDispatcher("/principal/usuario.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("erro.jsp").forward(request, response);
		}

	}

}
