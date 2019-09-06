package test;

import java.util.HashMap;

import controle.ManterTipo;
import util.Retorno;

public class TesteController {
	public static void main(String[] args) {
		ManterTipo mt = new ManterTipo();
		
		HashMap<String, Object> param = new HashMap<>();

		param.put("idtipo", "4");
		param.put("tipo", "tipo 04");
		mt.setVariaveis(param);
		
		Retorno ret = mt.incluir();
		System.out.println("Retorno: "+ret);
		
		
		param.put("tipo", "tipo04=alterada");
		mt.setVariaveis(param);
		
		Retorno ret2 = mt.alterar();
		System.out.println("Retorno2: "+ret2);

		//mt.delete();
	}
}
