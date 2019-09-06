package test;

import modelo.Usuario;
import util.Reflexao;

public class TesteReflexao {
	public static void main(String[] args) {
		Usuario u = new Usuario();
		u.setNome_usuario("Jose");
		//u.setSenha_usuario("Senha");
		System.out.println(Reflexao.getTabelaAtributoPkValor(u));
		System.out.println(u.getPk());
		System.out.println(Reflexao.getTabelaAtributoValor(u, "Nome_usuario"));
		//TODO tratar caso de informar campo que náo existe
		Reflexao.mudarTabelaAtributoValor(u, "Nome_usuario2","Jose2");
		System.out.println(u.getPk());
		u.setPk("Coisa");
		System.out.println(u.getNome_usuario());
	}
}
