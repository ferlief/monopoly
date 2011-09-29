package partida.tablero.casillas.especiales;

import partida.tablero.casillas.Casilla;

/**
 * Clase para la gestión de la casilla de tipo Salida.
 * Esta es la casilla en la que comienzan todos los jugadores
 * @author Manuel J. Canga Muñoz
 */
public class Salida extends Casilla{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 6012229154268051422L;

	/** Constructor de la clase */
	public Salida() {
		super();
		label="Salida";
	}
	
	/** Devolvemos la información de la casilla */
	public String toString() {
		//Si ya han salido todos los jugadores, entonces esta casilla
		//no tiene sentido devolver nada, porque ya no formará más parte del juego
		if(jugadores.isEmpty()) {
			return "";
		}else{
			return super.toString();
		}
	}
}
