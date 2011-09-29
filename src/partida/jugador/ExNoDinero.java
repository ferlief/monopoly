package partida.jugador;

/**
 *  Excepción  cuando haya un jugador sin dinero
 *  @author Manuel J. Canga Muñoz 
 */
public class ExNoDinero extends Exception {

	private static final long serialVersionUID = -1014226844013851122L;

	public  ExNoDinero(String msg) {
		super(msg);
	}
}
