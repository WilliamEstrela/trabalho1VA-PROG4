package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anotacoes.CasoUso;
import interfaces.IReferenciaAcaoNegocio;
import model.Reprovacao;
import model.Tipo;
import util.NegocioEntryPoint;
import util.Retorno;

@CasoUso(nomeCasoDeUso="manterReprovacao", precisaAutenticar=false, paginaComum=true)
public class ManterReprovacao extends CrudControle<Reprovacao,Integer> {

	@Override
	public Reprovacao getNovoObjeto() {
		return new Reprovacao();
	}

	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() {
		return null;
	}

	protected Retorno entryPointMudarAcaoPadraoPosApagar(String acao) {
		return new Retorno(true, null, LISTAR_TODOS);
	}
	
	protected Retorno entryPointValidarCPF(String acao) {
		String cpf = (String) this.getVariaveis().get("cpf");
		
		if(cpf.isEmpty()) {
			String action2 = acao.equals(INCLUIR) ? NOVO : EDITAR;
			return new Retorno(false, "Campo CPF esta invalido!", action2);
		}
		return new Retorno(true, null, LISTAR_TODOS);
	}

	@Override
	public List<NegocioEntryPoint> getEntryPoints() {
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.add(new NegocioEntryPoint(ENTRYPOINT_APAGAR_DEPOIS, this::entryPointMudarAcaoPadraoPosApagar));
		list.add(new NegocioEntryPoint(ENTRYPOINT_INCLUIR_ANTES, this::entryPointValidarCPF));
		return list;
	}
}
