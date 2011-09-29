package utiles;

import java.util.Properties;


/**
 * Clase que gestiona opciones/parámetros de configuración para que así sea más fácil
 * realizar modificaciones en el juego sin saber programación.
 * @author Manuel J. Canga Muñoz
 *
 */
public class Config {
	//Guarda todas las variables de configuracion
	static private Properties config;
	
	//Inicializa la clase config
	static { init(); }
	
	//Obtenemos las variables de configuración del juego
	private static void  init() {		
		try{
			config = new Properties();
			config.load(Config.class.getClassLoader().getResourceAsStream("Monopoly.conf"));

		}catch(Exception e){
			Vista.error("Problema al cargar el archivo de configuración del juego:");
		}
	}

	//Obtiene un valor en formato array de unas de las variables de confiuración(con identificador key)
	public static String[] getArray(String key) {		
		return config.getProperty(key).trim().split(",");
	}
	
	//Obtiene un valor de una de las variables de configuración(con identificador key)
	public static String get(String key) {
		return config.getProperty(key).trim();
	}
	
	//Obtiene un valor en formato int de una de las variables de configuración(con identificador key)
	public static int getInt(String key) {
		return Integer.parseInt(Config.get(key));
	}
}
