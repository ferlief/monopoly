package partida.tablero.casillas.especiales;

import partida.jugador.ExNoDinero;
import partida.jugador.Jugador;
import utiles.Dado;
import utiles.Vista;

/**
 * Clase para la gestión de la casilla de tipo Loteria
 * @author Manuel J. Canga Muñoz
 */
public class Loteria extends Especial {

	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -5548084211022017354L;

	/** Constructor de la clase */
	public Loteria()  {
		super();
		label = "Lotería";
	}
	
	/**
	 * jugador ha caido en la casilla, hay que procesarlo. 
	 */
	public void cae(Jugador jugador) throws ExNoDinero {
		//Lo añadimos al conjunto de jugaodres situados en la casilla
		jugadores.add(jugador);
		//Marcamos la casilla actual del jugador a esta.
		jugador.setPosicion(this);

		//Le mostramos la información sobre esta casilla
		Vista.print("\nEs el momento de probar suerte con tus dados...");
		Vista.print("\nSi sacas dos seis tu dinero se verá incrementado en un 10%.");
		Vista.print("\nEl coste por jugar son 100 euros");
		
		//Instamos al jugador a tirar el dado 
		Vista.esperarEnter("\nPulsa enter para tirar tu primer dado...");
		if(6 == Dado.tirar(jugador)) { //Si al tirar el primer dado saca un 6, continua
			Vista.print("Ya estás más cerca de ganar el euromillón.");
			Vista.print("Recuerda que sólo te falta sacar otro seis.");
			Vista.esperarEnter("\nPulsa enter para tirar el segundo dado...");
			if(6 == Dado.tirar(jugador) ){ //Si saca dos seises ha conseguido el premio
				Vista.print("Has conseguido el premio. Ahora eres un 10% más rico que antes.");
				Vista.print("Menos 100 euros por el boleto del juego");
				Vista.esperarEnter();
				//Calculamos el premio(10% del total de dinero que tiene)
				int premio = Math.round(jugador.getDinero()/10);
				//Se lo asignamos(quitandole 100 euros por caer en la casilla )
				jugador.setDinero(jugador.getDinero() + premio - 100);
				return;
			}
		}
		
		//Informamos al jugador de la mala suerte que tiene
		Vista.print("\nOhhhhh, no conseguiste el premio y perdiste 100 euros en el intento.");
		Vista.print("Más suerte para la próxima vez :(");
		//Le quitamos 100 euros por caer en la casilla
		jugador.setDinero(jugador.getDinero() - 100);
		Vista.esperarEnter();
		return;
	}
}
