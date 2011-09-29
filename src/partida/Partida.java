package partida;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import partida.jugador.ExHayGanador;
import partida.jugador.ExNoDinero;
import partida.jugador.Jugador;
import partida.tablero.Tablero;



import utiles.Menu;
import utiles.Vista;

/**
 * Clase que gestiona todo lo relacionado con una partida de Monopoly
 * @implements serializable: Para poder guardar el estado de una partida y luego continuarla
 * @author Manuel J. Canga Muñoz
 *
 */
public class Partida implements Serializable{
 //Tablero de juego, no es más que un sucesión de casillas de destintos tipos
 private Tablero tablero;
 //Lista de jugadores(se usa lista para mantener el orden de los jugadores )
 private ArrayList<Jugador> jugadores;
 //Número del 0 al num_jugadores-1 que establece el jugador que tiene el turno
 private int turno;
 //IdSerial para evitar inconsistencias entre guardado y objeto
 private static final long serialVersionUID = 741852419646180508L;
 
/**
 * Constructor de partida, como su nombre indica construye una partida lista
 * para ser jugada. 
 * @throws ExNoDinero, si es que hubo esta excepción nada más empezar es que hubo un error grave
 */
 public Partida() throws ExNoDinero {
	 //el turno empieza en el jugador 0(del arraylist de jugadores)
	 turno = 0;
	 //Construimos/inicializamos los jugadores de la partida
	 crearJugadores();
	 //Construimos/inicalizamos el tablero de partida 
	 tablero = new Tablero();
	 
	 //Ponemos a todos los jugadores en la casilla de salida
	 for(int i=0; i<jugadores.size(); i++) {
		 tablero.getCasilla(0).cae(jugadores.get(i));
	 }
 }
 
 /**
  * Constructor de partida a partir del número de un tablero ya disponible y de un número de jugadores
  * @param tablero
  * @param jugadores
  */
 public Partida(Tablero tablero, ArrayList<Jugador> jugadores) {
	 turno = 0;
	 this.tablero = tablero;
	 this.jugadores = jugadores;
 }
 
 /**
  * Empezamos a jugar la partida que tengamos lista
 * @throws ExHayGanador 
  */
 public void jugar() throws ExHayGanador {
	menuJuego();
 }

 
 /** 
  * Mostramos el menú de juego, o lo que es loo mismo
  * desarrollamos la partida
 * @throws ExHayGanador 
  */
 private void menuJuego() throws ExHayGanador {
	//Por defecto no hay opción elegida 
	int opcion = 0;
	
	//La partida seguirá en principio hasta
	//que no se le de a salir en el menú
	while(opcion != 4) {

		//Cargamos el título y las opciones del menú de juego
		Menu menu_juego = new Menu(getEstadoJugadores(), 
				"Tirar dado "+jugadores.get(turno).getNombre(), 
				"Ver tablero", 
				"Guardar juego",
				"Salir");
		
		///Mostramos el menú y esperamos la elección
		opcion = menu_juego.ver();
		Jugador jugador_turno = jugadores.get(turno);
		
		//Según la opción elegida
		switch(opcion) {
			/* Tirar dado */
			case 1:
				try {
					jugador_turno.turno(tablero);
					nextTurno(); //Pasamos al siguiente turno
				}catch(ExNoDinero e) {
					//eliminamos al jugador eliminado de la lista de jugadores
					jugadores.remove(turno);
					//devolvemos las cosas del jugador
					jugador_turno.eliminar();
					//retrasamos un turno por el jugador que acaba de ser eliminado
					turno--;
					//Pasamos al siguiente turno 
					Vista.print("\nEl jugador "+jugador_turno.getNombre()+" se quedó sin dinero.");
					Vista.print("\nPor tanto será expulsado de la partida y sus pertenencias irá a la banca.");
					Vista.esperarEnter();
					nextTurno(); //pasamos al siguiente turno
				}
				break;
			/* Ver tablero */
			case 2:
				//Vemos el estado de los jugadores y del tablero
				Vista.alert("Jugadores:"+getEstadoJugadores()+"\n\nl" +
						"Tablero:"+tablero); break;
			/* Guardamos una partida */
			case 3:
				Archivo guardada = new Archivo('w');
				guardada.save(this);
				break;
				
			case 4:
			default:
				/* No hacemos nada, pasamos al menú principal del juego */
		}
	}
	
 }
 
