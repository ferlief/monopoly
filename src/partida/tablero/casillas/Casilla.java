package partida.tablero.casillas;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import partida.jugador.ExNoDinero;
import partida.jugador.Jugador;



/**
 * Clase TDA Casilla de las que heredan todas las demas y sirve de base para formar el tablero
 * @implements serializable: Para poder guardar el estado de la casilla en una serialización
 * @author Manuel J. Canga Muñoz
 */
abstract public class Casilla implements Serializable{

	//Etiqueta de la casilla
	protected String label;
	//Conjunto de jugadores que forman parte de la partida
	protected Set<Jugador> jugadores;
	//Posición que ocupa dentro del tablero
	protected int posicion;
	 //IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = 4289836997653803621L;
	
	/** 
	 * Constructor básico de una casilla, pone posicion a 0
	 */
	public Casilla() { this(0);}
	
	/**
	 * Constructor que se le pasa la posición que ocupa la casilla
	 * @param posicion
	 */
	public Casilla(int posicion) {
		this.posicion = posicion;
		jugadores = new HashSet<Jugador>();
	}

	/**
	 * cada vez que un jugador sale de la casilla hay que hacer tareas 
	 * por ejemplo, quitarlo de la lista de jugadores
	 * @param jugador que va a salir de la casilla
	 */
	public void sale(Jugador jugador) { 
	
		//Si el jugador no estaba en la casilla se lanza una excepción
		if(!jugadores.remove(jugador) ) {
			throw new IllegalArgumentException("Usuario no se encuentra en la casilla");
		}
		 //Al salir el jugador ya no ocupa ninguna posición hasta que cae en otra casilla
		jugador.setPosicion(null);
	}
	
	/**
	 * El jugador cae en la casilla
	 * @param jugador, jugador que ha caido en la casilla
	 * @throws ExNoDinero, Se lanza cuando el jugador al caer a la casilla se queda sin dinero
	 * ej:[especiales]: Lotería, Carcel, [Noespeciales]pagar por impuesto a otro jugador
	 */
	public void cae(Jugador jugador) throws ExNoDinero  { 
		//Añadimos al jugador al listado de jugadores que hay en esta casilla
		jugadores.add(jugador);
		jugador.setPosicion(this); //Ponemos el jugador en la casilla actual
	}

	/** ************************** Getter/Setter ********************************** */
	public void setPosicion(int posicion){ this.posicion = posicion; }
	public String getLabel() { return label; }
	public int getPosicion() { return posicion; }
	
	/** 
	 * Devolvemos  los nombres de todos los jugadores que hay en la casilla
	 */
	public String getNombreJugadores() {
		String nombres = new String();
		
        Iterator<Jugador> it=jugadores.iterator();
        while(it.hasNext())
        {
          Jugador jugador=(Jugador)it.next();
          nombres += jugador.getNombre();
          if(it.hasNext()) nombres += "-";
        }
        
        if(0 == nombres.length()) { nombres += "no hay jugadores"; }
        
        return nombres;
	}

	/** ************************** METODOS ESPECIALES ********************************** */
	/**
	 * Devolvemos información sobre la casilla
	 */
	public String toString() { 
		String cadena = new String();
		
		cadena += String.format("\n%3d:", posicion);
		cadena += String.format("%-22s",getLabel()); //Tipo de Casilla
		cadena += String.format(";%-18s","0 euros"); //Precio de compra casilla ( getPrecio() )
		cadena += String.format(";%-18s","0 euros"); //Coste por caer ( getCoste() )
		cadena += String.format(";%-15s","No se compra"); //Dueño ( getDueno() )
		cadena += String.format(";%s",getNombreJugadores()); //Dueño ( getNombreJugadores() )

		return cadena;
	}
	
}