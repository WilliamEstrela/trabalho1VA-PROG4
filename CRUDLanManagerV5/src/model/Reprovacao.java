package model;

import anotacoes.Campo;
import modelo.Tabela;

public class Reprovacao extends Tabela<Integer> {

	@Campo(isPk=true,nome="id",nomeTela="ID")
	private Integer id;
	
	@Campo(nome="nome", nomeTela="Nome", obrigatorio=true)
	private String nome;
	
	@Campo(nome="matricula", nomeTela="Matricula", obrigatorio=true)
	private String matricula;
	
	@Campo(nome="cpf", nomeTela="Descricao")
	private String cpf;
	
	@Campo(nome="materia", nomeTela="Materia")
	private Materia materia;
	
	@Campo(nome="professor", nomeTela="Professor")
	private String professor;

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Materia getMateria() {
		return materia;
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	
}
