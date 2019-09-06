package conversores;

import java.lang.reflect.Field;

import interfaces.IConversor;

public abstract class AbstractConversor<TIPO> implements IConversor<TIPO> {
	public static final String PACOTE_CONVERSORES="conversores";
	public static final String SUFIXO_CONVERSORES="Conversor";
	private Field tipoAtributo;
	public void setFieldData(Field tipo) {
		this.tipoAtributo = tipo;
	}
	public Field getFieldData() {
		return this.tipoAtributo;
	}

}
