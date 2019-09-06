package test;


import model.Equipamento;
import model.Porta;
import model.PortaVlan;
import model.Switch;
import model.Tipo;
import model.Vlan;
import persistencia.DAOGeneric;
import util.Retorno;

public class testeDAO {
	public static void main(String[] args) {
		DAOGeneric dao = DAOGeneric.getIntancia();
		
		Vlan v = new Vlan();
		v.setVlan(1);
		v.setNome("Default");
		if(!dao.existir(v)){
			Retorno ret = dao.incluir(v);
			System.out.println("Retorno incluir:"+ret);
		}
		
		Vlan v2 = new Vlan();
		v2.setVlan(2);
		v2.setNome("Administrativo");
		
		v.setNome("Default-01");
		System.out.println("update:"+dao.alterar(v));
		
		System.out.println("exist:"+dao.existir(v));
		
		//System.out.println("delete:"+dao.delete(v));
		
		System.out.println("exists2:"+dao.existir(v2));
		if(!dao.existir(v2)){
			Retorno ret = dao.incluir(v2);
			System.out.println("Vlan2:"+ret);
		}
		System.out.println("Lista de VlanV2:");
		System.out.println(dao.listar(v2));

		System.out.println("Listagem geral Vlan");
		System.out.println(dao.listar(new Vlan()));

		
		
		System.out.println("Teste Tipo");
		Tipo tp = new Tipo();
		tp.setPk(1);
		tp.setTipo("Gerenciavel Gigabit");
		
		if(!dao.existir(tp)){
			Retorno ret = dao.incluir(tp);
			System.out.println("RetornoTipoPorta: "+ret);
		}
		
		System.out.println("teste Switch");
		Switch sw = new Switch();
		sw.setPk(1);
		sw.setNome("Switch 01");
		sw.setLocalizacao("Departamento financeiro");
		sw.setObservacao(" ");
		sw.setTipo(tp);		
		
		if(!dao.existir(sw)){
			Retorno ret = dao.incluir(sw);
			System.out.println("Retorno Switch:"+ret);
		}
		
		Switch sw2 = new Switch();
		sw2.setPk(2);
		sw2.setNome("Siwtch 02");
		sw2.setLocalizacao("Departamento Pessoal");
		sw2.setObservacao(" ");
		sw2.setTipo(tp);
		
		if(!dao.existir(sw2)){
			Retorno ret = dao.incluir(sw2);
			System.out.println("Retorno Switch:"+ret);
			
		}
		
		System.out.println(dao.listar(sw2));
		
		System.out.println("Teste de Porta");
		Porta pt = new Porta();
		pt.setIdporta(1);
		pt.setSwitch(sw);
		pt.setPorta("01");
		
		if(!dao.existir(pt)){
			Retorno ret = dao.incluir(pt);
			System.out.println("Exclui Porta:"+ret);
		}
		
		Porta pt2 = new Porta();
		pt2.setIdporta(2);
		pt2.setSwitch(sw);
		pt2.setPorta("02");
		
		if(!dao.existir(pt2)){
			Retorno ret = dao.incluir(pt2);
			System.out.println("Retorno Existir Porta:"+ret);
		}
		
		
		System.out.println("Teste Equipamento");
		Equipamento eq = new Equipamento();
		eq.setPk(1);
		eq.setPorta(pt);
		eq.setNome("Switch 01");
		eq.setDescricao("Switch do deparamento de Administração");
		eq.setObservacao(" ");
		System.out.println("Equipamento:"+eq);
		
		if(!dao.existir(eq)){
			Retorno ret = dao.incluir(eq);
			System.out.println("RetornoEquipamento:"+ret);
		}
		
		System.out.println("teste portaVlan");
		PortaVlan pv = new PortaVlan();
		pv.setIdPortaVlan(1);
		pv.setPorta(pt);
		pv.setVlan(v);
		
		if(!dao.existir(pv)){
			Retorno ret = dao.incluir(pv);
			System.out.println("Retorno incluir portaVlan:"+ret);
		}

	}
}
