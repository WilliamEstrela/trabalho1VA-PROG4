package view.controller;

import java.util.HashMap;
import java.util.List;

import biz.source_code.miniTemplator.MiniTemplator;
import interfaces.IControleNegocio;
import interfaces.IReferenciaVisaoAcao;
import util.VisaoEntryPoint;

public class VisaoAutenticarControle extends VisaoServletControleGenerica {

	protected void processFormularioLogin(MiniTemplator t,
			IControleNegocio controleNegocio) {
		
	}
	@Override
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodos() {
		HashMap<String, IReferenciaVisaoAcao> acaoMetodos = new HashMap<>();
		acaoMetodos.put("formularioLogin", this::processFormularioLogin);
		return acaoMetodos;
	}

	@Override
	public List<VisaoEntryPoint> getEntryPoints() {
		return null;
	}

}
