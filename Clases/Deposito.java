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
	//La capacidad libre debe ser menor a capacidad maxima.
	//Los paquetes están representados por un HashMap, donde las claves son strings de destinos de los paquetes y los valores son listas con paquetes con los destinos correspondientes.
	
	public Deposito(boolean refrigeracion, double capacidadMaxima , int id) throws Exception {
		
		if (capacidadMaxima <= 0) 
			throw new RuntimeException ("La capacidad maxima debe ser mayor a cero");
		
		paquetes = new HashMap <String, ArrayList<Paquete>>();
		this.refrigeracion = refrigeracion;
		this.capacidadMaxima = capacidadMaxima;
		this.capacidadLibre = capacidadMaxima;
		this.id = id;
	}

	public boolean isRefrigeracion() {
		return refrigeracion;
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
			//Verifica condiciones de refrigeracion del paquete y deposito
			if (paquete.isFrio() == this.refrigeracion){
				//Si no habia paquetes con el mismo destino
				if (!this.paquetes.containsKey(paquete.getDestino()))
					this.paquetes.put(paquete.getDestino(), new ArrayList<Paquete>());
				
				this.paquetes.get(paquete.getDestino()).add (paquete);
				this.capacidadLibre -= paquete.getVolumen();
				return true;
			}
			//Si no cumple con las mismas condiciones de frio
			return false;
		}
		//Si no hay lugar en el deposito
		return false;
	}
	
	//Al cargar el transporte devuelve el volumen y el peso de lo cargado
	public void cargarTransporte (Transporte transporte, String destino) throws Exception {
		
		if (this.paquetes.containsKey(destino)) { 
			
			ArrayList <Paquete> p = this.paquetes.get(destino);
			Iterator<Paquete> it = p.iterator();
			while (it.hasNext()) {
				
				Paquete paquete = (Paquete) it.next();
				if (cargarPaqueteATransporte(transporte, paquete)) {
					//Si el transporte pudo agregar el paquete
					this.capacidadLibre += paquete.getVolumen();
					//Elimina el paquete del deposito
					it.remove();
				}
			}
			
			if (p.size() ==0)
				this.paquetes.remove(destino);
		}
		
	}
	
	//Dado un transporte y un paquete, realiza la carga del paquete al transporte
	protected boolean cargarPaqueteATransporte (Transporte transporte, Paquete paquete) throws Exception {
		if (transporte.cargarPaquete(paquete))
			return true;
		
		return false;
	}
	
	//Devuelve la cantidad de paquetes frios que hay en el deposito
	public int cantidadPaquetesFrios () {
		
		int cont = 0;
		
		if (this.refrigeracion){
			Iterator<String> it =this.paquetes.keySet().iterator();
			while (it.hasNext()) {
				ArrayList <Paquete> list = (ArrayList<Paquete>) this.paquetes.get(it.next());
				cont += list.size();
			}
		}
		
		return cont;
	}
	
	@Override
	public String toString () {
		StringBuilder str = new StringBuilder ("Deposito ");
		str.append (this.id);
		str.append ("\n");
		str.append ("Capacidad maxima ");
		str.append (this.capacidadMaxima);
		str.append("L");
		str.append ("\n");
		str.append ("\n");
		
		Iterator<String> it = this.paquetes.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			str.append("Paquetes con destino a ");
			str.append(key);
			str.append(": ");
			str.append(this.paquetes.get(key).size());
			str.append ("\n");
		}
		
		return str.toString();
	}
}


