import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Deposito {

	private int id;
	private Map<String, ArrayList<Paquete>> paquetes = new HashMap <String, ArrayList<Paquete>>();
	private boolean refrigeracion;
	private double capacidadMaxima;
	private double capacidadLibre;
	
	//El nombre debe ser una cadena no vacia
	//La capacidad maxima debe ser mayor a cero
	
	public Deposito(boolean refrigeracion, double capacidadMaxima , int id) throws Exception {
		this.refrigeracion = refrigeracion;
		
		if (capacidadMaxima > 0) {
			this.capacidadMaxima = capacidadMaxima;
			this.capacidadLibre = capacidadMaxima;
			this.id = id;
		}
		
		if (capacidadMaxima <= 0) 
			throw new Exception ("La capacidad maxima debe ser mayor a cero");
	}


	public boolean isRefrigeracion() {
		return refrigeracion;
	}

	public double getCapacidad() {
		return this.capacidadMaxima;
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

	//Dado una identificacion de un paquete, revisa si esta alojado en el deposito. De ser asi, lo entrega y lo limpia de su sistema
	public double entregarPaquetes (ArrayList <Paquete> paquetes, Transporte transporte) {
		
		double cargadoTotal = 0;
		double cargadoPaquete;
		
		Iterator<Paquete> it = paquetes.iterator();
		while (it.hasNext()) {
			Paquete p = (Paquete) it.next();
			cargadoPaquete = transporte.cargar(p, this);
			
			if (cargadoPaquete > 0) {
				this.capacidadLibre += p.getVolumen();
				cargadoTotal += cargadoPaquete;
				it.remove();
			}
		}
		
		return cargadoTotal;
	}
	
	@Override
	public String toString () {
		StringBuilder ret = new StringBuilder ("Deposito ");
		ret.append (this.id + "\n");
		ret.append ("Capacidad maxima " +this.capacidadMaxima + "L");
		ret.append ("\n");
		ret.append ("\n");
		
		Iterator<String> it = this.paquetes.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ret.append("Paquetes con destino a " + key + ": " + this.paquetes.get(key).size());
			ret.append ("\n");
		}
		
		return ret.toString();
	}


	public Map<String, ArrayList<Paquete>> getPaquetes() {
		return paquetes;
	}



}

