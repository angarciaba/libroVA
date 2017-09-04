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
	
	
	/** M&eacute;todo Constructor*/
	vecCelulas(int x, int y) {
		interno = new Vector();
		this.x = x;
		this.y = y;
	}
	
	/** Agrega una c&eacute;lula a la poblaci&oacute;n*/
	public void addElement(Celula c){
		interno.addElement(c);
	}
	
	/** 0btiene el elemento de la posicion i */
	public Celula elementAt(int i) {
		return (Celula) interno.elementAt(i);
	}
	
	/** Pinta todos los elementos del vector en el contexto grafico g*/
	public void pintar(Graphics g){
		int i = 0;
	
	    //left + i*20 + 2, 5, 17, 17
		for (Enumeration e = interno.elements(); e.hasMoreElements();) {
      		((Celula) e.nextElement()).paint(g, (x + i*20 + 2), y);
			i++;
    	}
	}

    /** Chequea cada una de las c&eacute;lulas para ver si cambia su estado*/
	public void chequearCelulas() {
	    Celula c, izq, der;
	
		for (int i = 0; i<size(); i++) {
			c = elementAt(i);
			//si no es una célula #
			if (!c.getEstado().equalsIgnoreCase("#")) {
				izq = elementAt(i - 1);
				der = elementAt(i + 1);
				c.chequearEstado(izq, der);
			}				
		}
	}

    /** Determina si toda la poblaci&oacute;n se ha estabilizado*/
	public boolean isEquilibrio() {
		//si la primera célula esta en equilibrio todo esta en equilibrio
		if( ((Celula) interno.elementAt(1)).isEquilibrio() )
			return true;
		
		return false;			
	}

	/** Obtiene el n&uacute;mero de elementos del vector*/
	public int size(){
		return interno.size();
	}

	public void resetCelulas() {
		elementAt(1).resetCelula("P", "I");
		for (int i = 2; i<(size()-1); i++) {
			elementAt(i).resetCelula("I", "I");
		}
	}

} //fin de la clase