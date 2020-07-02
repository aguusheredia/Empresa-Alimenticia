import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Deposito {

	private int id;
	private Map<String, ArrayList<Paquete>> paquetes;
	private boolean refrigeracion;
	private double capacidadMaxima;
	private double capacidadLibre;
	
	//El nombre debe ser una cadena no vacia
	//La capacidad maxima debe ser mayor a cero
	
	public Deposito(boolean refrigeracion, double capacidadMaxima , int id) throws Exception {
		
		if (capacidadMaxima <= 0) 
			throw new Exception ("La capacidad maxima debe ser mayor a cero");
		
		paquetes = new HashMap <String, ArrayList<Paquete>>();
		this.refrigeracion = refrigeracion;
		this.capacidadMaxima = capacidadMaxima;
		this.capacidadLibre = capacidadMaxima;
		this.id = id;
	}


	public boolean isRefrigeracion() {
		return refrigeracion;
	}

	public double getCapacidad() {
		return this.capacidadMaxima;
	}
	
	public double getCapacidadLibre() {
		return this.capacidadLibre;
	}
	
	public int getId() {
		return id;
	}	
	
	//Dado un paquete lo agrega al deposito
	//De ser posible, devuelve true, de lo contrario, devuelve false
	public boolean agregarPaquete (Paquete paquete) {
		//Verifica capacidad
		if (paquete.getVolumen() <= this.capacidadLibre){
			//Verifica si requiere frio el paquete
			if (!paquete.isFrio() || 
					(paquete.isFrio() && this.refrigeracion)){
				//Si no habia paquetes con el mismo destino
				if (!this.paquetes.containsKey(paquete.getDestino()))
					this.paquetes.put(paquete.getDestino(), new ArrayList<Paquete>());
				
				this.paquetes.get(paquete.getDestino()).add (paquete);
				this.capacidadLibre -= paquete.getVolumen();
				return true;
			}
			//Si no cumple con las condiciones de frio
			return false;
		}
		//Si no hay lugar en el deposito
		return false;
	}
	
	//Al cargar el transporte devuelve el volumen y el peso de lo cargado
	public double[] cargarTransporte (Transporte transporte, String destino) {
		
		double [] ret = new double [2];
		ret[0] = 0;
		ret [0] = 0;
		
		if (!this.paquetes.containsKey(destino)) 
			return ret;		
		
		double volumenTotal = 0;
		double volumenPaquete;
		double peso = 0;
		
		ArrayList <Paquete> p = this.paquetes.get(destino);
		Iterator<Paquete> it = p.iterator();
		while (it.hasNext()) {
			Paquete paquete = (Paquete) it.next();
			volumenPaquete = transporte.cargar(paquete, this);
			
			if (volumenPaquete > 0) {
				this.capacidadLibre += paquete.getVolumen();
				volumenTotal += volumenPaquete;
				peso += paquete.getPeso();
				it.remove();
			}
		}
		
		ret [0] = volumenTotal;
		ret [1] = peso;
		return ret;
	}

	
	//Devuelve la cantidad de paquetes frios que hay en el deposito
	public int paquetesFrios () {
		
		int cont = 0;
		
		if (this.refrigeracion){
			Iterator<String> it =this.paquetes.keySet().iterator();
			while (it.hasNext()) {
				ArrayList <Paquete> list = (ArrayList<Paquete>) this.paquetes.get(it.next());
				for (Paquete paquete: list) {
					if (paquete.isFrio()) {
						cont ++;
					}
				}
			}
		}
		
		return cont;
	}
	
	@Override
	public String toString () {
		StringBuilder ret = new StringBuilder ("Deposito ");
		ret.append (this.id + "\n");
		ret.append ("Capacidad maxima ");
		ret.append (this.capacidadMaxima);
		ret.append("L");
		ret.append ("\n");
		ret.append ("\n");
		
		Iterator<String> it = this.paquetes.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ret.append("Paquetes con destino a ");
			ret.append(key);
			ret.append(": ");
			ret.append(this.paquetes.get(key).size());
			ret.append ("\n");
		}
		
		return ret.toString();
	}

}

