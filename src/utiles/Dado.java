package utiles;

import partida.jugador.Jugador;


/**
 * Clase que gestiona una tirada de dados por parte de un jugador
 * @author Manuel J. Canga Muñoz
 *
 */
public class Dado {
	/**
	 * El jugador, jugador, tira el dado
	 * @return byte, retorna el número sacado en el dado
	 */
	static public byte tirar(Jugador jugador)  {
		//obtenemos el nombre del jugador
		String dueno = jugador.getNombre();
		//Obtenemos un número al azar entre 1 y 6
		byte numero = (byte) Bombo.getNumero(1,6);
		//Limpiamos la pantalla
		Vista.clear();
		//MOstramos al jugador el número que ha sacado
		Vista.print("\n"+dueno+", has sacado un "+numero+".");
		///Devolvemos el número
		return numero;
	}

}
