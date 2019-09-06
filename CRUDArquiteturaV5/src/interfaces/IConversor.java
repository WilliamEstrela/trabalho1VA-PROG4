package interfaces;

import java.lang.reflect.Field;

public interface IConversor<TIPO> {
	public TIPO converter(String valor);
	public String converterBdParaVisao(TIPO valor);
	public void setFieldData(Field tipo);
	public Field getFieldData() ;
}
