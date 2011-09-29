package partida.tablero.casillas.noespeciales.servpublicos;

/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Agua extends ServPublico{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -5416735924447975303L;

	//Constructor por defecto
	public Agua() {
		super();
		label="Compañia del agua";
	}
}
