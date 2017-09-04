/**/
import java.util.Vector ;
import java.util.Enumeration ;
import java.awt.Graphics ;

/** Clase: vecCelulas
 *    Es la encargada de mantener toda la poblaci&oacute;n de c&eacute;lulas, adiciona,
 *    obtiene, da vida y muerte a c&eacute;lulas, tambi&eacute;n les da orden de dibujarse.
 *  @Author: Eider J. Falla
 */
public class vecCelulas {
	/** Vector Interno*/
	private Vector interno;
	/** Posici&oacute;n (X,Y)*/
	private int x = 0, y = 0;
	
	
	/** M&eacute;todo Constructor, crea el vector que contendra a la poblaci&oacute;n.*/
	vecCelulas(int x, int y) {
		interno = new Vector();
		this.x = x;
		this.y = y;		
	}
	
	/** Agrega una c&eacute;lula a la poblaci&oacute;n*/
	public void addElement(Celula c){
		interno.addElement(c);
	}
	
	/** 0btiene la c&eacute;lula en la posici&oacute;n i */
	public Celula elementAt(int i) {
		return (Celula) interno.elementAt(i);
	}
	
	/** Pinta todos los elementos de la poblaci&oacute;n en el contexto grafico g*/	
	public void pintar(Graphics g){
		int i = 0;
	
	    //left + i*20 + 2, 5, 17, 17
		for (Enumeration e = interno.elements(); e.hasMoreElements();) {
      		((Celula) e.nextElement()).paint(g, (x + i*20 + 2), y);
			i++;
    	}
	}
    
	/** Da vida a cada una de las c&eacute;lulas de la poblaci&oacute;n diciendole
	 *  a su hilo de ejecuci&oacute;n que se inicie.
	 */
	public void darVida() {
	   	Celula c;
	
		for (int i=0; i<gSize(); i++) {
			c = elementAt(i);			
			//si no es una célula #
			if (!c.getEstado().equalsIgnoreCase("#")) 
			{   System.out.print("Celula " + i);
				c.start();				
			}
		}
	}
	
	/** Asesina cada una de las c&eacute;lulas de la poblaci&oacute;n dando
	 *  fin a su hilo de ejecuci&oacute;n.
	 */
	public void darMuerte() {
	   for (int i=0; i<gSize(); i++) 
	 	  elementAt(i).stop();
	}
	
	/** Define los vecinos (izq. y der.) de cada una de las c&eacute;lulas de
	 *  la poblaci&oacute;n.
	 */
	public void definirVecinos() {
	   Celula c, izq, der;
	
	   for (int i=0; i<gSize(); i++) {
	 	  c = elementAt(i);
		  //si no es una célula #
		  if (!c.getEstado().equalsIgnoreCase("#")) {
		  	  izq = elementAt(i - 1);
			  der = elementAt(i + 1);
			  c.setVecinos(izq, der);
		  }
	   }
	}
	
    /** Determina si toda la poblaci&oacute;n se ha estabilizado
	 */
	public boolean isEquilibrio() {
		//si la primera célula esta en equilibrio todo esta en equilibrio
		if( ((Celula) interno.elementAt(1)).isEquilibrio() )
			return true;
		
		return false;			
	}

	/** Obtiene el n&uacute;mero de elementos del vector*/
	public int gSize(){
		return interno.size();
	}
	
    /** 
	 */
	public void resetCelulas() {
		elementAt(1).resetCelula("P", "I");
		for (int i = 2; i<(gSize()-1); i++) {
			elementAt(i).resetCelula("I", "I");
		}
	}

    /** 
	 */
    public void celulaClicked(int posX, int posY) {
	   Celula c;
	   	   
	   for (int i=0; i<gSize(); i++) {
	   	 c = elementAt(i);
	     if(c.isCoordenada(posX, posY)) {
		   	System.out.println("Es la celula # " + i);			
		    c.anularCelula();
			c = elementAt(i - 1);
			if(!c.getEstado().equalsIgnoreCase("#"))
				c.resetCelula("P", "I");
			c = elementAt(i + 1);
			if(!c.getEstado().equalsIgnoreCase("#"))
				c.resetCelula("P", "I");
		 }
	   }
	}
} //fin de la clase