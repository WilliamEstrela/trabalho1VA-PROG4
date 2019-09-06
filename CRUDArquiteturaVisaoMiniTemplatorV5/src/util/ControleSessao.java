package util;

import javax.servlet.http.HttpSession;

import interfaces.IControleSessao;

public class ControleSessao implements IControleSessao {

	HttpSession session;
	
	public ControleSessao(HttpSession session) {
		this.session = session;
	}
	
	@Override
	public void setVariavel(String name, Object val) {
		this.session.setAttribute(name, val);
	}

	@Override
	public Object getVariavel(String name) {
		return this.session.getAttribute(name);
	}

}