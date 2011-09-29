package partida.tablero.casillas.especiales;

import java.util.HashSet;
import java.util.Set;

import partida.jugador.Jugador;
import utiles.Dado;
import utiles.Vista;

/**
 * Clase para la gestión de la casilla de tipo Cárcel
 * @author Manuel J. Canga Muñoz
 */
public class Carcel extends Especial {
	
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -5587904753310555995L;
	//Conjunto de jugadores atrapados en la cárcel a la espera de sacar un 5
	protected Set<Jugador> atrapados;

	/** Constructor de la clase */
	public Carcel()  {
		super();
		label = "Cárcel";
		atrapados = new HashSet<Jugador>();

	}
	
	
	/** 
	 * Al jugador le toca salir(o intentar salir) de la cárcel este turno
	 * @jugador que intentará salir
	 */
	public void sale(Jugador jugador) {
		//Si el jugador no está ya atrapado en la cárcel, sale normal
		if(!atrapados.contains(jugador)) {
			jugadores.remove(jugador);
			jugador.setPosicion(null);
			return;
		}

		//Mostramos al jugador el mensaje pertineente
		Vista.clear();
		Vista.print("\nAndas metido aún en chirona.");
		Vista.print("\nRecuerda, que si quieres salir el siguiente turno de aquí");
		Vista.print("\ntendrás que sacar un cinco con el dado.");
		
		//Hacemos que el jugador tire el dado para ver si sale o no
		Vista.esperarEnter("\nPulsa enter para probar suerte con el dado...");
		if ( 5 == Dado.tirar(jugador)  ) { //Si sale un 5 sale
			atrapados.remove(jugador); //y quitamos al jugador de jugadores atrapados
			Vista.print("\nEnhorabuena, sales libre el próximo turno");
			Vista.print("\nya no te pudrirás en la cárcel.");
		}else { //Sino saca un 5 le tocará seguir estando en la cárcel
			Vista.print("\nPor ahora, sigues en la cárcel.");
			Vista.print("Yo que tú me iría buscando un buen abogado");
		}
		Vista.esperarEnter();
	}
	
	/**
	 * jugador ha caido en la carcel
	 */
	public void cae(Jugador jugador) {
		//Añadimos el jugador a jugadores en esta casilla
		jugadores.add(jugador);
		//Marcamos la posicion actual del jugador es esta casilla
		jugador.setPosicion(this);
		//Guardamos al jugador en jugadores atrapados
		atrapados.add(jugador);
		//Le decimos al jugador su destino fatal.
		Vista.print("\nSeguirás aquí hasta que, en un turno, saques un cinco con el dado.");
		Vista.print("\nOhhhhh.Más suerte para la próxima vez, amigo.");
		
		Vista.esperarEnter();
	}
}
