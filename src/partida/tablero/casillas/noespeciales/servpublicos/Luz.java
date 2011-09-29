package partida.tablero.casillas.noespeciales.servpublicos;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Luz  extends ServPublico{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 8710428630167836490L;

	//Constructor por defecto
	public Luz() {
		super();
		label="Compañia de la Luz";
	}
}
