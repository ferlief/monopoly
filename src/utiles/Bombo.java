package utiles;

import java.util.ArrayList;

/**
 * Clase que gestiona un conjunto de números como si fuera un bombo de lotería de navidad.
 * Primero se meten todos los números que queremos como si fueran las bolas
 * y luego vamos sacando esos números al azar.
 * Esto sirve para obtener un número de posición de casilla al azar
 * y así ir asignando las distintas casillas al tablero en esa posición devuelta.
 * @author Manuel J. Canga Muñoz 
 *
 */
public class Bombo {
	//Contiene todos los números que luego se irán sacando al azar
	private ArrayList<Integer> numeros;
	
	//Constructor por defecto de Bombo, por defecto 100 "bolas"
	public Bombo() {
		this(100);
	}
	
	/**
	 * Constructor de bombo
	 * @param max pasamos el número máximo de "bolas" que habrá
	 */
	public Bombo(int max) {		
		numeros = new ArrayList<Integer>(max);
		
		//Vamos añadiendo una a una todas las bolas.
		for(int i=0; i<=max; i++) {
			numeros.add(i, i);
		}
				
	}

	/**
	 * Sacamos una bola del bombo.
	 * @return int, devolvemos el número que contenía la bola.
	 */
	public int getNumero() {
		int pos = getNumero(0, numeros.size());
		return getNumero(pos);
	}
	
	
	/**
	 * Sacamos una bola especifica del bombo
	 * @param pos, posicion de la bola que se sacará
	 * @return número que tiene esa bola
	 */
	public int getNumero(int pos) {
		return numeros.remove(pos);
	}
	

	/**
	 * Sacamos un número al azar de un bombo entre "desde" hasta "hasta". 
	 * Esto no afecta al bombo de un objeto.
	 * @return
	 */
	static public int getNumero(int desde, int hasta) {
		return (int)(Math.random()*hasta)+desde;
	}
	
	
	/**
	 * Comprueba si el bombo tiene bolas o no
	 * @return boolean: false si ya no tiene bolas y esta vacío, true en caso contrario.
	 */
	public boolean vacio() {
		if(numeros.size() > 0)
			return false;
		else
			return true;
	}
	
	//Convertimos el bombo a String(por ejemplo, por si queremos depurar)
	public String toString() {
		return numeros.toString();
	}

}
