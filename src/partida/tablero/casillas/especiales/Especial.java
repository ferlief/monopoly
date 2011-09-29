package partida.tablero.casillas.especiales;

import partida.tablero.casillas.Casilla;
import utiles.Config;

/**
 * Clase abstracta que determinará casillas de tipo especiales
 * @author Manuel J. Canga Muñoz
 */
abstract public class Especial extends Casilla{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 8191038948440427279L;

	//Constructor de la clase
	protected Especial() {
		super();
	}
	
	//Devuelve los distintos tipos de casillas especiales que hay
	static public String[] getTipos() {
		//Los tipos se obtienen del fichero de configuración del juego
		return Config.getArray("casillas_especiales_tipos");
	}

	//Factoria para la creación de casillas que tengan un tipo derivado de Especial
	//Así facilita las cosas
	static public Casilla getCasilla(String tipo) {

		try {
			return (Casilla) Class.forName("partida.tablero.casillas.especiales."+tipo.trim()).newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("Tipo de casilla especial no encontrado");
		}

	}
	

}
