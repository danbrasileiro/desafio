package br.com.sefaz.desafio.dto;

import br.com.sefaz.desafio.util.Util;

public class UsuarioTelefoneDTO extends DesafioDTO {

	private Long id;
	private Integer ddd;
	private String numero;
	private TipoTelefoneDTO tipoTelefone;
	private UsuarioDTO usuario;

    public UsuarioTelefoneDTO() {}
    public UsuarioTelefoneDTO(UsuarioDTO usuario) {
		super();
		this.usuario = usuario;
	}
    public UsuarioTelefoneDTO(Integer ddd, String numero, TipoTelefoneDTO tipoTelefone) {
		super();
		this.ddd = ddd;
		this.numero = numero;
		this.tipoTelefone = tipoTelefone;
	}
	public UsuarioTelefoneDTO(Long id, Integer ddd, String numero, TipoTelefoneDTO tipoTelefone, UsuarioDTO usuario) {
		super();
		this.id = id;
		this.ddd = ddd;
		this.numero = numero;
		this.tipoTelefone = tipoTelefone;
		this.usuario = usuario;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDdd() {
		return ddd;
	}
	public void setDdd(Integer ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}

	public TipoTelefoneDTO getTipoTelefone() {
		return tipoTelefone;
	}
	public void setTipoTelefone(TipoTelefoneDTO tipoTelefone) {
		this.tipoTelefone = tipoTelefone;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	@Override
    public boolean equals(Object objeto) {
    
		return !Util.nullOrEmpty(objeto) && !Util.nullOrEmpty(getId()) && objeto instanceof UsuarioTelefoneDTO && !Util.nullOrEmpty(((UsuarioTelefoneDTO) objeto).getId()) && ((UsuarioTelefoneDTO) objeto).getId().equals(getId());
    }
}