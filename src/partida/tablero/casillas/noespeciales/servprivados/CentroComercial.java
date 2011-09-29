package partida.tablero.casillas.noespeciales.servprivados;

import utiles.Bombo;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class CentroComercial extends ServPrivado{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -1749523628467820584L;

	//Constructor por defecto
	public CentroComercial(){
		super();
		label = "Centro Comercial";
		//Obtenemos el precio de la casilla
		precio = Bombo.getNumero(10000, 20000);
	}

}
