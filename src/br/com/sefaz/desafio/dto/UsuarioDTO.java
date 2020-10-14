package br.com.sefaz.desafio.dto;

import java.util.List;

import br.com.sefaz.desafio.util.Util;

public class UsuarioDTO extends DesafioDTO {

	private String nome;
	private String email;
	private String senha;
	private List<UsuarioTelefoneDTO> listaUsuarioTelefone;
	private List<UsuarioTelefoneDTO> listaUsuarioTelefoneCadastrar;
	private List<UsuarioTelefoneDTO> listaUsuarioTelefoneExcluir;

	public UsuarioDTO() {}
	public UsuarioDTO(String email) {

		super();
		this.email = email;
	}
	public UsuarioDTO(String nome, String email) {

		super();
		this.nome = nome;
		this.email = email;
	}

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

    public List<UsuarioTelefoneDTO> getListaUsuarioTelefone() {
		return listaUsuarioTelefone;
	}
	public void setListaUsuarioTelefone(List<UsuarioTelefoneDTO> listaUsuarioTelefone) {
		this.listaUsuarioTelefone = listaUsuarioTelefone;
	}

	public List<UsuarioTelefoneDTO> getListaUsuarioTelefoneCadastrar() {
		return listaUsuarioTelefoneCadastrar;
	}
	public void setListaUsuarioTelefoneCadastrar(List<UsuarioTelefoneDTO> listaUsuarioTelefoneCadastrar) {
		this.listaUsuarioTelefoneCadastrar = listaUsuarioTelefoneCadastrar;
	}

	public List<UsuarioTelefoneDTO> getListaUsuarioTelefoneExcluir() {
		return listaUsuarioTelefoneExcluir;
	}
	public void setListaUsuarioTelefoneExcluir(List<UsuarioTelefoneDTO> listaUsuarioTelefoneExcluir) {
		this.listaUsuarioTelefoneExcluir = listaUsuarioTelefoneExcluir;
	}

	@Override
    public boolean equals(Object objeto) {
    
        return !Util.nullOrEmpty(objeto) && !Util.nullOrEmpty(getEmail()) && objeto instanceof UsuarioDTO && !Util.nullOrEmpty(((UsuarioDTO) objeto).getEmail()) && ((UsuarioDTO) objeto).getEmail().equals(getEmail());
    }
}