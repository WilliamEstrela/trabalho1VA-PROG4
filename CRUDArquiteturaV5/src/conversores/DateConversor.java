package conversores;

import java.text.SimpleDateFormat;
import java.util.Date;

import interfaces.IConversor;

public class DateConversor extends AbstractConversor<Date> implements IConversor<Date> {

	@Override
	public Date converter(String valor) {
		if(valor == null || valor.equals("")) return null;
		Date retorno ;
		try{
			retorno = getDateFormat().parse(valor);
		}catch (Exception e) {
			throw new RuntimeException("Erro conversão:"+e.getMessage());
		}
		return retorno;
	}

	public SimpleDateFormat getDateFormat() {
		return new SimpleDateFormat("dd/MM/yyyy");
	}

	@Override
	public String converterBdParaVisao(Date valor) {
		if(valor==null) {
			return "";
		}else {
			return this.getDateFormat().format(valor);
		}
	}

}
