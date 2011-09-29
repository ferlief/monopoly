package partida.tablero.casillas.noespeciales.servprivados;

import utiles.Bombo;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Restaurante  extends ServPrivado{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 525441887954634266L;

	//Constructor por defecto
	public Restaurante() {
		super();
		label="Restaurante";
		//Obtenemos el precio de la casilla
		precio = Bombo.getNumero(500, 1000);
	}
}
