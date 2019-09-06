package model;

import java.util.ArrayList;
import java.util.List;

import anotacoes.Campo;
import conversores.IntegerConversor;
import conversores.StringConversor;
import conversores.TabelaConversor;
import interfaces.IConversor;
import modelo.Tabela;
import util.Retorno;

public class Porta extends Tabela<Integer> {

	@Campo(isPk=true, nome="idporta", nomeTela="ID")
	private Integer idporta;
	
	@Campo(nome="switch_idswitch", nomeTela="Switch",obrigatorio=true)
	private Switch switch_;
	
	@Campo(nome="porta", nomeTela="Porta", obrigatorio=true)
	private String porta;
	
/*	public void setPk(Integer pk) {
		this.idporta = pk;
	}
	public Integer getPk() {
		return this.idporta;
	}*/
	public Integer getIdporta() {
		return idporta;
	}

	public void setIdporta(Integer idporta) {
		this.idporta = idporta;
	}

	public Switch getSwitch_() {
		return this.switch_;
	}

	public void setSwitch(Switch switch_) {
		this.switch_ = switch_;
	}

	public String getPorta() {
		return porta;
	}

	public void setPorta(String porta) {
		this.porta = porta;
	}

	
/*	@Override
	public String getTabelaPKNome() {		
		return "idporta";
	}*/


	@Override
	public String getTabelaNome() {
		return "porta";
	}

	/*@Override
	public List<String> getCamposNome() {
		ArrayList<String> listNomes = new ArrayList<>();
		listNomes.add("idporta");
		listNomes.add("switch_idswitch");
		listNomes.add("porta");
		return listNomes ;
	}*/
	/*@Override
	protected Retorno setCamposValor(List<Object> list) {
		Retorno ret = new Retorno(true, null);
		try{
			this.setPk((Integer) list.get(0));
			
			this.setSwitch((Switch) list.get(1));

			this.setPorta((String) list.get(2) );

		}catch(Exception e){
			ret.setSucesso(false);
			ret.addMensagem("Erro ao configura campos, ERROR:"+e.getMessage());
		}
				
		return ret;
	}*/

	
	/*@Override
	public List<Object> getCamposValor() {
		ArrayList<Object> list = new ArrayList<>();

		list.add(this.getPk());
		list.add(this.getSwitch());
		list.add(this.getPorta());

		return list;
	}*/

	@Override
	public Tabela<?> getNovoObjeto() {
		return new Porta();
	}
	
	/*@SuppressWarnings("rawtypes")
	@Override
	public List<IConversor> getCamposConversor() {
		ArrayList<IConversor> listConversor = new ArrayList<>();
		listConversor.add(new IntegerConversor());//pk		
		listConversor.add(new TabelaConversor(new Switch()));//switch como inteiro
		listConversor.add(new StringConversor());//porta (nome da porta
		return listConversor ;
	}*/
	/*@Override
	protected List<String> getCamposObrigatorios() {
		List<String> list = new ArrayList<>();
		list.add("switch_idswitch");
		list.add("porta");
		return list;
	}*/
}