 /** Pasamos el turno al siguiente jugador que le toque */
 private int nextTurno() throws ExHayGanador {
	 //Comprobamos si ya ha terminado la partida o no (si queda 1 jugador nada más, este es el campeón
	 if(1 == jugadores.size() ) {
		 ///Lanzamos la excexpión de que ya hay un ganador.
		 throw new ExHayGanador("El jugador "+jugadores.get(turno).getNombre()+" ha ganado la partida\n");
	 }
	 //incrementamos para pasar de turno
	 turno++;
	 //Como el turno es rotario después del último pasa al primero
	 if(turno> (jugadores.size() -1) ) //recordar que el turno empieza en 0 y size en 1(de ahí el -1)
		 turno = 0;

	 return turno;
 }
 
 /** 
  * Devolvemos un string con el estado de la partida(o lo que es lo mismo, el estado de todos los jugadores )
  */
 private String getEstadoJugadores() {
	String estado_jugadores = new String();
	
	//Metemos en el string la cabecera de nombre de jugador
	estado_jugadores += String.format("\n%-25s",  "Nombre");
	//Metemos en el string la cabecera de color de jugador
	estado_jugadores += String.format(";%-10s", "Color" );
	//Metemos en el string la cabecera de dinero de jugador
	estado_jugadores += String.format(";%s", 	"Dinero ");
	//Metemos también una linea para que quede más bonito
	estado_jugadores += "\n--------------------------------------------------------------------";
	
	//Metemos uno a uno la información de todos los jugadores
	for(int i=0; i<jugadores.size(); i++) {
		estado_jugadores += "\n"+jugadores.get(i);
	}
	
	return estado_jugadores;
 }
 
 /**
  * Pedimos el número de jugadores que participaran en la partida
  * @return num_jugadores
  */
 private int selectNumjugadores() {
	 int num_jugadores = -1;
	 int max_jugadores = Jugador.MAX_JUGADORES();

	 //Seguir pidiendo el número de jugadores hasta que sesa una cantidad válida [2..NUM_MAX]
	 while(num_jugadores<2 || num_jugadores>max_jugadores) {
		Vista.clear();
		if(num_jugadores != -1) { //Sino se seleciono los jugadores permitidos, mostramos error y volvemos preguntar
			Vista.print("\nCantidad de jugadores incorrécta");
		}

		try {
			//preguntarmos cuantos jugadores
			String total = Vista.input("\nPor favor, indique el número de jugadores(2-"+max_jugadores+"):");
			//Validamos el total devuelto pero con cuidado por si no es un número
			num_jugadores = Integer.parseInt(total);
		}catch(NumberFormatException e){
			num_jugadores = 0;
		}
	}
	
	 return num_jugadores;
	 
 }
 
 /**
  * Realizamos la creación de los jugadores que formaran parte de la partida
  */
 private void crearJugadores() {
	 //Pedimos el número de jugadores que participaran
	int num_jugadores = selectNumjugadores();
	 //inicializamos jugadores;
	 jugadores = new ArrayList<Jugador>(num_jugadores);
	 //Jugador temporal para comprobaciones
	 Jugador jugador;
	 //Por cada uno de los jugadores vamos pidiendo todos sus datos relativos
	 for(int i=0; i<num_jugadores; i++) {
		Vista.alert("Ahora se procederá a la creación del jugador "+(i+1));
		jugador = new Jugador();
		
		//Comprobamos si ya existe el usuario
		 while(jugadores.contains(jugador)) {
			 Vista.alert("El nombre elegido para su jugador ya existe. Especifique otro.");
			 jugador.nuevoNombre();
		 }
		 
		 //metemos al jugador en la lista de jugadores de la partida
		 jugadores.add(jugadores.size(),jugador);
	 }
	 
	 //ordenamos los jugadores por sus nombres
	 Collections.sort(jugadores);
 }
 
}
