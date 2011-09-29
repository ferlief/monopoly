package partida.tablero.casillas.noespeciales.servprivados;
import partida.tablero.casillas.noespeciales.NoEspecial;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
abstract public class ServPrivado extends NoEspecial{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -2816014440780824806L;

	public ServPrivado() {
		super();
		label="Servicio Privado";
	}
}
