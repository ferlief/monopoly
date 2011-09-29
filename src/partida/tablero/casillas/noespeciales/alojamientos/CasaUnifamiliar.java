package partida.tablero.casillas.noespeciales.alojamientos;

import utiles.Bombo;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class CasaUnifamiliar extends Alojamiento {
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 7714880435140994596L;

	//Constructor por defecto de Bombo, por defecto 100 "bolas"
	public CasaUnifamiliar() {
		super();
		label = "Casa Unifamiliar";
		//Obtenemos el precio de la casilla
		precio = Bombo.getNumero(500, 1000);
	}
}
