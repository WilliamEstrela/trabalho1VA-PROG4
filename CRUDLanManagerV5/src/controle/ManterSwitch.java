package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anotacoes.CasoUso;
import interfaces.IReferenciaAcaoNegocio;
import model.Switch;
import util.NegocioEntryPoint;
import util.Retorno;

@CasoUso(nomeCasoDeUso="manterSwitch", precisaAutenticar=false, paginaComum=true)
public class ManterSwitch extends CrudControle<Switch, Integer> {

	@Override
	public Switch getNovoObjeto() {
		return new Switch();
	}

	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() {
		return null;
	}

	protected Retorno entryPointMudarAcaoPadraoPosIncluirAlterar(String acao) {
		return new Retorno(true, null, LISTAR_TODOS);
	}

	@Override
	public List<NegocioEntryPoint> getEntryPoints() {
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.add(new NegocioEntryPoint(ENTRYPOINT_ALTERAR_DEPOIS, this::entryPointMudarAcaoPadraoPosIncluirAlterar));
		list.add(new NegocioEntryPoint(ENTRYPOINT_INCLUIR_DEPOIS, this::entryPointMudarAcaoPadraoPosIncluirAlterar));
		return list;
	}

}
