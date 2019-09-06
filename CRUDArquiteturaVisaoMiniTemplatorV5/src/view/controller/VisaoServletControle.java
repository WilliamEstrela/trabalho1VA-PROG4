package view.controller;

import java.util.HashMap;
import java.util.List;

import interfaces.IReferenciaVisaoAcao;
import util.VisaoEntryPoint;

/**
 * @author guili
 * Classe não deve ser herdada, por isso é final, se necessitar estender seu comportamento utilizar a classe
 * {@link VisaoCrudServletControleGenerica}
 */
public final class VisaoServletControle extends VisaoServletControleGenerica {
	@Override
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodos() {
		return null;
	}

	@Override
	public List<VisaoEntryPoint> getEntryPoints() {
		return null;
	}


}
