

//import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;

/**
 * Clase: Celula
 *   Esta se encarga de implementar el objeto c&eacute;lula, contiene los m&eacute;todos para
 *   dibujar, con sus colores respectivos.
 * @Author: Eider J. Falla
 */
public class Celula implements Runnable {
   /** Mantiene el estado actual de la celula*/
   private String estado, estadoAntes;
   /** Define el tamaño de la celula */
   private int tam = 19;
   /** */
   private int x, y, k, id;
   private Thread runner; 
   private Celula izq, der;
   private Graphics env;
   private boolean seguir;
   

   /** M&eacute;todo Constructor, define el estado Actual y Anterior de
    *  la c&eacute;lula.
    */
   Celula(String Actual, String Antes, int nid) {
   	 runner = null;
     estadoAntes = Antes;
   	 estado = Actual;
	 x = 0;
	 y = 0;
	 seguir = true;
	 k = 0;
	 id = nid;
   }   

   /** Define cuales son sus vecinos izq. y der. para poder así establecer
    *  su estado de acuerdo al de ellos.
    */
   public void setVecinos(Celula izq, Celula der) {
     this.izq = izq;
	 this.der = der;
   }
   
   /** Detiene el hilo de ejecuci&oacute;n, la c&eacute;lula muere.
     */
   public void stop() {
      if (runner != null) {
     	runner = null;		
	    seguir = false;	
	    System.out.println(" Detenida... " + id + "." + k + "." + estado);
      }
   }
   
   /** Crea el hilo de ejeuci&oacute;n, y da espera antes de iniciar su ejecuci&oacute;n
    *  esto con el fin de dar tiempo a sus vecinos de que se creen.
    */
   public void start() { 
   	  if (runner == null) {	  	 
   	     runner = new Thread(this);
		 runner.setPriority(Thread.MIN_PRIORITY);
         runner.start(); 
		 seguir = true;
         //este sleep permite sincronizar las células.
	     try { 
		 	//estos números son importantes... debe ser 100.
	       Thread.sleep(100); 
	     } catch (InterruptedException e) { }	
		 
		 System.out.println(" Iniciada... " + id + "." + k + "." + estado);
   	  }
   }
    
   /** Ejecuta el hilo, chequeando constantemente su estado y luego durmiendo
    *  para permitir que su estado afecte a otros.
    */
   public void run() { 
   	  while (seguir) {
	  	k++;		
   	    if(!estado.equalsIgnoreCase("#")) 
	        chequearEstado();
		//System.out.println("    Estado... " + id + "." + k + "." + estado);	
		//esto permite esperar a que las células vecinas detecten mi nuevo estado.		
	    try { 
			//estos números son importantes... debe ser 200.
	       Thread.sleep(200); 
	    }
        catch (InterruptedException e) { }	
   	  }	
   }
  
   /** M&eacute;todo para obtener el estado actual de la c&eacute;lula
    */
   public String getEstado() { return estado; }
   
   /** M&eacute;todo para inicializar la c&eacute;lula, en sus estados
    *  Actual y Anterior.
    */
   public void resetCelula(String Actual, String Antes) {
   	  estado = Actual;
	  estadoAntes = Antes;
   }
   
   /** M&eacute;todo para obtener el estado que tenia antes la c&eacute;lula.
    */
   public String getAntes() { return estadoAntes; }
   
   /** Cambia el estado actual de la c&eacute;lula a nuevo y por ende modifica
    *  su estado anterior.
    */
   public void cambiarEstado(String nuevo) {
   	 estadoAntes = estado;
	 estado = nuevo;
   }
   
   /** Retorna -true- si la c&eacute;lula ha alcanzado un estado de equilibrio, este
    *  estado de equilibrio lo definen el estado A, B, y R.
    */
   public boolean isEquilibrio() {
   	 if(estado.equalsIgnoreCase("A") || estado.equalsIgnoreCase("B") || estado.equalsIgnoreCase("R"))
	 	return true;
		
	 return false;
   }
    
