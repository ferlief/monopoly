package utiles;
import java.util.Scanner;

/**
 * 
 * @author Manuel Jesús Canga Muñoz <manuelcanga@gmail.com> 
 * Esta clase se encarga de hacer de interfaz con la pantalla
 * 
 */
public class Vista {
	/**
	 * Limpia la pantalla (similar a clear o cls )
	 */
	public static void clear()  {
		for(byte i= 0; i<70; i++) {
			System.out.println("");
		}
	}
	
	/**
	 * Mostramos un aviso
	 * @param texto, texto del aviso a mostrar
	 */
	public static void alert(String texto) {
		clear();
		Vista.print(texto);
		esperarEnter();
	}
	
	/**
	 * Mostramos una línea de texto
	 * @param texto, línea de texto a mostrar
	 */
	public static void print(String texto) {
		System.out.print(texto);
	}

	/**
	 * Pedimos una información al usuario
	 * @param texto, texto de la petición a realizar
	 * @return
	 */
	public static String input(String texto) { 
		Vista.print(texto);
		Scanner Input = new Scanner(System.in);
		return Input.nextLine();
	}
	
	/**
	 * Se notifica de un error
	 * @param texto, texto del error
	 */
	public static void error(String texto) {
		clear();
		print("ERROR:"+texto+"\n");
		System.exit(-1);
	}
	
	/**
	 * Se espera a que se pulse la tecla enter
	 * @param texto, texto o textos que se visualizaran para pedir pulsar una tecla
	 */
	public static void esperarEnter(String...texto) {
		try {
			if(texto.length > 0)
				Vista.print(texto[0]);
			else
				Vista.print("\n\nPulsa enter para continuar...");
			
			System.in.read();
			if(System.in.available()> 0) 
				System.in.skip(System.in.available() );
		}catch(Exception e) {
			//Sea lo que sea el error nos da igual, pues queriamos notificar y esperar respuesta
		}
	}
}
