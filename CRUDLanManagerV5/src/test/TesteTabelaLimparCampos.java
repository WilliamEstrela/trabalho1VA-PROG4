package test;

import model.Tipo;

public class TesteTabelaLimparCampos {
	public void teste() {
		Tipo tp = new Tipo();
		tp.setPk(1);
		tp.setTipo("Teste");
		System.out.println(tp);
		tp.limparColunas();
		System.out.println(tp);
	}
	public static void main(String[] args) {
		TesteTabelaLimparCampos ttlc = new TesteTabelaLimparCampos();
		ttlc.teste();
	}
}