   /**Dibuja la c&eacute;lula en la posici&oacute;n (x, y), en el entorno gr&acute;fico g,
    * también dibuja su estado actual y anterior.
    */
   public void paint(Graphics g, int posX, int posY) {
      this.x = posX;
	  this.y = posY;
      //System.out.println("x: " + x + "   y: " + y);
	  
   	  if (estado.equalsIgnoreCase("#"))
	  	g.setColor(new Color(160,185,185));//g.setColor(Color.black);	  
      else if (estado.equalsIgnoreCase("I") || estado.equalsIgnoreCase("P"))
	  		g.setColor(new Color(225,205,215)); //g.setColor(Color.white);		
	  else if (estado.equalsIgnoreCase("Z"))
	  		g.setColor(Color.red);
		else if (estado.equalsIgnoreCase("N0") || estado.equalsIgnoreCase("N1"))
		      g.setColor(Color.white);
			else if (estado.equalsIgnoreCase("J0") || estado.equalsIgnoreCase("J1") || 
			         estado.equalsIgnoreCase("J2") || estado.equalsIgnoreCase("J3") || 
					 estado.equalsIgnoreCase("J4"))
				    g.setColor(Color.blue);
				else if (estado.equalsIgnoreCase("A"))
						g.setColor(Color.red);
					else if (estado.equalsIgnoreCase("B"))
							g.setColor(Color.white);
						else if (estado.equalsIgnoreCase("R"))
								g.setColor(Color.blue);
	     
	  g.fillRect( x, y, tam, tam*2);	  
	  
	  g.setColor(Color.white);
	  g.fillRect( x, y + tam + 55, tam, tam);
	  g.fillRect( x, y + 2*tam + 60, tam, tam);
	  //g.setFont(new Font("Arial", Font.BOLD, 10));
      g.setColor(Color.black);
      g.drawString(estadoAntes, x + 1, y + tam*2 + 50);
      g.drawString(estado, x + 1, y + tam*3 + 55);	  
   }
   
