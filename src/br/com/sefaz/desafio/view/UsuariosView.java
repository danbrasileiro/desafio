package br.com.sefaz.desafio.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;

import br.com.sefaz.desafio.dto.TipoTelefoneDTO;
import br.com.sefaz.desafio.dto.UsuarioDTO;
import br.com.sefaz.desafio.dto.UsuarioTelefoneDTO;
import br.com.sefaz.desafio.exception.DesafioRuntimeException;
import br.com.sefaz.desafio.facade.UsuarioFacade;
import br.com.sefaz.desafio.util.Util;

@ManagedBean(name = "usuariosView")
@ViewScoped
public class UsuariosView extends DesafioView {

	private static final long serialVersionUID = -6737281967583513440L;

	private UsuarioFacade usuarioFacade;
	private List<UsuarioDTO> usuarios;
	private UsuarioDTO usuario;
	private UsuarioTelefoneDTO telefoneUsuario;

	private List<UsuarioTelefoneDTO> telefonesUsuario;
	private List<UsuarioTelefoneDTO> telefonesUsuarioCadastrar;
	private List<UsuarioTelefoneDTO> telefonesUsuarioExcluir;
	
	@PostConstruct
	public void init() {

		try {

			setAcao("Cadastrar");
			setUsuarios(getUsuarioFacade().obterListaUsuarios(null));

			Map<String, String> lMapParametros = obterParametrosRequisicao();

			if (!Util.nullOrEmpty(lMapParametros)) {

				if (!Util.nullOrEmpty(lMapParametros.get("id"))) {

					setUsuario(getUsuarioFacade().obterUsuario(new UsuarioDTO(lMapParametros.get("id"))));
					setTelefonesUsuario(getUsuario().getListaUsuarioTelefone());
					setAcao("Alterar");
				}
				else {

					String lOperacao = lMapParametros.get("operacao");

					if (!Util.nullOrEmpty(lOperacao)) {

						switch (lOperacao.toLowerCase()) {

							case "cadastrar":

								exibirMsgSucesso("msg.registro.cadastrado.com.sucesso");

							break;

							case "alterar":

								exibirMsgSucesso("msg.registro.alterado.com.sucesso");

							break;
						}
					}
				}
			}
		}
		catch (DesafioRuntimeException e) {

			if ("msg.nao.foi.possivel.obter.registro.selecionado".equals(e.getErroMessagem())) {

				exibirMsgErro(e.getErroMessagem());
			}
			else {

				exibirMsgErro("msg.erro.no.carregamento.dos.usuarios");
			}
		}
	}

	public String atualizar() {

		if ("cadastrar".equals(getAcao().toLowerCase())) {

			try {

				getUsuario().setListaUsuarioTelefoneCadastrar(getTelefonesUsuarioCadastrar());
				getUsuarioFacade().inserirUsuario(getUsuario());

				setUsuario(null);

				return "atualizarTela";
			}
			catch (DesafioRuntimeException e) {

				exibirMsgErro("msg.erro.no.cadastro");
			}
		}
		else {

			if ("alterar".equals(getAcao().toLowerCase())) {

				try {

					getUsuario().setListaUsuarioTelefoneCadastrar(getTelefonesUsuarioCadastrar());
					getUsuario().setListaUsuarioTelefoneExcluir(getTelefonesUsuarioExcluir());

					getUsuarioFacade().alterarUsuario(getUsuario());

					setUsuarios(getUsuarioFacade().obterListaUsuarios(null));

					setUsuario(null);

					return "voltar";
				}
				catch (DesafioRuntimeException e) {

					exibirMsgErro("msg.erro.na.alteracao");
				}
			}
		}

		return null;
	}

	public void editarColuna(CellEditEvent event) {

		Object lAntigoNome = event.getOldValue();
		Object lNovoNome = event.getNewValue();
         
		if (!Util.nullOrEmpty(lNovoNome) && !lNovoNome.equals(lAntigoNome)) {

			try {

				getUsuarioFacade().alterarUsuario((UsuarioDTO) ((DataTable) event.getComponent()).getRowData());

				exibirMsgSucesso("msg.registro.alterado.com.sucesso");
			}
			catch (DesafioRuntimeException e) {

				exibirMsgErro("msg.erro.na.alteracao");
			}
		}
	}

    public void excluirRegistro() {

    		try {

    			getUsuarioFacade().excluirUsuario(getUsuario());

    			getUsuarios().remove(getUsuario());

    			setUsuario(null);

    			exibirMsgSucesso("msg.registro.excluido.com.sucesso");
    		}
    		catch (DesafioRuntimeException e) {

    			exibirMsgErro("msg.erro.na.exclusao");
    		}
    }

