package partida.jugador;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

import partida.tablero.Tablero;
import partida.tablero.casillas.Casilla;
import partida.tablero.casillas.especiales.Carcel;
import partida.tablero.casillas.noespeciales.NoEspecial;
import utiles.Config;
import utiles.Dado;
import utiles.Vista;

/**
 * 
 * Clase que es TDA de Jugador del juego Monopoly. 
 * @implements serializable: Para poder guardar el estado de un jugador al guardar una partida
 * @implements Comparable: Para poder comparar jugadores según su nombre para la colocación de los turnos  
 * @author Manuel J. Canga Muñoz
 *
 */

public class Jugador implements Comparable<Object>, Serializable{

	//Nombre del jugador
	private String nombre;
	//Color utilizado por el jugador en el juego
	private Color color;
	//Casilla que se encuentra
	private Casilla posicion;
	//Total de casillas de las que es dueño el jugador
	//Se usan set porque no es indiferente la posición ( y no queremos duplicados)
	private Set<Casilla> casillas;
	//Dinero en euro que dispone.
	private int dinero;
	//IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -317491268845939959L;
	
	
	/**
	 * Constructor de la clase, inicializa sus valores
	 * a partir de la GUI
	 */
	public Jugador() {
		//Asigna(y dar a escoger) el color a un jugaodor
		nuevoColor();
		// Asigna(y dar a escoger) el nombre a un jugador
		nuevoNombre();
		// Al principio no estamos en ninguna casilla
		posicion = null;
		//Al principio no somos dueños de ninguna casilla
		casillas = new HashSet<Casilla>();
		//Cogemos el dinero inicial de la configuración del juego
		dinero = Config.getInt("jugador_dinero_incial");
	}

	/** 
	 * Eliminamos el jugador ( seguramente porque perdió todo su dinero 
	 */
	public void eliminar() {
		// Devolvemos el color que habiamos escogido
		if(null != color) {
			Color.add(color.get());
		}
		
		// Las casillas que compro el jugador vuelven a estar libre 
		if( !casillas.isEmpty() ) {
	        Iterator<Casilla> it = casillas.iterator();

	        while(it.hasNext())
	        {
	          NoEspecial tcasilla = (NoEspecial)it.next();
	          tcasilla.setDueno(null);
	        }

		}
			
	}
	
	/**
	 * Devuelve información de si el jugador está o no en la cárcel
	 * @return boolean: true si está | false sino está
	 */
	public boolean enCarcel() {

		//Si todavía no ha empezado la partida o la casilla actual
		//No es de tipo cárcel entonces es que no está en una carcel.
		if(null == posicion || !(posicion instanceof Carcel) )
			return false;
	
		return true;
	}
	
	/** 
	 * Jugamos un nuevo turno del jugador
	 * @throws ExNoDinero 
	 */
	public void turno(Tablero tablero) throws ExNoDinero {
		//Creamos un alias de la posicion actual
		Casilla casilla_inicio = posicion;
		
		//Intentamos salir de la casilla actual
		casilla_inicio.sale(this);
		
		//Si no estamos ya en ninguna casilla es que hemos 
		//salido satisfactoriamente
		if(null != posicion) {
			return ;
		}
		
		//Tiramos el dado para mover el jugador a su casilla nueva
		byte numero = Dado.tirar(this);
		//averiguamo la casilla final del jugador tras su movimiento
		Casilla casilla_destino = tablero.moverFicha(casilla_inicio, numero);
		//Informamos al jugaor de la nueva casilla en la que se movió o cayó
		Vista.print("\nCaiste en la casilla número "+casilla_destino.getPosicion()+":"+casilla_destino.getLabel());
		//movemos al jugador hasta su casilla destino
		casilla_destino.cae(this);
	}
	
	/**
	 * El jugador compró una casilla y se la añadimos a sus posesiones
	 * @param casilla : casilla que se va a añadir
	 * @return boolean indica si la operación se realizó con éxito o no
	 */
	public boolean addCasilla(Casilla casilla) {
		return this.casillas.add(casilla);
	}
	