   /** M&eacute;todo para chequear estado, este realiza la inspecci&oacute;n de las
    *  c&eacute;lulas vecinas (izq. y der.) para determinar un nuevo estado actual.
    */
   public synchronized void chequearEstado() {
      if(estado.equalsIgnoreCase("P") && der.getAntes().equalsIgnoreCase("I"))
	  	cambiarEstado("Z");
	  else if(estado.equalsIgnoreCase("I") && izq.getAntes().equalsIgnoreCase("Z"))
	    cambiarEstado("Z");
	  else if(estado.equalsIgnoreCase("I") && !izq.getAntes().equalsIgnoreCase("Z"))
	  	cambiarEstado("I");
	  else if(estado.equalsIgnoreCase("Z") && der.getEstado().equalsIgnoreCase("#"))
	  	cambiarEstado("A");
	  else if(estado.equalsIgnoreCase("Z") && der.getEstado().equalsIgnoreCase("A"))	
	  	cambiarEstado("A");
	  else if(estado.equalsIgnoreCase("Z") && izq.getAntes().equalsIgnoreCase("#"))
	  	cambiarEstado("N0");
	  else if(estado.equalsIgnoreCase("Z") && izq.getAntes().equalsIgnoreCase("N1"))
	  	cambiarEstado("N0");
	  else if(estado.equalsIgnoreCase("Z") && !izq.getAntes().equalsIgnoreCase("N1") && !izq.getAntes().equalsIgnoreCase("#"))
	  	cambiarEstado("Z");
	  else if((estado.equalsIgnoreCase("N0") || estado.equalsIgnoreCase("N1")) && der.getEstado().equalsIgnoreCase("A"))
	  	cambiarEstado("B");
	  else if((estado.equalsIgnoreCase("N0") || estado.equalsIgnoreCase("N1")) && der.getEstado().equalsIgnoreCase("B"))
	  	cambiarEstado("B");
	  else if(estado.equalsIgnoreCase("N0") && izq.getAntes().equalsIgnoreCase("#") && der.getEstado().equalsIgnoreCase("N0"))
	  	cambiarEstado("J0");
	  else if(estado.equalsIgnoreCase("N0") && izq.getAntes().equalsIgnoreCase("#") && der.getEstado().equalsIgnoreCase("Z"))
	  	cambiarEstado("N1");
	  else if(estado.equalsIgnoreCase("N0") && izq.getAntes().equalsIgnoreCase("J4"))	
	  	cambiarEstado("J0");
	  else if(estado.equalsIgnoreCase("N0") && !izq.getAntes().equalsIgnoreCase("J4") && !izq.getAntes().equalsIgnoreCase("#"))	
	  	cambiarEstado("N1");
	  else if(estado.equalsIgnoreCase("N1") && izq.getAntes().equalsIgnoreCase("J4"))
	  	cambiarEstado("J0");
	  else if(estado.equalsIgnoreCase("N1") && !izq.getAntes().equalsIgnoreCase("J4"))
	  	cambiarEstado("N0");
	  else if((estado.equalsIgnoreCase("J0") || estado.equalsIgnoreCase("J1") || estado.equalsIgnoreCase("J2") || estado.equalsIgnoreCase("J3") || estado.equalsIgnoreCase("J4")) && der.getEstado().equalsIgnoreCase("B"))
	  	cambiarEstado("R");
	  else if((estado.equalsIgnoreCase("J0") || estado.equalsIgnoreCase("J1") || estado.equalsIgnoreCase("J2") || estado.equalsIgnoreCase("J3") || estado.equalsIgnoreCase("J4")) && der.getEstado().equalsIgnoreCase("R"))
	  	cambiarEstado("R");
	  else if(estado.equalsIgnoreCase("J0"))
	  	cambiarEstado("J1");
	  else if(estado.equalsIgnoreCase("J1"))
	  	cambiarEstado("J2");
	  else if(estado.equalsIgnoreCase("J2"))
	  	cambiarEstado("J3");
	  else if(estado.equalsIgnoreCase("J3"))
	  	cambiarEstado("J4");
	  else if(estado.equalsIgnoreCase("J4"))
	  	cambiarEstado("J0");
	  else if(estado.equalsIgnoreCase("A") && izq.getEstado().equalsIgnoreCase("P"))
	    resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("A") && izq.getEstado().equalsIgnoreCase("I"))
	    resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("B") && izq.getEstado().equalsIgnoreCase("P"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("B") && izq.getEstado().equalsIgnoreCase("I"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("R") && izq.getEstado().equalsIgnoreCase("P"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("R") && izq.getEstado().equalsIgnoreCase("I"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("A") && der.getEstado().equalsIgnoreCase("P"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("A") && der.getEstado().equalsIgnoreCase("I"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("B") && der.getEstado().equalsIgnoreCase("P"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("B") && der.getEstado().equalsIgnoreCase("I"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("R") && der.getEstado().equalsIgnoreCase("P"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("R") && !izq.getEstado().equalsIgnoreCase("#") && der.getEstado().equalsIgnoreCase("I"))
	   	resetCelula("I", "I");
	  else if(estado.equalsIgnoreCase("R") && izq.getEstado().equalsIgnoreCase("#") && der.getEstado().equalsIgnoreCase("I"))
	   	resetCelula("P", "I");	
	  else if(estado.equalsIgnoreCase("P") && !izq.getEstado().equalsIgnoreCase("#") && der.getEstado().equalsIgnoreCase("#"))
	   	resetCelula("I", "I");	
	  //if(estado.equalsIgnoreCase("") && izq.getEstado().equalsIgnoreCase("") && der.getEstado().equalsIgnoreCase(""))
   }
 
   /** Determina si el punto (posX, posY) se encuentra dentro del espacio de dibujo de
    *  la c&eacute;lula.
    */
   public boolean isCoordenada(int posX, int posY) {
   	  if(posX >= x && posX <= x+tam && posY >= y && posY <= y+(tam*2))
	  	return true;
	
	  return false;	
   }
   
   /** Asesina la c&eacute;lula de forma l&oacute;gica en estado #.
    */
   public void anularCelula() {
   	  estado = "#";
	  estadoAntes = "#";
   }
 
} //fin de la clase

