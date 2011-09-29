package partida.jugador;

/**
 * Excepción para cuando un jugador gana(es decir, sólo queda 1 jugador en juego)
 * @author Manuel J. Canga Muñoz
 *
 */
public class ExHayGanador  extends Exception {

	private static final long serialVersionUID = -8127033456224206131L;

	public  ExHayGanador(String msg) {
		super(msg);
	}
}