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

import java.io.IOException;
import java.net.Socket;

/**
 * @author juang <juangmuelas@gmail.com>
 * @since 14/01/2021
 * @version 1
 */
public class HiloDespachador extends Thread {
    private Socket socketCliente;

    public HiloDespachador(Socket socketCliente) {
        this.socketCliente = socketCliente;
    }
    
    
    public void run() {
        /**
         * Aprovechamos la estructura del ejemplo, añadiendo el proceso 
         * de atención y cerrado del cliente.
         */
        
        try{  
            //Llamamos al método del servidor para procesar la petición
            ServidorHttpMultihilo.procesaPeticion(socketCliente);
            //cierra la conexión entrante
            socketCliente.close();
            System.out.println("cliente atendido");          
        } catch (IOException ex) {
            ex.printStackTrace();
        }    
    }
}//Fin clase HiloDespachador
