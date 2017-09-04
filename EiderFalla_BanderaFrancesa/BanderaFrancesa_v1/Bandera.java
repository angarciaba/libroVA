import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Event;


/** Clase: BanderaFrancesa
 *    Crea una poblaci&oacute;n de c&eacute;lulas, le da vida y las dibuja en pantalla
 *    de acuerdo al estado que estas presenten, cuando finaliza, da muerte a su poblaci&oacute;n
 *    y se sale del applet.
 *  @Author: Eider J. Falla 
 */
public class Bandera extends java.applet.Applet  implements Runnable {
   /** Define el color del fondo y otros colores*/
   private Color cFondo, cTexto;
   /** La poblaci&oacute;n de c&eacute;lulas*/
   private vecCelulas poblacion;
   private Thread runner = null;
   
   /** M&eacute;todo Constructor
    */
   public Bandera() {
   	  //crea la población con posición inicial (x,y)
   	  poblacion = new vecCelulas(15, 21);
	  //crea las células de la población
	  poblacion.addElement(new Celula("#", "#"));
	  poblacion.addElement(new Celula("P", "I"));
	  for (int i=1; i<18; i++){
	  	poblacion.addElement(new Celula("I", "I"));
	  }
	  poblacion.addElement(new Celula("#", "#"));
   }

   /** Iniciador para los graficos
    */
   public void init() {
      cFondo = new Color(160,185,185);
	  cTexto = new Color(0,0,0);
      setBackground(cFondo);      
   }
   
   /** Detiene el hilo
     */
   public void stop() {
      if (runner != null) 
         runner = null;
   }
  
   /** 
    */
   public boolean mouseDown(Event evt, int x, int y) {
      if (runner == null) {	  
         runner = new Thread(this);
		 poblacion.resetCelulas();
         runner.start();
      }
      return true;
   }
  
   /** 
    */
   public void run() {
   	  while (!poblacion.isEquilibrio()) {
   	     poblacion.chequearCelulas();		 
	     repaint();
		 try { 
		   Thread.sleep(300); 
		 }
         catch (InterruptedException e) { }
   	  }
	  
	  runner = null;
   }
  
   /** Dibuja la poblaci&oacute;n con todas las c&eacute;lulas*/
   public void paint(Graphics g) {
      g.setFont(new Font("Arial", Font.BOLD, 12));
      g.setColor(cTexto);
      g.drawString("Bandera Francesa:",7,18);
	  poblacion.pintar(g);
   }   
   

} //fin clase