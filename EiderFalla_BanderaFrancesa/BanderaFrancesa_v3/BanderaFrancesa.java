/**/
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;

/** Clase: BanderaFrancesa
 *    Crea una poblaci&oacute;n de c&eacute;lulas, le da vida y las dibuja en pantalla
 *    de acuerdo al estado que estas presenten, cuando finaliza, da muerte a su poblaci&oacute;n
 *    y se sale del applet.
 *  @Author: Eider J. Falla 
 */
//public class BanderaFrancesa extends javax.swing.JApplet implements MouseListener, Runnable {
public class BanderaFrancesa extends java.applet.Applet implements Runnable, MouseListener {
   /** Define el color del fondo y otros colores*/
   private Color cFondo, cTexto;
   /** La poblaci&oacute;n de c&eacute;lulas*/
   private vecCelulas poblacion;
   /** Hilo principal de ejecuci&oacute;n*/
   private Thread runner = null;
   /** Variable para detener el ciclo de ejecuci&oacute;n*/
   private boolean seguir;
   /** Define mensajes para mostrar en pantalla*/
   private String msg;
   
   
   /** M&eacute;todo Constructor, crea el vector de poblaci&oacute;n de
    *  c&eacute;lulas.
    */
   public BanderaFrancesa() {   	  
   	  addMouseListener(this);
   	  //crea la población con posición inicial (x,y)
   	  poblacion = new vecCelulas(15, 21);
   }

   /** Inicializador de la Applet, define colores de fondo y texto, y crea
    *  cada uno de los objetos c&eacute;lula, para luego definir sus 
	*  respectivos vecinos (izq, der).
    */
   public void init() {
      cFondo = new Color(160,185,185);
	  cTexto = new Color(0,0,0);
      setBackground(cFondo);
	  msg = new String("");
	  //Variable para detener el ciclo de chequeo de población.
	  seguir = true;
	  //Crea las células de la población.
	  poblacion.addElement(new Celula("#", "#", 0));	  
	  poblacion.addElement(new Celula("P", "I", 1));
	  for (int i=1; i<18; i++){
	  	poblacion.addElement(new Celula("I", "I", i + 1));
	  }
	  poblacion.addElement(new Celula("#", "#", 19));
	  //Una vez se establece la población, se definen los vecinos 
	  //de cada célula
	  poblacion.definirVecinos();
   }
   
   /** Captura el evento del mouse cuando se realiza un clicked, si es izq.
    *  inicializa el hilo principal y a la poblaci&oacute;n, si es derecho
	*  chequea para ver si se marco un c&eacute;lula con #.
    */
   public void mouseClicked(MouseEvent e) {
   	  if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {	  	 
	     if (runner == null) {	  		 	
		 	//crea el hilo principal
            runner = new Thread(this);
		    seguir = true;			
			//reinicializa las células de la población
		    poblacion.resetCelulas();
			//Inicia el hilo
            runner.start();
			System.out.println("Inicia..."); 
         }
	     else {
		   stop();
	     }
	  } 
	  else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
	  	 //si dio click derecho verifica si marco un célula para pasarla a #, y 
		 //marcar como P a sus vecinas.
	  	 poblacion.celulaClicked(e.getX(), e.getY());
	  }
   }
   /** Otros eventos del mouse que no se utilizan.*/
   public void mousePressed(MouseEvent e) { }
   public void mouseReleased(MouseEvent e) { }
   public void mouseEntered(MouseEvent e) { }
   public void mouseExited(MouseEvent e) { }

   /** Detiene el hilo principal y asesina a la poblaci&oacute;n.
     */
   public void stop() {
      if (runner != null) {
	  	poblacion.darMuerte();
     	seguir = false;
     	runner = null;
		System.out.println("Detiene...");
     }         
   }
   
   /** Inicia el Hilo principal que contiene a los otros, luego genera la vida
    *  en la c&eacute;lulas de la poblaci&oacute;n y se mantiene en un constante
	*  repaint(), para dibujar cualquier cambio en el estado de las c&eacute;lulas.
    */
   public void run() {
   	  System.out.println("Iniciar el Hilo...");
	  msg = "Viven.";
	  repaint();
	  poblacion.darVida();
	  while (seguir) {
	     repaint();
	     try { 
	        Thread.sleep(100); 
	     }
         catch (InterruptedException e) { }	  
   	  }	
	  msg = "Mueren.";
	  repaint();
   }
  
   /** Dibuja la poblaci&oacute;n con todas las c&eacute;lulas, también 
    *  los letreros que son necesarios visualizar.
    */
   public void paint(Graphics g) {
      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.setColor(cTexto);
      g.drawString("Bandera Francesa:",7,18);
	  g.drawString(msg, 7, 85);
	  poblacion.pintar(g);
   }   
   
   /** Se encarga de verificar la detenci&oacute;n del hilo principal y sus
    *  hijos cuando el Applet es destruido.
    */
   public void destroy() { stop(); }
} //fin clase