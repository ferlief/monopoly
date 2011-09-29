package partida.tablero.casillas.noespeciales.servpublicos;

import partida.tablero.casillas.noespeciales.NoEspecial;
import utiles.Bombo;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class ServPublico extends NoEspecial{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 5732296322702368577L;

	//Constructor por defecto
	public ServPublico() {
		super();
		label="Servicio público";
		//Obtenemos el precio de la casilla
		precio = Bombo.getNumero(100, 500);
	}
}
