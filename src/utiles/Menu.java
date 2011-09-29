package utiles;
import java.util.ArrayList;



/**
 *
 * Esta clase se encaga de recabar las opciones de un menu
 * Visualizarlo y luego devolver la opción escogida.
 * 
 * @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
 * 
 */
public class Menu {
	//Las distintas opciones que se presentaran en el menú
	private ArrayList <String> opciones;
	//Título que tendrá el menú
	private String title;
	
	//Constructor por defecto
	public Menu() {	
		this("");
	}
	
	/* Constructor que pasamos el titulo y las distintas
	 * opciones que formaran parte del menú
	*/
	public Menu(String titulo, String... opciones) {
		this.opciones = new ArrayList<String>();
		this.title = titulo;
		
		//Si se han añadido opciones las añadimos.
		if(opciones.length > 0) {
			for(int i=0; i<opciones.length; i++) {
				addOption(opciones[i]);
			}
		}
	}
	
	//Añadimos una opción al menú
	public void addOption(String opcion) {
		opciones.add(opcion);
	}
	
	/** Visualizamos el menú */
	public int ver() {
		//La opción mínima
		int min = 1;
		//La opción máxima
		int max = opciones.size();
		//Por defecto no hay opción selecionada
		int selec = -1;
		
		do {
			//Limpiamos la pantalla
			Vista.clear();
			//Si hay un título de menú, lo mostramos
			if(this.title != "") {
				Vista.print(title+"\n\n");
			}
			
			/* Vamos mostrando las distintas opciones con una númeración asociada:
			 * 1.inicio | 2.... | 3.... | 4.salir
			 */
			for(int i = min; i<=max; i++) {
				Vista.print(" "+i+"."+opciones.get(i-1)+"\n");
			}
			/* si se selecionó una opción pero estamos aquí
			 * es que fue una erronea 
			 */
			if(selec != -1) {
				Vista.print("\n Opción no válida.\n");
			}
			try {
				//Optenemos la opción elegida pasandola a entero
				selec = Integer.parseInt(Vista.input("\nSeleccione una opción("+min+"-"+max+"):"));
			}catch(NumberFormatException e) { //Se escribió algo no numérico
				//Si se seleciono algo no numérico consideramos que escribió un 0
				selec = 0;
			}
		//Seguimos mientras que no se haya escogido una opción de las disponible
		}while(selec<min  || selec>max);

		//Devolvemos el número de la opción elegida
		return selec;
	}
}
