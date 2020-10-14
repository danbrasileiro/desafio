package br.com.sefaz.desafio.dao;

import java.util.ArrayList;

import br.com.sefaz.desafio.dto.UsuarioTelefoneDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;

public interface UsuarioTelefoneDAOIf {

	public ArrayList<UsuarioTelefoneDTO> obterListaTelefonesUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException;

	public UsuarioTelefoneDTO inserirTelefoneUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException;

	public int excluirTelefoneUsuario(UsuarioTelefoneDTO pUsuarioTelefone) throws DesafioRuntimeException;
}