package model;

import anotacoes.Campo;
import modelo.Tabela;

public class Materia extends Tabela<Integer>{
	
	@Campo(isPk=true,nome="id",nomeTela="ID")
	private Integer id;
	
	@Campo(nome="materia", nomeTela="Nome", obrigatorio=true)
	private String materia;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}
	
	
}
