package partida.tablero.casillas.noespeciales.servpublicos;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Telefono  extends ServPublico{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 5343289474380175338L;

	//Constructor por defecto
	public Telefono() {
		super();
		label="Compañia del telefono";
	}
}
