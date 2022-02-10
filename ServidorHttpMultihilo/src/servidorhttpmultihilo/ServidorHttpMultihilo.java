/*
 * TAREA PSP05. EJERCICIO 2.
 * Modifica el ejemplo del servidor HTTP (Proyecto java ServerHTTP, apartado 
 * 5.1 de los contenidos) para que implemente multihilo, y pueda gestionar 
 * la concurrencia de manera eficiente.
 * 
 * Para ello, usaremos la estructura propuesta en el apartado 5.2, que nos
 * añade un archivo más para manejar los hilos, llamada HiloDespachador, 
 * que será una extensión de la clase Thread de Java, cuyo constructor 
 * almacenará el socketCliente que recibe en una variable local utilizada 
 * luego por su método run() para tramitar la respuesta.
 * 
 * RECORDAR  COMENTAR EL PACKAGE SI SE QUIERE COMPILAR FUERA DE NETBEANS.
 */
package servidorhttpmultihilo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;



/**
 * @author juang <juangmuelas@gmail.com>
 * @since 14/01/2021
 * @version 1
 */


/**
 * *****************************************************************************
 * Servidor HTTP que atiende peticiones de tipo 'GET' recibidas por el puerto 
 * 8066
 *
 * NOTA: para probar este código, comprueba primero de que no tienes ningún otro
 * servicio por el puerto 8066 (por ejemplo, con el comando 'netstat' si estás
 * utilizando Windows)
 */
public class ServidorHttpMultihilo {
    
    /**
     * @param socCliente socket de comunicación.
     * @param hilo objeto para el uso de threads
     */
          
    private static Socket socCliente;
    private static HiloDespachador hilo;

    /**
     ***************************************************************************
     * procedimiento principal que asigna a cada petición entrante un socket 
     * cliente, por donde se enviará la respuesta una vez procesada 
     * @param args the command line arguments
     * @throws IOException, requerida al crear nuestro ServerSocket
     */
    public static void main(String[] args) throws IOException,Exception {
        try {
            //Asociamos al servidor el puerto 8066
            ServerSocket socServidor = new ServerSocket(8066);
            //El método que escribirá en servidor las instrucciones
            imprimeDisponible();

            //acepta una petición entrante, procesa la petición por el socket cliente
            //por donde la recibe
            while (true) {
              //a la espera de peticiones
              socCliente = socServidor.accept();
              //atiendo un cliente (AÑADIMOS A LA IMPRESION EL CLIENTE CONCRETO)
              System.out.println("Atendiendo al cliente " + socCliente.toString());
              
              /**
               * Siguiendo la estructura del ejemplo 5.2:
               * crea un nuevo hilo para despacharla por el socketCliente que 
               * le asignó.
               */
              
              hilo = new HiloDespachador(socCliente);
              hilo.start();
            }//Fin while
        } catch (IOException e) {
            e.getMessage();
        } //Fin try-catch
    } //Fin clase Main
    
    /**
     * Método que retorna la fecha y hora actual de un modo apropiado para
     * usarlo en cabeceras HTTP
     * @return 
     */
    public static String getDateValue(){
        DateFormat df = new SimpleDateFormat(
        "EEE, d MMM yyyy HH:mm:ss z", Locale.ENGLISH);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date());
    }
    
    /**
   *****************************************************************************
   * procesa la petición recibida
   * Eliminamos la restricción de acceso private para ser visible desde Hilo
   * @throws IOException
   */
    static void procesaPeticion(Socket socketCliente) throws IOException {
    /**
     * Variables locales
     * @param peticion String para el BufferReader.
     * @param html String recoge datos desde paginas.
     */
    String peticion;
    String html;

    //Flujo de entrada
    InputStreamReader inSR = new InputStreamReader(socketCliente.getInputStream());
    //espacio en memoria para la entrada de peticiones
    BufferedReader bufLeer = new BufferedReader(inSR);

    //objeto de java.io que entre otras características, permite escribir 
    //'línea a línea' en un flujo de salida
    PrintWriter printWriter = new PrintWriter(socketCliente.getOutputStream(), true);

    //mensaje petición cliente
    peticion = bufLeer.readLine();

    //para compactar la petición y facilitar así su análisis, suprimimos todos 
    //los espacios en blanco que contenga
    peticion = peticion.replaceAll(" ", "");

    //si realmente se trata de una petición 'GET' (que es la única que vamos a
    //implementar en nuestro Servidor)
    if (peticion.startsWith("GET")) {
      //extrae la subcadena entre 'GET' y 'HTTP/1.1'
      peticion = peticion.substring(3, peticion.lastIndexOf("HTTP"));

      //si corresponde a la página de inicio
      if (peticion.length() == 0 || peticion.equals("/")) {
        //sirve la página
        html = Paginas.HTML_INDEX;
        printWriter.println(Mensajes.LINEA_INICIAL_OK);
        printWriter.println(Paginas.PRIMERA_CABECERA);       
        /**
         * Incluimos la cabecera date de la tarea.
         */
        printWriter.println(Paginas.FECHA_CABECERA);
        printWriter.println("Content-Length: " + html.length() + 1);
        printWriter.println("\n");
        printWriter.println(html);
      } //si corresponde a la página del Quijote
      else if (peticion.equals("/quijote")) {
        //sirve la página
        html = Paginas.HTML_QUIJOTE;
        printWriter.println(Mensajes.LINEA_INICIAL_OK);
        printWriter.println(Paginas.PRIMERA_CABECERA);
        /**
         * Incluimos la cabecera date de la tarea.
         */
        printWriter.println(Paginas.FECHA_CABECERA);
        printWriter.println("Content-Length: " + html.length() + 1);
        printWriter.println("\n");
        printWriter.println(html);
      } //en cualquier otro caso
      else {
        //sirve la página
        html = Paginas.HTML_NO_ENCONTRADO;
        printWriter.println(Mensajes.LINEA_INICIAL_NOT_FOUND);
        printWriter.println(Paginas.PRIMERA_CABECERA);
        /**
         * Incluimos la cabecera date de la tarea.
         */
        printWriter.println(Paginas.FECHA_CABECERA);
        printWriter.println("Content-Length: " + html.length() + 1);
        printWriter.println("\n");
        printWriter.println(html);
      }  
    } // Fin if-else de peticiones GET
  } //Fin método procesaPeticion

  /**
   * **************************************************************************
   * muestra un mensaje en la Salida que confirma el arranque, y da algunas
   * indicaciones posteriores
   */
  private static void imprimeDisponible() {

    System.out.println("El Servidor WEB se está ejecutando y permanece a la "
            + "escucha por el puerto 8066.\nEscribe en la barra de direcciones "
            + "de tu explorador preferido:\n\nhttp://localhost:8066\npara "
            + "solicitar la página de bienvenida\n\nhttp://localhost:8066/"
            + "quijote\n para solicitar una página del Quijote,\n\nhttp://"
            + "localhost:8066/q\n para simular un error");
  } //Fin método imprimeDisponible
    
}//Fin clase ServidorHttpMultihilo
