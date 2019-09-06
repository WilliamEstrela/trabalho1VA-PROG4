package util;

import java.io.IOException;
import java.util.Properties;

public class Configuracoes {
	public static final String DRIVER_BD = "DRIVER_BD";
	public static final String SENHA_BD = "SENHA_BD";
	public static final String USUARIO_BD = "USUARIO_BD";
	public static final String URLCONEXAO = "URLCONEXAO";
	private String urlConexao="";
	private String usuarioBd="";
	private String senhaBd="";
	private String driveBd="";
	
	private static Configuracoes conf ;
	public static Configuracoes getInstance() {
		if(conf==null) {
			conf = new Configuracoes();
			conf.init();
		}
		return conf;
	}
	public String getUrlConexao() {
		return urlConexao;
	}
	public String getUsuarioBd() {
		return usuarioBd;
	}
	public String getSenhaBd() {
		return senhaBd;
	}
	public String getDriveBd() {
		return driveBd;
	}
	private Configuracoes() {
		
	}
	private void init() {
		Properties prop = new Properties();
		try {
			prop.load( getClass().getClassLoader().getResourceAsStream("config.properties") );
			urlConexao	= prop.getProperty(URLCONEXAO, "jdbc:postgresql://localhost:5433/lan_manager");
			usuarioBd	= prop.getProperty(USUARIO_BD, "postgres");
			senhaBd		= prop.getProperty(SENHA_BD,"");
			driveBd		= prop.getProperty(DRIVER_BD,"org.postgresql.Driver");
			
		} catch (IOException e) {
			e.printStackTrace();
		} //carrega propriedades do arquivo jdbc.properties, 
	}
}
