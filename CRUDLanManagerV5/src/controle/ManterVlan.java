package controle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anotacoes.CasoUso;
import interfaces.IReferenciaAcaoNegocio;
import model.Vlan;
import util.NegocioEntryPoint;
import util.Retorno;

@CasoUso(nomeCasoDeUso="ManterVlan", precisaAutenticar=false, paginaComum=true)
public class ManterVlan extends CrudControle<Vlan,Integer> {

	@Override
	public Vlan getNovoObjeto() {
		return new Vlan();
	}
	public Retorno acaoAtivar(String acao) {
		Vlan vlan = this.editar();
		vlan.setAtivo(true);
		
		Retorno ret = dao.alterar(vlan);
		ret.setAcao(CrudControle.LISTAR_TODOS);
		
		return ret;
	}

	public Retorno acaoDesativar(String acao) {
		Vlan vlan = this.editar();
		vlan.setAtivo(false);
		
		Retorno ret = dao.alterar(vlan);
		ret.setAcao(CrudControle.LISTAR_TODOS);
		
		return ret;
	}
	@Override
	public HashMap<String, IReferenciaAcaoNegocio> getAcaoMetodos() {
		HashMap<String, IReferenciaAcaoNegocio> acaoMetodos = new HashMap<>();
		acaoMetodos.put("ativar",	this::acaoAtivar);
		acaoMetodos.put("desativar",this::acaoDesativar);
		acaoMetodos.put("ligar",  this::acaoAtivar);
		acaoMetodos.put("desligar",this::acaoDesativar);
		acaoMetodos.put("teste", this::acaoTeste);
		return acaoMetodos;
	}
	
	public Retorno acaoTeste(String acao) {
		Retorno ret = new Retorno(true, null,acao);
		String teste = (String)this.getVariaveis().get("teste");
		this.getVariaveis().put("teste", teste.toUpperCase());
		return ret;
	}

	protected Retorno entryPointValidarVlanNumber(String acao) {

		String vlanID = (String) this.getVariaveis().get("vlan");

		if (vlanID != null && vlanID.length() >= 2 && vlanID.length() <= 4) {
			return new Retorno(true, null, acao);
		} else {
			String action2 = acao.equals(INCLUIR) ? NOVO : EDITAR;
			return new Retorno(false, "Campo Vlan deve ter entre 2 e 4 caracteres!", action2);
		}
	}

	@Override
	public List<NegocioEntryPoint> getEntryPoints() {
		List<NegocioEntryPoint> list = new ArrayList<>();
		list.add(new NegocioEntryPoint(ENTRYPOINT_ALTERAR_ANTES, this::entryPointValidarVlanNumber));
		list.add(new NegocioEntryPoint(ENTRYPOINT_INCLUIR_ANTES, this::entryPointValidarVlanNumber));
		return list;
	}
	
	
}
