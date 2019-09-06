package conversores;

import anotacoes.BooleanConversorParameteros;
import interfaces.IConversor;

public class BooleanConversor extends AbstractConversor<Boolean> implements IConversor<Boolean> {
	private static String VALOR_BOOLEAN_TRUE = "true";
	@SuppressWarnings("unused")
	private static String VALOR_BOOLEAN_FALSE = "false";
	
	private String valorVerdadeiro;
	private String valorFalso;
/*	public BooleanConversor(String verdadeiro, String falso) {
		this.valorVerdadeiro = verdadeiro;
		this.valorFalso = falso;
	}*/
	public BooleanConversor() {
		this.valorVerdadeiro = "Verdadeiro";
		this.valorFalso = "Falso";
	}
	@Override
	public Boolean converter(String valor) {
		parametrizaConversor();
		
		if(valor == null || valor.equals("")) return null;
		Boolean retorno ;
		try{
			if(valor.equalsIgnoreCase(this.valorVerdadeiro) || valor.equalsIgnoreCase(VALOR_BOOLEAN_TRUE)){
				retorno = new Boolean(true);
			}else{
				retorno = new Boolean(false);
			}
		}catch (Exception e) {
			throw new RuntimeException("Erro conversão:"+e.getMessage());
		}
		return retorno;
	}
	private void parametrizaConversor() {
		BooleanConversorParameteros bcp = 
				this.getFieldData().
				getAnnotation(BooleanConversorParameteros.class);
		if(bcp!=null) {
			this.valorVerdadeiro = bcp.textoVerdadeiro();
			this.valorFalso = bcp.textoFalso();
		}
	}

	@Override
	public String converterBdParaVisao(Boolean valor) {
		parametrizaConversor();
		if(valor==null) {
			return "";
		}else {
			if(valor) {
				return this.valorVerdadeiro;
			}else {
				return this.valorFalso;
			}
		}
	}

}
