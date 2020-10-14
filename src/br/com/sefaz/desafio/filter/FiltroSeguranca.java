package br.com.sefaz.desafio.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sefaz.desafio.view.DesafioView;

public class FiltroSeguranca extends DesafioView implements Filter {

	private static final long serialVersionUID = -8950168568340755147L;

	private static final String FACES_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>";

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pFilterChain) throws IOException, ServletException {

		HttpServletRequest lHttpServletRequest = (HttpServletRequest) pRequest;
		HttpServletResponse lHttpServletResponse = (HttpServletResponse) pResponse;

		String lUrlAcesso = lHttpServletRequest.getServletPath();

		if (lUrlAcesso.contains(".xhtml") || (!"/login.jsf".equals(lUrlAcesso) && lUrlAcesso.lastIndexOf("/") == 0 && obterUsuarioLogado(lHttpServletRequest) == null)) {

			String lUrlPaginaLogin = pRequest.getScheme() + "://" + lHttpServletRequest.getHeader("host") + lHttpServletRequest.getContextPath() + "/login.jsf";

			if (lUrlAcesso.contains(".jsf")) {

			    if ("partial/ajax".equals(lHttpServletRequest.getHeader("Faces-Request"))) {

			    		pResponse.setContentType("text/xml");
			    		pResponse.setCharacterEncoding("UTF-8");
			    		pResponse.getWriter().printf(FACES_REDIRECT_XML, lUrlPaginaLogin + "?sessao=expirou");
			    }
			    else {

			    		lHttpServletResponse.sendRedirect(lUrlPaginaLogin + "?sessao=expirou");
			    }
			}
			else {

				lHttpServletResponse.sendRedirect(lUrlPaginaLogin);
			}
		}
		else {

			pFilterChain.doFilter(pRequest, pResponse);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}