    public void adicionarTelefone() {

    		String lTipoTelefone = obterParametroRequisicao("sltTipoTelefone_input");
    				
    		if (!Util.nullOrEmpty(lTipoTelefone)) {

    			String lTelefone = obterParametroRequisicao("inpTelefone");
    			
    			if (!Util.nullOrEmpty(lTelefone)) {

        			if (lTelefone.length() >= 14) {

            			if (Util.nullOrEmpty(getTelefonesUsuario())) {

            				setTelefonesUsuario(new ArrayList<UsuarioTelefoneDTO>());
                		}

            			getTelefonesUsuario().add(new UsuarioTelefoneDTO(Integer.parseInt(lTelefone.substring(1, 3)), lTelefone.substring(5, lTelefone.length()), TipoTelefoneDTO.obterPorChave(lTipoTelefone)));

            			if (Util.nullOrEmpty(getTelefonesUsuarioCadastrar())) {

            				setTelefonesUsuarioCadastrar(new ArrayList<UsuarioTelefoneDTO>());
                		}

            			getTelefonesUsuarioCadastrar().add(new UsuarioTelefoneDTO(Integer.parseInt(lTelefone.substring(1, 3)), lTelefone.substring(5, lTelefone.length()), TipoTelefoneDTO.obterPorChave(lTipoTelefone)));
        			}
        			else {

        				exibirMsgErro("msg.favor.informar.um.numero.de.telefone.valido");
        			}
        		}
        		else {

        			exibirMsgErro("msg.favor.informar.o.telefone.do.usuario");
        		}
    		}
    		else {

    			exibirMsgErro("msg.favor.informar.o.tipo.do.telefone");
    		}
    }

    public void excluirTelefoneUsuario() {

    		if (!Util.nullOrEmpty(getTelefoneUsuario())) {

    			if (!Util.nullOrEmpty(getTelefoneUsuario().getId())) {

        			if (Util.nullOrEmpty(getTelefonesUsuarioExcluir())) {

        				setTelefonesUsuarioExcluir(new ArrayList<UsuarioTelefoneDTO>());
            		}

        			getTelefonesUsuarioExcluir().add(getTelefoneUsuario());
        			getTelefonesUsuario().remove(getTelefoneUsuario());
    			}
    			else {

	    			setTelefonesUsuario(getTelefonesUsuario().stream().filter(
	    					reg -> !reg.getDdd().equals(getTelefoneUsuario().getDdd()) 
	    					|| !reg.getNumero().equals(getTelefoneUsuario().getNumero()) 
	    					|| !reg.getTipoTelefone().equals(getTelefoneUsuario().getTipoTelefone())).collect(Collectors.toList()));

	    			setTelefonesUsuarioCadastrar(getTelefonesUsuarioCadastrar().stream().filter(
	    					reg -> !reg.getDdd().equals(getTelefoneUsuario().getDdd()) 
	    					|| !reg.getNumero().equals(getTelefoneUsuario().getNumero()) 
	    					|| !reg.getTipoTelefone().equals(getTelefoneUsuario().getTipoTelefone())).collect(Collectors.toList()));
    			}
    		}
    }

    public void processarPDF(Object pDocumento) {

		Document lDocumento = (Document) pDocumento;
		lDocumento.setPageSize(PageSize.A4.rotate());
    }

	public UsuarioFacade getUsuarioFacade() {

		if (Util.nullOrEmpty(usuarioFacade)) {

			usuarioFacade = new UsuarioFacade();
		}

		return usuarioFacade;
	}

	public List<UsuarioDTO> getUsuarios() {

		return usuarios;
	}
	public void setUsuarios(List<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioDTO getUsuario() {

		if (Util.nullOrEmpty(usuario)) {

			usuario = new UsuarioDTO();
		}

		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public UsuarioTelefoneDTO getTelefoneUsuario() {
		return telefoneUsuario;
	}
	public void setTelefoneUsuario(UsuarioTelefoneDTO telefoneUsuario) {
		this.telefoneUsuario = telefoneUsuario;
	}

	public List<TipoTelefoneDTO> getTiposTelefone() {

		return TipoTelefoneDTO.getTodosValores();
	}

	public List<UsuarioTelefoneDTO> getTelefonesUsuario() {
		return telefonesUsuario;
	}
	public void setTelefonesUsuario(List<UsuarioTelefoneDTO> telefonesUsuario) {
		this.telefonesUsuario = telefonesUsuario;
	}

	public List<UsuarioTelefoneDTO> getTelefonesUsuarioCadastrar() {
		return telefonesUsuarioCadastrar;
	}
	public void setTelefonesUsuarioCadastrar(List<UsuarioTelefoneDTO> telefonesUsuarioCadastrar) {
		this.telefonesUsuarioCadastrar = telefonesUsuarioCadastrar;
	}

	public List<UsuarioTelefoneDTO> getTelefonesUsuarioExcluir() {
		return telefonesUsuarioExcluir;
	}
	public void setTelefonesUsuarioExcluir(List<UsuarioTelefoneDTO> telefonesUsuarioExcluir) {
		this.telefonesUsuarioExcluir = telefonesUsuarioExcluir;
	}
}