package partida.jugador;
import utiles.Config;
import utiles.Menu;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Clase que gestiona todo lo relacionado con los colores de un jugador
 * No se ha hecho con Enums porque un color no podía ser asignado a más de un jugador
 * No se ha hecho por conjunto para mantener tanto el nombre del color, como para poder
 * ir pidiéndolo fácilmente(mediante un numérico ) 
 * @implements serializable: Para poder guardar el color asociado en una serialización
 * @author Manuel J. Canga Muñoz
 *
 */
class Color implements Serializable{

	//Es el label del color selecionado
	private String seleccionado;
	//Listado de colores disponibles a escoger
	private static ArrayList<String> colores;
	//UID único para evitar inconsistencias
	private static final long serialVersionUID = 5011486051274565511L;


	//inicialización la parte estática de la clase
	static {
		//Cargamos los colores base desde el archivo de configuración del juego
		add((String[]) Config.getArray("colores_base"));
	}
	
	
	/**
	 *  Constructor que nos permite inicializar el objeto
	 *  desde GUI
	 */
	public Color() {
		//Si no hay más colores es lógico que tenemos que lanzar una excepción
		if(colores.size() < 1) {
			throw new IllegalAccessError("No hay más colores para escoger");
		}
		
		//Pedimos el color que se quiera y lo guardamos
		seleccionado = pedirColor();
	}
	
	/**
	 * Constructor que recibe el color a ser usado
	 * @param color
	 */
	public Color(String color) {
		//Al selecionar un color hay que quitarlo del listado de colores disponible
		if (colores.contains(color) ) {
			seleccionado = color; 
			colores.remove(color);
		}else {
			///Sino está el color lanzamos una excepción pues sólo se permiten
			//colores de los escogidos
			throw new IllegalArgumentException("No se ha encontrado color");
		}
		
	}
	
	/** @return devuelve el color del objeto	 */
	public String get() { return seleccionado; }
	
	/** Lógico que el color en formato string es su label */
	public String toString() { return this.get(); }
	
	/**
	 * Añade colores al conjunto de colores disponible
	 */	
	public static void add(String... colores) {
		//Si no se ha inicializado la lista de colores, lo hacemos a partir de los colores pasados
		if(null == Color.colores ) {
			Color.colores = new ArrayList<String>(colores.length);
		}
		
		///Empezamos a añadir los colores que nos hayan pasado y que aún no estén
		for(int i= 0; i<colores.length;) {
			if(!Color.colores.contains(colores[i])) {
				Color.colores.add(i, colores[i]);
				i++;
			}
		}
		
	}
	
	/**
	 * @return colores disponibles  en el sistema
	 */
	public static String[] getColores() {
		String[] base = new String[1];
		return (String[]) colores.toArray(base);
	}


	/**
	 * Pedimos por gui un color
	 * @return string con el label del color escogido
	 */
	public String pedirColor() {
		int color_elegido = 0;
		
		/** Para la elección del color realizamos un menú
		 * con todos los colores elegibles 
		 */
		Menu menu_colores = new Menu("Selección de color");
		
		//Cargamos el menú con los colores disponibles
		for(int i= 0; i<colores.size(); i++) {
			menu_colores.addOption(colores.get(i));
		}
			
		//MOstramos los distintos colores para que escojan uno
		color_elegido = menu_colores.ver();
		
		//Elinamos el color escogido para que nadie más pueda cogerlo
		return colores.remove(color_elegido-1);
	}
}
