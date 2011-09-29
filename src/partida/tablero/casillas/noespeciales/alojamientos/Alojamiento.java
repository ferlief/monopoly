package partida.tablero.casillas.noespeciales.alojamientos;
import partida.tablero.casillas.noespeciales.NoEspecial;

/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Alojamiento extends NoEspecial {
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -6609964428172572669L;

	//Constructor por defecto de Bombo, por defecto 100 "bolas"
	public Alojamiento()  {
		super();
		label="Alojamiento";
	}
}
