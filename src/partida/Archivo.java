package partida;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import utiles.Vista;


/**
 * 
 * Clase para gestionar el recuperar y guardar partidas de Monopoly
 * @author Manuel J. Canga Muñoz
 *
 */
public class Archivo {
	
	//Guarda el nombre del archivo
	private String archivo;

	//Constructor sencillo
	public Archivo() { archivo = null; }
	
	/** 
	 * Constructor avanzado que inicializa mediante GUI
	 * @param tipo: r, si lectura. w, si es escritura
	 */
	public Archivo(char tipo) {
		//Sino es ni operación de lectura ni de escritura, lanzamos exception
		if(tipo != 'w' && tipo != 'r') { throw new IllegalArgumentException(); }
		
		//Si es operacion de escritura pedimos la ruta para guardar
		if('w' == tipo) {
			archivo = pedirRuta("Por favor, escriba donde quiere guardar la partida actual.");
		}else{ //Si es operacion de lectura pedimos la ruta para leer
			archivo = pedirRuta("Por favor, escriba el archivo donde se encuentra la partida guardada.");
		}
	}
	
	/** 
	 * Método que se encarga de pedir la ruta de un archivo(existente o no)
	 * @param texto  el texto base que se usará para pedir la ruta
	 * @return
	 */
	private String pedirRuta(String texto) {
		Vista.clear();
		Vista.print(texto);
		String archivo = "";
		do {
			//Mientras que no se haya escrito algún nombre de archivo
			//Seguimos pidiendo el nombre
			archivo = Vista.input("\nArchivo:");
		} while(archivo.length()<1); 
		
		return archivo;
	}

	
	/** 
	 * Hacemos el guardo del archivo de partida
	 * @param partida, objeto partida que queremos guardar
	 * @return boolean, true: partida guardada, false: fallo en el guardado
	 */
	public boolean  save(Partida partida) {
		try {
			ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(archivo));
			file.writeObject(partida);
			Vista.print("Partida guardada corréctamente");
			return true;
		} catch (FileNotFoundException e) {
			Vista.print("Ruta, o nombre, de archivo no válida");
		} catch (IOException e) {
			Vista.print("Error en el proceso de grabación");
		}
		
		Vista.esperarEnter("Pulsa enter para continuar...");
		return false;

	}
	

	/**
	 * Hacemos la carga del archivo de partida
	 * @return partida, es un objeto partida que vamos a continuar
	 */
	public Partida  load() {		
		try {
			ObjectInputStream file = new ObjectInputStream(new FileInputStream(archivo));
			return (Partida)file.readObject();
		} catch(ClassNotFoundException e) {
			Vista.print("Ruta, o nombre, del archivo no válida");
		} catch (IOException e ) {
			Vista.print("Archivo no encontrado o no podido leer.");
		}
		
		Vista.esperarEnter("\nPulsa enter para continuar...");
		return null;
	}
}
;;