	/**
	 * Borramos una casilla de las posesiones del jugador
	 * @param casilla
	 * @return boolean indica si la operación se realizó con exito o no.
	 */
	public boolean delCasilla(Casilla casilla) {
		return this.casillas.remove(casilla);
	}

	/** ************************** Getter/Setter ********************************** */
	public String getNombre() { return nombre; }
	public Casilla getPosicion() { return posicion; }
	public int getDinero() { return dinero; }
	//El número de jugadores se establece según el número de jugadores que haya
	public static int MAX_JUGADORES() { return Color.getColores().length; }
	public Casilla setPosicion(Casilla casilla) { return posicion = casilla; }
	public int setDinero(int dinero) throws ExNoDinero { 
		this.dinero = dinero;
		//Si no le queda dinero, lanzamos una excepción
		if(this.dinero <= 0) {
			throw new ExNoDinero("Jugador "+getNombre()+" sin dinero");
		}
		
		return this.dinero;
	}
	
	/** ************************** METODOS ESPECIALES ********************************** */
	//Para las comprobaciones de que un jugador sea diferente a otro ( por el nombre )
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Jugador otro = (Jugador) obj;
		if (nombre == null) {
			if (otro.nombre != null)
				return false;
		} else if (!nombre.equals(otro.nombre))
			return false;
		return true;
	}

	/**
	 * Nos permite comparar jugadores con respecto al nombre(para ordenarlos, por ejemplo).
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public int compareTo(Object o){
		  	if (!(o instanceof Jugador)) {
		  		throw new IllegalArgumentException("Se ha intentando comparar un jugador con otra elemento distinto");
		  	}
		  	
		    Jugador otroJugador = (Jugador) o;
		    //le pasamos la tarea a string
		    return nombre.compareTo(otroJugador.getNombre());
	}

	/**
	 * Para visualizar la información del jugador de una manera fácil y rápida
	 */
	public String toString() {
		///Toda esta parafernalia es para que aparezca las comas en los miles
		Locale local = new Locale("ES", "ES");
		NumberFormat f = NumberFormat.getNumberInstance(local);
		String string = new String();
		//Mostramos informacion del nombre
		string += String.format("%-25s", nombre);
		//Mostramos la información del color
		string += String.format(";%-10s", color );
		//Mostramos la información del dinero restante del jugador
		string += String.format(";%s", f.format(dinero)+" euros");
		
		return string;
	}
	
	/** ************************** METODOS INICIALIZACION ********************************** */
	
	/** 
	 * Pedimos un nuevo nombre como parte de la inicialización del objeto
	 */
	public void nuevoNombre() {
		
		Vista.clear();
		//Tomamos la longitud minima y maxima que puede tener el nombre del jugador
		int tamano_min_nombre = Config.getInt("jugador_tamano_min_nombre");
		int tamano_max_nombre = Config.getInt("jugador_tamano_max_nombre");
		
		do {
			//Obtenemos el nombre del jugador
			nombre = Vista.input("\n\nPor favor, jugador "+color+", escriba su nombre:").trim();

			//Validamos que el nombre tenga número de carácteres válidos
			if(nombre.length()<= tamano_min_nombre) {
				Vista.print("\nNombre demasiado corto(min "+tamano_min_nombre+" letras).");
			}else	if(nombre.length() > tamano_max_nombre) {
				Vista.print("\nNombre demasiado largo(max "+tamano_max_nombre+" letras).");
			}
			
		//El bucle seguirá y seguirá hasta que se determine un nombre correcto
		}while(nombre.length() < tamano_min_nombre || nombre.length() > tamano_max_nombre);
	}
	
	/** 
	 * Inicializamos el color del jugador
	 */
	public void nuevoColor() {
		//Devolvemos el color que habiamos escogido
		if(null != color) {
			Color.add(color.get());
		}
		
		color = new Color();
	}
}
