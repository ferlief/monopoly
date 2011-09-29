package partida.tablero;

import java.io.Serializable;

import partida.tablero.casillas.Casilla;
import partida.tablero.casillas.especiales.Especial;
import partida.tablero.casillas.especiales.Salida;
import partida.tablero.casillas.noespeciales.NoEspecial;
import utiles.Bombo;
import utiles.Config;
import utiles.Vista;

/**
 * Clase TDA Tablero para todo lo relacionado con el tablero en la partida del Monopoly
 * @implements serializable: Para poder guardar el estado del tablero en una serialización
 * @author Manuel J. Canga Muñoz
 *
 */
public class Tablero implements Serializable{

	//Listado de casillas que forman el tablero
	private Casilla[] casillas;
	 //IdSerial para evitar inconsistencias entre guardado y objeto
	private static final long serialVersionUID = -8263399411301773498L;

	/** 
	 * Constructor que se encarga de inicializar el tablero a partir de GUI
	 */
	public Tablero() {
		//¿Tamaño del tablero?
		int tamano = pedirTamano();
		//A partir del tamaño del tablero creamos este
		//El +1 es para añadir la casilla de salida
		casillas = new Casilla[tamano+1];
		
		//Creamos un bombo de números del tamaño selecionado
		Bombo bombo = new Bombo(tamano);
		//Colocamos la casilla de salida
		setSalida(bombo);
		//Colocamos las casillas especiales
		setEspeciales(tamano/10, bombo);
		//Colocamos las casillas no especiales
		setNoEspeciales(bombo);
		
	}
	
	/**
	 * Constructor de tablero a partir de un listado de casillas predefinidos
	 * @param casillas que formaran el tablero
	 */
	public Tablero(Casilla[] casillas) {
		this.casillas = casillas;
	}
	
	/**
	 * Movemos una ficha/jugador por el tablero
	 * @param inicio casilla a partir desde que se va a mover la ficha/jugaodr
	 * @param movimientos  ///Número de casillas que se moverá
	 * @return casilla, retorna la casilla final
	 */
	public Casilla moverFicha(Casilla inicio, byte movimientos) {
		int pos_inicial = inicio.getPosicion();
		int pos_final = pos_inicial + movimientos;
		//Si se ha llegado al final del tablero, toca recomenzar desde casilla 1
		if(pos_final >= casillas.length) {
			pos_final = pos_final - casillas.length + 1 /* la salida no cuenta */;
		}
		
		return casillas[pos_final];
	}


	/* ******************************* INICIALIZACIÓN DE LA CLASE ****************************** */
	/**
	 * Pedimos por GUI el número de casillas que formaran parte del tablero
	 */
	private int pedirTamano() {
		int tamano = 0;
		//Tomamos del archivo de configuración la cantidad mínima que puede tener el tablero
		int tam_min = Config.getInt("tablero_tam_min");
		//Tomamos del archivo de configuración la cantidad máxima que puede tener el tablero
		int tam_max = Config.getInt("tablero_tam_max");

		
		Vista.clear();
		//Mientras que no nos de un valor adecuado de tablero seguimos preguntando [min..max] y divisible por 10
		while(tamano < tam_min || tamano > tam_max  || 0 != (tamano%10) ) {
			try {
				tamano = Integer.parseInt(Vista.input("\n\nPor favor, escoja tamaño del tablero("+tam_min+"-"+tam_max+"):"));
			}catch(NumberFormatException e) {
				//Si nos han querer poner algo que no sea un número se lo advertimos y pedimos de nuevo
				Vista.print("\nEspecifique un tamaño de tablero entendible.");
				continue;
			}
			//Mensajes personalizados según el error haya cometido al introducir los tamaños
			if(tamano < tam_min) 
				Vista.print("\nTamaño del tablero demasiado pequeño(mínimo "+tam_min+").");
			else if(tamano > 40) 
				Vista.print("\nTamaño del tablero demasiado pequeño(máximo "+tam_max+").");
			else if(0 != (tamano%10) ) {
				Vista.print("\nTamaño del tablero tiene que ser disible entre 10.");
			}
		 
		}
		
		return tamano;
	}
	
	

	//Añadimos a la casilla de salida,todos los jugadores
	private void setSalida(Bombo bombo) {
		casillas[0] = new Salida();
		bombo.getNumero(0); //quitamos por tanto, del bombo, la casilla de salida	
	}
	
	/** 
	 * Situamos la casillas especiales
	 * @param num_especiales el número de casillas especiales que se colocaran
	 * @param bombo, bombo con las distintas posiciones disponibles que hay para colocar casillas
	 */
	private void setEspeciales(int num_especiales, Bombo bombo) {

		//Obtenemos todos los tipos de casillas especiales
		String[] casillas_especiales = Especial.getTipos();
		
		//Para cada tipo de casilla especial y mientras el bomto no esté vacío
		//Vamos sacando casillas disponibles del tablero y a esa le asignamos el tipo
		for(int i=0; i<casillas_especiales.length && !bombo.vacio(); i++) {
			int cantidad = num_especiales;
			while(cantidad > 0 && !bombo.vacio()){ 
				int num_casilla = bombo.getNumero();
				casillas[num_casilla] = Especial.getCasilla(casillas_especiales[i]);
				casillas[num_casilla].setPosicion(num_casilla);
				cantidad--;
			}
		}

	}
	
	private void setNoEspeciales(Bombo bombo) {
		String[] casillas_no_especiales = NoEspecial.getTipos();
		for(int i=0; !bombo.vacio(); i++) {
			int num_casilla = bombo.getNumero();
			casillas[num_casilla] = NoEspecial.getCasilla(casillas_no_especiales[i]);
			casillas[num_casilla].setPosicion(num_casilla);
			if(i == casillas_no_especiales.length-1) {
				i=0;
			}
		}
	}

	/** ************************** Getter/Setter ********************************** */
	public Casilla getCasilla(int pos){
		if(pos<0 || pos>casillas.length) {
			throw new IllegalArgumentException("Acceso a casilla fuera de rango");
		}
		
		return casillas[pos];
	}
	
	/** ************************** METODOS ESPECIALES ********************************** */
	/**
	 * Devuelve un string con toda la información del tablero en formato CSV
	 */
	public String toString() {
		String tablero = new String("");
		//Cabecera del número de casilla
		tablero += String.format("\n%3s:",  "Cas"          );
		//Cabecera deñ tipo
		tablero += String.format("%-22s",   "Tipo"         );
		//Cabecera del precio de compra
		tablero += String.format(";%-18s",  "Precio Compra");
		//Cabecera del coste que se tiene al caer
		tablero += String.format(";%-18s",  "Coste caer"	  );
		//Cabecera del dueño de casilla
		tablero += String.format(";%-15s",  "Dueño casilla");
		//Cabecera para jugadores que hay en una casilla
		tablero += String.format(";%s",	"Jugadores en casilla"); 
	
		 //Línea de separación de cabecera y datos casilla
		tablero += "\n"; for(int i=0; i<150; i++) tablero += "-";

		//Ahora por cada casilla, vamos devolviendo sus datos
		for(int i=0; i<casillas.length; i++) {
			tablero += casillas[i];
		}
		tablero += "\n";
		
		//Devolemos toda la información del tablero
		return tablero;
	}
}
