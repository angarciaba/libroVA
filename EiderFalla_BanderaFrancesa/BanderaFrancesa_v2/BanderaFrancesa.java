
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
   private Thread runner = null;
   private boolean seguir;
   
   
   /** M&eacute;todo Constructor
    */
   public BanderaFrancesa() {   	  
   	  addMouseListener(this);
   	  //crea la población con posición inicial (x,y)
   	  poblacion = new vecCelulas(15, 21);	  
   }

   /** Inicializador de la Applet
    */
   public void init() {
      cFondo = new Color(160,185,185);
	  cTexto = new Color(0,0,0);
      setBackground(cFondo);
	  //Variable para detener el ciclo de chequeo de población.
	  seguir = true;
	  //Crea las células de la población.
	  poblacion.addElement(new Celula("#", "#"));	  
	  poblacion.addElement(new Celula("P", "I"));
	  for (int i=1; i<24; i++){
	  	poblacion.addElement(new Celula("I", "I"));
		//addMouseListener(poblacion.elementAt(i));
	  }
	  poblacion.addElement(new Celula("#", "#"));
   }
   
   /**
    */
   public void mouseClicked(MouseEvent e) {
   	  if ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK) {	  	 
	     if (runner == null) {	  
		 	System.out.println("Inicia..."); 
            runner = new Thread(this);
		    seguir = true;
		    poblacion.resetCelulas();
            runner.start();
         }
	     else {
		   System.out.println("Detiene..."); 
		   stop();
	     }
	  }
	  else if ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK) {
	  	 //System.out.println("Mouse Clicked Left");	 		 
	  	 poblacion.celulaClicked(e.getX(), e.getY());
	  }   	     
   }
   public void mousePressed(MouseEvent e) { }
   public void mouseReleased(MouseEvent e) { }
   public void mouseEntered(MouseEvent e) { }
   public void mouseExited(MouseEvent e) { }

   /** Detiene el hilo
     */
   public void stop() {
      if (runner != null) {
     	seguir = false;	
     	runner = null;	    
     }         
   }
   
   /** 
    */
   public void run() {
   	  //while (!poblacion.isEquilibrio()) {
	  while (seguir) {
   	     poblacion.chequearCelulas();		 
	     repaint();
		 try { 
		   Thread.sleep(300); 
		 }
         catch (InterruptedException e) { }
   	  }	  
   }
  
   /** Dibuja la poblaci&oacute;n con todas las c&eacute;lulas*/
   public void paint(Graphics g) {
      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.setColor(cTexto);
      g.drawString("Bandera Francesa:",7,18);
	  poblacion.pintar(g);
   }   
   
   public void destroy() { stop(); }
} //fin clase