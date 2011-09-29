import partida.Archivo;
import partida.Partida;
import partida.jugador.ExHayGanador;
import partida.jugador.ExNoDinero;
import utiles.Menu;
import utiles.Vista;


/**
 * Juego de Monopoly para Asignatura de Leng de Programación de UNED
 * Esta clase se encarga de empezar la partida y gestionar el menú principal del juego
 * @author Manuel J. Canga Muñoz
 *
 */
public class Monopoly {

	/**
	 * Método por donde empieza el juego
	 * Se encarga de llamar al menú principal y
	 * carga la partida resultante del menú principal
	 * @param args
	 */
	public static void main(String[] args){

		try {

			//Instanciamos la clase Monopoly
			Monopoly juego = new Monopoly();
			//Llamamos al menú principal del juego esperando
			//la partida que se jugará( bien una nueva o una ya guardada )
			Partida partida = juego.menuPrincipal();
			//Jugamos la partida elegida
			partida.jugar();
		}catch( ExHayGanador e) {//Un jugador ha conseguido ganar la partida
			Vista.print(e.getMessage()+"\nEnhorabuena por la buena partida jugada.\n");
		}catch(Exception e) {	//Si hubiera algún error, lo notificamos como es debido.
			Vista.error("\n Un error inesperador ocurrió en el juego del Monopoly y se cerrará.\n");
		}
	}
	
	/**
	 * Mostramos el menu del juego principal
	 * @return partida, devolvemos una partida jugable
	 * @throws ExNoDinero, si hubo un error de este tipo es que algo grave ha pasado(pues se debería
	 * de haber cazado ya esta excepción
	 */
	public Partida menuPrincipal() throws ExNoDinero {
		Partida partida = null;
		do { //El menú seguirá mostrandose hasta que se escoja partida o se quiera acabar el juego
			
			//Cargamos el menú y las opciones del juego
			Menu menu_principal = new Menu("Monopoly: Práctica de Lenguajes de Programación  - Junio 2011", 
										   "Empezar nueva partida", 
										   "Cargar partida anterior", 
										   "Salir");
			int opcion = menu_principal.ver(); //Mostramos el menú y esperamos una elección
	
			//Procesamos la opción escogida
			switch(opcion) {
				case 1: 
					//Empezamos una nueva partida
					partida = new Partida();
					break;
				case 2: 
					//Continuamos una partida anteriormente guardada
					Archivo guardada = new Archivo('r'); 
					partida = guardada.load();  //cargamos la partida desde archivo
					break;
				case 3: 
					//Se selecionó acabar así que nos despedimos
					Vista.print("\nGracias por jugar al Monopoly. Hasta pronto");
					System.exit(0); 
			}
		}while(partida == null);
		
		//Devolvemos partida
		return partida;
	}

}
