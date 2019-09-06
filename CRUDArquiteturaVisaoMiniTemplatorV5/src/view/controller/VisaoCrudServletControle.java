package view.controller;

import java.util.HashMap;
import java.util.List;

import interfaces.IReferenciaVisaoAcao;
import util.VisaoEntryPoint;

/**Classe utilizado nos casos de uso, mas não deve ser estendida, pois é final, a classe estendida
 * deve ser a {@link VisaoCrudServletControleGenerica}
 * @author guili
 *
 */
public final class VisaoCrudServletControle extends VisaoCrudServletControleGenerica {
	@Override
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodos() {
		return null;
	}

	@Override
	public List<VisaoEntryPoint> getEntryPoints() {
		return null;
	}

}
