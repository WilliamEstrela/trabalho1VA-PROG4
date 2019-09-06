package conversores;

import interfaces.IConversor;

public class IntegerConversor extends AbstractConversor<Integer> implements IConversor<Integer> {

	@Override
	public Integer converter(String valor) {
		if(valor == null || valor.equals("")) return null;
		Integer retorno ;
		try{
			retorno = Integer.valueOf(valor);
		}catch (NumberFormatException e) {
			throw new RuntimeException("Erro conversão:"+e.getMessage());
		}
		return retorno;
	}

	@Override
	public String converterBdParaVisao(Integer valor) {
		if(valor==null) {
			return "";
		}else {
			return valor.toString();
		}
	}

}
