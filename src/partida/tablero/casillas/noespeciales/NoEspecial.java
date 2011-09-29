package partida.tablero.casillas.noespeciales;

import java.text.NumberFormat;
import java.util.Locale;

import partida.jugador.ExNoDinero;
import partida.jugador.Jugador;
import partida.tablero.casillas.Casilla;
import utiles.Config;
import utiles.Vista;


/**
* Esta clase gestiona las tareas comunes a las casillas no especiales 
* así como tareas varias relacionadas con estas
* @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
* 
*/
abstract public class NoEspecial extends Casilla{
	private static final long serialVersionUID = -766108981098434818L;
	//Precio de la casilla
	protected int precio = 0;
	//Dueno de la casilla
	protected Jugador dueno = null;
	
	//Constructor por defecto
	protected NoEspecial() {
		super();
	}

	/**
	 * El jugador, jugador, cae en la casilla
	 */
	public void cae(Jugador jugador)  throws ExNoDinero {
		super.cae(jugador);
		//si no tiene dueño le ofrecemos al jugador la posibilidad de comprarla
		if(dueno == null) {
			//Si el jugador tiene dinero, seguimos adelante
			if(precio < jugador.getDinero() ) {
				String respuesta;
				//pregutnamos al jugador si quiere comprarla
				do {
					respuesta = Vista.input("\n¿Te gustaría comprar "+label+" por "+getPrecioFormated()+" (s/n)?");
					
					//si se decide a comprarla...
					if((respuesta.equals("S") || respuesta.equals("s")) ){
						//Le quitamos el dinero de lo que cuesta
						jugador.setDinero(jugador.getDinero() - precio);
						//Le asignamos como dueño
						setDueno(jugador);
						//Le mostramos claramente que casilla ha comprado
						Vista.print("Casilla "+label+" comprada");
						//Añadimos la casilla al listado de casillas del jugador
						jugador.addCasilla(this);
						break; //Saltamos el bucle y ahorramos comprobacionesçççç
					}
				//El bucle seguirá hasta que no se escoga entre s/n
				}while(!respuesta.equals("n") && !respuesta.equals("N"));
			}else {
				//El jugador no tiene dinero para comprar la casilla y se lo decimos
				Vista.print("\nEsta casilla no tiene dueño, pero tampoco tienes dinero para comprarla.");
				Vista.print("\nConsigue "+getPrecioFormated()+" euros para la próxima vez");
			}
		}else if ( dueno != jugador) {
			//Si el jugador no es el dueño, malas noticias para el
			//Avisamos de quien es el dueño
			Vista.print("\nEsta casilla pertenece a "+dueno.getNombre());
			//si el dueño está en la cárcel no tiene porque pagarle el jugador
			if( !jugador.enCarcel()) {
				//le informamos de lo que tiene que pagar el jugador
				Vista.print(".Por ello tienes que pagarle "+getCosteFormated());
					jugador.setDinero(jugador.getDinero() - getCoste());
			}else {
				//Informamos que se libra de pagar al dueño porque el dueño está en la carcel.
				Vista.print(".Pero como está en la cárcel no le tienes que pagar.");
			}
		}else {
			//No hacemos nada la casilla ya es del jugador
			Vista.print("\nEsta casilla es de tu propiedad");
		}
		Vista.esperarEnter();
		
	}
	
	/**
	 * Obtenemos desde el fichero de configuración del juego los distintos tipos
	 * de casillas no especiales
	 */
	static public String[] getTipos() {
		return Config.getArray("casillas_no_especiales_tipos");
	}

	/**
	 * Factoría que devuelve un objeto de la clase especificada de tipo NoEspecial
	 * @param tipo, nombre de la clase que se quiere instanciar
	 * @return objeto de la clase instanciada
	 */
	static public Casilla getCasilla(String tipo) {
		try {
			//Obtenemos el objeto de la clase pedida
			return (Casilla) Class.forName("partida.tablero.casillas.noespeciales."+tipo.trim()).newInstance();
		} catch (Exception e) {
			//Si no es un tipo válido, le mandamos una excepción.
			throw new IllegalArgumentException("Tipo de casilla no especial no encontrado");
		}

	}
	
	
	/** ************************** Getter/Setter ********************************** */

	//Precio de la casilla
	public int getPrecio() { return precio; }
	//Precio de la casila formateado
	public String getPrecioFormated() { 
		Locale local = new Locale("ES", "ES");
		
		NumberFormat f = NumberFormat.getNumberInstance(local);
		return f.format(precio)+" euros";
	}
	//Coste de la casilla
	public int getCoste() { return Math.round(getPrecio()/10); }
	//Coste de la casilla formateado
	public String getCosteFormated() { 
		Locale local = new Locale("ES", "ES");
		
		NumberFormat f = NumberFormat.getNumberInstance(local);		
		return f.format(getCoste())+" euros";
	}
	//Dueño de la casilla
	public String getDueno() { if(dueno != null) return dueno.getNombre(); else return "sin dueño"; }
	//asignamos un nuevo dueño a la casilla 
	public void setDueno(Jugador jugador) { dueno = jugador; }

	/** ************************** METODOS ESPECIALES ********************************** */
	//Devolvemos un string con información sobre la casilla
	public String toString()  { 
		String cadena = new String();
		
		cadena += String.format("\n%3d:", posicion);
		cadena += String.format("%-22s",getLabel()			 ); //Tipo de Casilla
		cadena += String.format(";%-18s",getPrecioFormated());//Precio de compra casilla ( getPrecio() )
		cadena += String.format(";%-18s", getCosteFormated()); //Coste por caer ( getCoste() )
		cadena += String.format(";%-15s",getDueno()); //Dueño ( getDueno() )
		cadena += String.format(";%s",getNombreJugadores()); //Dueño ( getNombreJugadores() )

		return cadena;
	}
}
