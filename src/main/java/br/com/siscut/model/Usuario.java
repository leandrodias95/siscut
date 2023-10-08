package br.com.siscut.model;

public class Usuario {
	private Long id;
	private String login;
	private String senha;
	private String email;
	private String nome;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public boolean isNovo() {
		if(this.id==null) {
		return true;
		}else if(this.id!=null || this.id >0) {
			return false;
		}
		return this.id == null;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", login=" + login + ", senha=" + senha + ", email=" + email + ", nome=" + nome
				+ "]";
	}	

}
