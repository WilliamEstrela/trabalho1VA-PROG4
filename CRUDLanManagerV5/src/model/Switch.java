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

public class Switch extends Tabela<Integer> {

	@Campo(isPk=true, nome="idswitch", nomeTela="ID")
	private Integer idswitch;
	
	@Campo(nome="nome", nomeTela="Nome", obrigatorio=true)
	private String 	nome;
	
	@Campo(nome="localizacao",nomeTela="Localiza��o")
	private String 	localizacao;
	
	@Campo(nome="observacao",nomeTela="Obeserva��o")
	private String 	observacao;
	
	@Campo(nome="tipo_idtipo", nomeTela="Tipo", obrigatorio=true)
	private Tipo	tipo;
	
	@Campo(nome="vlan", nomeTela="VLAN")
	private Vlan	vlan;
	
/*	@Override
	public String getTabelaPKNome() {
		return "idswitch";
	}*/

	public Integer getIdswitch() {
		return idswitch;
	}

	public void setIdswitch(Integer idswitch) {
		this.idswitch = idswitch;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Vlan getVlan() {
		return vlan;
	}

	public void setVlan(Vlan vlan) {
		this.vlan = vlan;
	}

	@Override
	public String getTabelaNome() {
		return "switch";
	}

	@Override
	public Tabela<?> getNovoObjeto() {
		return new Switch();
	}
}
