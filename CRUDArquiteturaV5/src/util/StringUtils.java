package util;

public class StringUtils {
	public static String capitularizar(String texto) {
		return texto.substring(0, 1).toUpperCase()+texto.substring(1);
	}
/*	public static void main(String[] args) {
		System.out.println(StringUtils.capitularizar("testeId"));
	}*/
}
