package view.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import biz.source_code.miniTemplator.MiniTemplator;
import interfaces.IControleNegocio;
import interfaces.ICrudControleNegocio;
import interfaces.IReferenciaVisaoAcao;
import model.Switch;
import model.Vlan;
import modelo.Tabela;
import util.VisaoEntryPoint;

public class VisaoManterSwitch extends VisaoCrudServletControleGenerica {

	@Override
	public HashMap<String, IReferenciaVisaoAcao> getVisaoAcaoMetodos() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void entryPointProcessaEditDepois(MiniTemplator t,
    			IControleNegocio controleNegocio) {
		ICrudControleNegocio crudControleNegocio =(ICrudControleNegocio)controleNegocio;
		
		List<Tabela> listaEstrangeiro = getListaVlanAtiva(crudControleNegocio);
		
		Tabela vlanSelected = getVlanFromSwitch(crudControleNegocio);
		
		processarTabelaEstrangeira(t, crudControleNegocio, vlanSelected, listaEstrangeiro,"vlan_ativo");
	}

	@SuppressWarnings("rawtypes")
	private Tabela getVlanFromSwitch(ICrudControleNegocio crudControleNegocio) {
		Tabela s = crudControleNegocio.editar();
		if(s==null) {
			s = crudControleNegocio.getNovoObjeto();
		}
		Tabela pk = ((Switch)s).getVlan();
		return pk;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Tabela> getListaVlanAtiva(ICrudControleNegocio crudControleNegocio) {
		Vlan vlan = new Vlan();
		vlan.setAtivo(true);
		List<Tabela> listaEstrangeiro = crudControleNegocio.procurarTodos(vlan);
		return listaEstrangeiro;
	}
	@Override
	public List<VisaoEntryPoint> getEntryPoints() {
		List<VisaoEntryPoint> list = new ArrayList<>();
		list.add(new VisaoEntryPoint(ENTRYPOINT_EDIT_DEPOIS, this::entryPointProcessaEditDepois));
		list.add(new VisaoEntryPoint(ENTRYPOINT_NEW_DEPOIS, this::entryPointProcessaEditDepois));
		return list;
	}

}

