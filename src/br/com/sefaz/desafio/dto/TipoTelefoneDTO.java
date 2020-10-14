package br.com.sefaz.desafio.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.sefaz.desafio.util.Util;

public class TipoTelefoneDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final TipoTelefoneDTO CELULAR = new TipoTelefoneDTO("C", "Celular");
    public static final TipoTelefoneDTO RESIDENCIAL = new TipoTelefoneDTO("R", "Residencial");

	private static Map<String, TipoTelefoneDTO> tipoTelefone = new HashMap<String, TipoTelefoneDTO>();

    static {

    		tipoTelefone.put(CELULAR.getCodigo(), CELULAR);
    		tipoTelefone.put(RESIDENCIAL.getCodigo(), RESIDENCIAL);
    }

    private String codigo;
    private String nome;

    private TipoTelefoneDTO(String pCodigo, String pNome) {
    		codigo = pCodigo;
    		nome = pNome;
    }

    public void setCodigo(String pCodigo) {
    	
    		codigo = pCodigo;
    }
    public String getCodigo() {

    		return codigo;
    }

    public void setNome(String pNome) {

    		nome = pNome;
    }
    public String getNome() {

    		if (!Util.nullOrEmpty(codigo)) {

    			return tipoTelefone.get(codigo).nome;
    		}

    		return nome;
    }

    public Object getValue() {

    		return getCodigo();
    }

    public static List<TipoTelefoneDTO> getTodosValores() {

        return new ArrayList<TipoTelefoneDTO>(tipoTelefone.values());
    }

    public static TipoTelefoneDTO obterPorChave(String pCodigo) {

    		return tipoTelefone.get(pCodigo);
    }

    public boolean equals(Object pObjeto) {

    		return !Util.nullOrEmpty(pObjeto) && pObjeto instanceof TipoTelefoneDTO && ((TipoTelefoneDTO) pObjeto).getCodigo().equals(getCodigo());
    }
}