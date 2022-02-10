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

/**
 * @author juang <juangmuelas@gmail.com>
 * @since 14/01/2021
 * @version 1
 */
//Mensajes que intercambia el Servidor con el Cliente según protocolo HTTP
public class Mensajes {
    /**
     * @param LINEA_INICIAL_OK  String con modificador final para asegurar
     * el dato de respuesta inalterable.
     * @param LINEA_INICIAL_NOT_FOUND String con modificador final para 
     * asegurar el dato de respuesta inalterable.
     */
    /**
     * En este apartado mantenemos el código ofrecido por el temario para 
     * la resolución del ejercicio.
     * Sólo modifico el nombre de las variables, para acomodarlo a los 
     * estándares, pues estaban en minúsculas.
     */
    public static final String LINEA_INICIAL_OK = "HTTP/1.1 200 OK";
    public static final String LINEA_INICIAL_NOT_FOUND =
            "HTTP/1.1 404 Not Found";
  //  public static final String lineaInicial_BadRequest =
  //          "HTTP/1.1 400 Bad Request";
}//Fin clase Mensajes
