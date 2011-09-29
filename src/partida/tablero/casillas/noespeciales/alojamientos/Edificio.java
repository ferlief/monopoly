package partida.tablero.casillas.noespeciales.alojamientos;

import utiles.Bombo;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Edificio extends Alojamiento {
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 8098264964941065595L;

	//Constructor por defecto de Bombo, por defecto 100 "bolas"
	public Edificio() {
		super();
		label = "Edificio";
		//Obtenemos el precio de la casilla
		precio = Bombo.getNumero(5000, 10000);
	}
}
