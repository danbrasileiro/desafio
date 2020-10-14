package br.com.sefaz.desafio.dao;

import java.util.ArrayList;

import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;

public interface UsuarioDAOIf {

	public ArrayList<UsuarioDTO> obterListaUsuarios(UsuarioDTO pUsuario) throws DesafioRuntimeException;

	public UsuarioDTO obterUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException;

	public void inserirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException;

	public int alterarUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException;

	public int excluirUsuario(UsuarioDTO pUsuario) throws DesafioRuntimeException;
}