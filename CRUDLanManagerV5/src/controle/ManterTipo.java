package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anotacoes.CasoUso;
import interfaces.IReferenciaAcaoNegocio;
import model.Tipo;
import util.NegocioEntryPoint;
import util.Retorno;

@CasoUso(nomeCasoDeUso="manterTipo", precisaAutenticar=false, paginaComum=true)
public class ManterTipo extends CrudControle<Tipo,Integer> {


	@Override
	public Tipo getNovoObjeto() {
		return new Tipo();
	}

	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() {
		return null;
	}

	protected Retorno entryPointMudarAcaoPadraoPosApagar(String acao) {
		return new Retorno(true, null, LISTAR_TODOS);
	}

	@Override
	public List<NegocioEntryPoint> getEntryPoints() {
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.add(new NegocioEntryPoint(ENTRYPOINT_APAGAR_DEPOIS, this::entryPointMudarAcaoPadraoPosApagar));
		return list;
	}

	
	
}
