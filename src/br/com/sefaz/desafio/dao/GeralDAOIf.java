package br.com.sefaz.desafio.dao;

import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;

public interface GeralDAOIf {

	public UsuarioDTO logarSistema(UsuarioDTO pUsuario) throws DesafioRuntimeException;
}