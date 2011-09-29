package partida.tablero.casillas.noespeciales.servprivados;

import utiles.Bombo;

/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Hotel  extends ServPrivado {
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 1942072630303324290L;

	//Constructor por defecto
	public Hotel() {
		super();
		label="Hotel";
		//Obtenemos el precio de la casilla
		precio = Bombo.getNumero(5000, 10000);
	}
}
