package model;

import java.util.ArrayList;
import java.util.List;

import conversores.IntegerConversor;
import interfaces.IConversor;
import modelo.Tabela;
import util.Retorno;


public class PortaVlan extends Tabela<Integer> {

	private Integer idPortaVlan;
	private Porta porta;
	private Vlan vlan;
	//private Date dataCriacao;

	// sobrescrevendo o metodo para utilizar o valor local(uma alternativa)
	@Override
	public Integer getPk() {
		return this.getIdPortaVlan();
	}

	@Override
	public void setPk(Integer pk) {
		this.setIdPortaVlan(pk);
	}

	public Integer getIdPortaVlan() {
		return idPortaVlan;
	}

	public void setIdPortaVlan(Integer idPortaVlan) {
		this.idPortaVlan = idPortaVlan;
	}

	public Porta getPorta() {
		return porta;
	}

	public void setPorta(Porta porta) {
		this.porta = porta;
	}

	public Vlan getVlan() {
		return vlan;
	}

	public void setVlan(Vlan vlan) {
		this.vlan = vlan;
	}

	
	@Override
	public String getTabelaNome() {
		return "porta_vlan";
	}
	
	@Override
	public String getTabelaPKNome() {		
		return "idporta_vlan";
	}
	
	@Override
	public List<String> getCamposNome(){
		ArrayList<String> listNomes = new ArrayList<>();
		listNomes.add("idporta_vlan");
		listNomes.add("porta_idporta");
		listNomes.add("vlan_vlan");
		return listNomes ;
	}

	@Override
	protected Retorno setCamposValor(List<Object> list) {
		Retorno ret = new Retorno(true, null);
		try{
			this.setPk((Integer) list.get(0));
			
			this.setPorta((Porta)list.get(1));
			//tem que tratar para dados estrangeiros
/*			Porta _porta = new Porta();
			_porta.setPk((Integer)list.get(1));
			_porta = this.getDadosExtrangeiro(_porta);
			this.setPorta(_porta);*/

			this.setVlan((Vlan)list.get(2));
			//tem que tratar para dados estrangeiros
/*			Vlan _vlan = new Vlan();
			_vlan.setPk((Integer)list.get(2));
			_vlan = this.getDadosExtrangeiro(_vlan);
			this.setVlan(_vlan);*/

		}catch(Exception e){
			ret.setSucesso(false);
			ret.addMensagem("Erro ao configura campos, ERROR:"+e.getMessage());
		}
				
		return ret;
	}


	@Override
	public List<Object> getCamposValor() {
		ArrayList<Object> list = new ArrayList<>();

		list.add(this.getPk());
		list.add(this.getPorta());
		list.add(this.getVlan());

		return list;
	}

	@Override
	public Tabela<?> getNovoObjeto() {
		return new PortaVlan();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List<IConversor> getCamposConversor() {
		ArrayList<IConversor> listConversor = new ArrayList<>();
		listConversor.add(new IntegerConversor());//pk		
		listConversor.add(new IntegerConversor());//porta id
		listConversor.add(new IntegerConversor());//vlan id
		return listConversor ;
	}
	@Override
	protected List<String> getCamposObrigatorios() {
		List<String> list = new ArrayList<>();
		list.add("porta_idporta");
		list.add("vlan_vlan");
		return list;
	}
}
