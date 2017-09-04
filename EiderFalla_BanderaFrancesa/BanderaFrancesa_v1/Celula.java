

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
public class Celula {
   /** Mantiene el estado actual de la celula*/
   private String estado, estadoAntes;
   /** Define el tamaño de la celula */
   private int tam = 17;
   
   
   /** M&eacute;todo Constructor
    */
   Celula(String estado, String antes) {
   	 this.estado = estado;
	 estadoAntes = antes;
   }   
   
   /** M&eacute;todo para obtener el estado de la c&eacute;lula
    */
   public String getEstado() { return estado; }
   
   /** M&eacute;todo para inicializar la c&eacute;lula.
    */
   public void resetCelula(String Actual, String Antes) {
   	  estado = Actual;
	  estadoAntes = Antes;
   }
   
   /** M&eacute;todo para obtener el estado que tenia antes
    */
   public String getAntes() { return estadoAntes; }
   
   /**
    */
   public void cambiarEstado(String nuevo) {
   	 estadoAntes = estado;
	 estado = nuevo;
   }
   
   /** Retorna -true- si la c&eacute;lula ha alcanzado un estado de equilibrio
    */
   public boolean isEquilibrio() {
   	 if(estado.equalsIgnoreCase("A") || estado.equalsIgnoreCase("B") || estado.equalsIgnoreCase("R"))
	 	return true;
		
	 return false;
   }
    
   /**Dibuja la c&eacute;lula en la posici&oacute;n (x, y), en el entorno gr&acute;fico g
    */
   public void paint(Graphics g, int x, int y) {
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
	  g.fillRect( x, y + tam + 35, tam, tam);
	  g.setFont(new Font("Arial", Font.BOLD, 10));
      g.setColor(Color.black);
      g.drawString(estadoAntes, x + 1, y + tam*2 + 30);
	  
	  g.setColor(Color.white);
	  g.fillRect( x, y + 2*tam + 40, tam, tam);
	  g.setFont(new Font("Arial", Font.BOLD, 10));
      g.setColor(Color.black);
      g.drawString(estado, x + 1, y + tam*3 + 35);
   }
   
   /** M&eacute;todo para chequear estado
    */
   public void chequearEstado(Celula izq, Celula der) {
      if(estado.equalsIgnoreCase("P"))
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
		
	  //if(estado.equalsIgnoreCase("") && izq.getEstado().equalsIgnoreCase("") && der.getEstado().equalsIgnoreCase(""))
   }
   
   
} //fin de la clase


/*
   public void chequearEstado(Celula izq, Celula der) {
      if(estado.equalsIgnoreCase("P"))
	  	cambiarEstado("Z");
	  else if(estado.equalsIgnoreCase("I") && izq.getEstado().equalsIgnoreCase("Z"))
	    cambiarEstado("Z");
	  else if(estado.equalsIgnoreCase("I") && !izq.getEstado().equalsIgnoreCase("Z"))
	  	cambiarEstado("I");
	  else if(estado.equalsIgnoreCase("Z") && der.getEstado().equalsIgnoreCase("#"))
	  	cambiarEstado("A");
	  else if(estado.equalsIgnoreCase("Z") && der.getEstado().equalsIgnoreCase("A"))	
	  	cambiarEstado("A");
	  else if(estado.equalsIgnoreCase("Z") && izq.getEstado().equalsIgnoreCase("#"))
	  	cambiarEstado("N0");
	  else if(estado.equalsIgnoreCase("Z") && izq.getEstado().equalsIgnoreCase("N1"))
	  	cambiarEstado("N0");
	  else if(estado.equalsIgnoreCase("Z") && !izq.getEstado().equalsIgnoreCase("N1") && !izq.getEstado().equalsIgnoreCase("#"))
	  	cambiarEstado("Z");
	  else if((estado.equalsIgnoreCase("N0") || estado.equalsIgnoreCase("N1")) && der.getEstado().equalsIgnoreCase("A"))
	  	cambiarEstado("B");
	  else if((estado.equalsIgnoreCase("N0") || estado.equalsIgnoreCase("N1")) && der.getEstado().equalsIgnoreCase("B"))
	  	cambiarEstado("B");
	  else if(estado.equalsIgnoreCase("N0") && izq.getEstado().equalsIgnoreCase("#") && der.getEstado().equalsIgnoreCase("N0"))
	  	cambiarEstado("J0");
	  else if(estado.equalsIgnoreCase("N0") && izq.getEstado().equalsIgnoreCase("#") && der.getEstado().equalsIgnoreCase("Z"))
	  	cambiarEstado("N1");
	  else if(estado.equalsIgnoreCase("N0") && izq.getEstado().equalsIgnoreCase("J4"))	
	  	cambiarEstado("J0");
	  else if(estado.equalsIgnoreCase("N0") && !izq.getEstado().equalsIgnoreCase("J4") && !izq.getEstado().equalsIgnoreCase("#"))	
	  	cambiarEstado("N1");
	  else if(estado.equalsIgnoreCase("N1") && izq.getEstado().equalsIgnoreCase("J4"))
	  	cambiarEstado("J0");
	  else if(estado.equalsIgnoreCase("N1") && !izq.getEstado().equalsIgnoreCase("J4"))
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
 	}

*/