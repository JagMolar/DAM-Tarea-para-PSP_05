/*
 * TAREA PSP05. EJERCICIO 1.
 * Modifica el ejemplo del servidor HTTP (Proyecto java ServerHTTP, apartado 
 * 5.1 de los contenidos) para que incluya la cabecera Date.
 * RECORDAR  COMENTAR EL PACKAGE SI SE QUIERE COMPILAR FUERA DE NETBEANS.
 */

package servidorhttp;

/**
 * @author juang <juangmuelas@gmail.com>
 * @since 14/01/2021
 * @version 1
 */

/**
 * ****************************************************************************
 * clase no instanciable donde se definen algunos valores finales
 * 
 * Sólo modifico el nombre de las variables, para acomodarlo a los 
 * estándares, pues estaban en minúsculas.
 */

public class Paginas {
    
    /**
     * @param PRIMERA_CABECERA  String con modificador final para asegurar
     * el dato de respuesta inalterable.
     * @param FECHA_CABECERA String con modificador final para 
     * asegurar el dato de respuesta inalterable.
     * @param HTML_INDEX String con modificador final para asegurar
     * el dato de respuesta inalterable.
     * @param HTML_QUIJOTE String con modificador final para 
     * asegurar el dato de respuesta inalterable.
     * @param HTML_NO_ENCONTRADO  String con modificador final para asegurar
     * el dato de respuesta inalterable.
     */
    
    /**
     * Declaración de las variables
     */
    public static final String PRIMERA_CABECERA =
          "Content-Type:text/html;charset=UTF-8";
    /**
     * Añadimos la cabecera Date, como nos han pedido para la tarea.
     */
    public static final String FECHA_CABECERA = 
            "Date:" + ServidorHttp.getDateValue(); 
    //contenido index
    public static final String HTML_INDEX = "<!DOCTYPE html>"
            + "<html>"
            + "<head><title>index</title></head>"
            + "<body>"
            + "<h1>¡Enhorabuena!</h1>"
            + "<p>Tu servidor HTTP mínimo funciona correctamente</p>"
            + "</body>"
            + "</html>";
    //contenido quijote
    public static final String HTML_QUIJOTE = "<!DOCTYPE html>"
            + "<html>"
            + "<head><title>quijote</title></head>"
            + "<body>"
            + "<h1>Así comienza el Quijote</h1>"
            + "<p>En un lugar de la Mancha, de cuyo nombre no quiero "
            + "acordarme, no ha mucho tiempo que vivía un hidalgo de los "
            + "de lanza en astillero, adarga antigua, rocín flaco y galgo "
            + "corredor. Una olla de algo más vaca que carnero, salpicón "
            + "las más noches, duelos y quebrantos (huevos con tocino) los "
            + "sábados, lentejas los viernes, algún palomino de añadidura "
            + "los domingos, consumían las tres partes de su hacienda. El "
            + "resto della concluían sayo de velarte (traje de paño fino), "
            + "calzas de velludo (terciopelo) para las fiestas con sus "
            + "pantuflos de lo mismo, y los días de entresemana se honraba "
            + "con su vellorí (pardo de paño) de lo más fino. Tenía en su "
            + "casa una ama que pasaba de los cuarenta, y una sobrina que "
            + "no llegaba a los veinte, y un mozo de campo y plaza, que "
            + "así ensillaba el rocín como tomaba la podadera...</p>"
            + "</body>"
            + "</html>";
    //contenido noEncontrado
    public static final String HTML_NO_ENCONTRADO = "<!DOCTYPE html>"
            + "<html>"
            + "<head><title>noEncontrado</title></head>"
            + "<body>"
            + "<h1>¡ERROR! Página no encontrada</h1>"
            + "<p>La página que solicitaste no existe en nuestro "
            + "servidor</p>"
            + "</body>"
            + "</html>"; 
} //Fin clase Paginas
