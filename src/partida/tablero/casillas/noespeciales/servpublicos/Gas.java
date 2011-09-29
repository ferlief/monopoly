package partida.tablero.casillas.noespeciales.servpublicos;


/**
*
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
public class Gas  extends ServPublico{
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -561330544271429979L;

	//Constructor por defecto
	public Gas() {
		super();
		label="Compañia del Gas";
	}
}
