package br.com.sefaz.desafio.facade;

import br.com.sefaz.desafio.bo.GeralBO;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;

public class GeralFacade {

	private static final GeralBO geralBO = new GeralBO();

	public UsuarioDTO logarSistema(UsuarioDTO pUsuario) throws DesafioRuntimeException {

		return geralBO.logarSistema(pUsuario);
	}
}