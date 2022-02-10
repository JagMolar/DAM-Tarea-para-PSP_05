/*
 * TAREA PSP05. EJERCICIO 1.
 * Modifica el ejemplo del servidor HTTP (Proyecto java ServerHTTP, apartado 
 * 5.1 de los contenidos) para que incluya la cabecera Date.
 * El servidor se basa en la versión 1.1 del protocolo HTTP.
 * Implementa solo una parte del protocolo con solo peticiones GET
 * y dos tipos de mensajes: peticiones de clientes a servidores y respuestas
 * de servidores a clientes. 
 *
 * RECORDAR  COMENTAR EL PACKAGE SI SE QUIERE COMPILAR FUERA DE NETBEANS.
 */
package servidorhttp;

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
public class ServidorHttp {

    /**
     ***************************************************************************
     * procedimiento principal que asigna a cada petición entrante un socket 
     * cliente, por donde se enviará la respuesta una vez procesada 
     * @param args the command line arguments
     * @throws IOException, requerida al crear nuestro ServerSocket
     */
    public static void main(String[] args) throws IOException, Exception {
        //Asociamos al servidor el puerto 8066
        ServerSocket socServidor = new ServerSocket(8066);
        //El método que escribirá en servidor las instrucciones
        imprimeDisponible();
        /**
         * @param socCliente socket de comunicación.
         */
        Socket socCliente;

        //ante una petición entrante, procesa la petición por el socket cliente
        //por donde la recibe
        while (true) {
          //a la espera de peticiones
          socCliente = socServidor.accept();
          //atiendo un cliente
          System.out.println("Atendiendo al cliente ");
          //Salta al método que sirve al cliente la página
          procesaPeticion(socCliente);
          //cierra la conexión entrante
          socCliente.close();
          System.out.println("cliente atendido");
        }
        
    }//Fin clase main
    
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
   *
   * @throws IOException
   */
  private static void procesaPeticion(Socket socketCliente) throws IOException {
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
}//Fin clase ServidorHttp
