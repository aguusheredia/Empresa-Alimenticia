import java.util.ArrayList;
import java.util.Iterator;

public class  Transporte {

	private String id;
	private double cargaMaxima;
	private double capacidadMaxima;
	private double cargaLibre;
	private double capacidadLibre;
	private double costoKm;
	private boolean refrigeracion;
	private ArrayList <Paquete> paquetes;
	private Destino destino;
	private boolean enViaje;
	private double costosExtras;
	
	//La id del transporte debe tener una longitud de 6 o 7 caracteres alfanumericos
	//carga maxima, capacidad maxima y costo por Km deben ser mayor a cero
	
	public Transporte (String id, double cargaMaxima, double capacidadMaxima, double costoKm, boolean refrigeracion) throws Exception{
		
		//Excepciones para controlar el IREP
		if (!Verificacion.verificarPatente(id)) 
			throw new Exception ("La patente debe tener 6 o 7 caracteres con el siguiente formato: "
					+ "JBY261, AA123BB");
		
		if (cargaMaxima <= 0)
			throw new Exception ("La carga maxima debe ser mayor a cero");
		
		if (capacidadMaxima <= 0) 
			throw new Exception ("La capacidad maxima debe ser mayor a cero");
		
		if (costoKm <= 0) 
			throw new Exception ("El costo por Km debe ser mayor a cero");
		
		paquetes = new ArrayList <Paquete>();
		this.refrigeracion = refrigeracion;
		this.costosExtras=0;
		this.id = id;
		this.cargaMaxima = cargaMaxima;
		this.cargaLibre = cargaMaxima;
		this.capacidadMaxima = capacidadMaxima;
		this.capacidadLibre = capacidadMaxima;
		this.costoKm = costoKm;
	}
	
	public double getCargaMaxima() {
		return cargaMaxima;
	}

	public double getCapacidadMaxima() {
		return capacidadMaxima;
	}
	
	public String getId() {
		return id;
	}

	public boolean isRefrigeracion() {
		return refrigeracion;
	}

	public Destino getDestino() {
		return destino;
	}

	public boolean isEnViaje() {
		return enViaje;
	}


	public int cantPaquetes () {
		return this.paquetes.size();
	}
	
	public void asignarDestino (Destino destino) throws Exception {
		
		if (this.cantPaquetes() > 0)
			throw new Exception ("El transporte ya esta cargado");
		
		if (this.destino != null)
			throw new Exception ("EL transporte ya tiene un destino asignado");
		
		if (this.enViaje)
			throw new Exception ("El transporte se encuentra en viaje");
		
		this.destino = destino;
	}

	//Dado un paquete, revisa sus caraceristicas y de ser posible lo carga
	//Devuelve el valor en L cargado
	public boolean cargar (Paquete paquete) {
		
		//Verifica todas las caracteristicas del paquetes y transporte
		//De ser posible, lo suma a la carga
		if (paquete.getPeso() <= this.cargaLibre
			&& paquete.getVolumen() <= this.capacidadLibre) {
				
			if (this.refrigeracion && paquete.isFrio() || !this.refrigeracion 
				&& !paquete.isFrio()) {
					
					this.paquetes.add(paquete);
					//Baja la carga y capacidad libre del transporte
					cargaLibre -= paquete.getPeso();
					capacidadLibre -= paquete.getVolumen();
					return true;
			}
		}
		return false;
	}
	
	//Dado un paquete y un precio por tonelada, realiza la suma necesaria a costos Extras
	public void sumarCostosTercerizadoFrio(Paquete paquete, double precioPorTonelada) throws Exception {
		if (paquete == null)
			throw new Exception ("Debe recibir un paquete");
		
		if (precioPorTonelada < 0)
			throw new Exception ("El precio por tonelada deve ser igual o mayor a cero");
		
		if (paquete.isFrio()) {
			this.costosExtras += (paquete.getPeso() * precioPorTonelada) / 1000;
		}
		
	}
	
	
	public double volumenCargado() {
		double volumen = 0;
		for (Paquete p: this.paquetes) {
			volumen += p.getVolumen();
		}
		return volumen;
	}
	
	//Inicia viaje, el transporte debe estar cargado y tener un viaje previamente asignado 
	public void iniciarViaje () throws Exception{
		if (this.enViaje == true || this.destino == null || this.paquetes.size() == 0 )
			throw new Exception ("El transporte ya esta en viaje, no tiene viaje asignado o no esta cargado");
		
		else
			this.enViaje = true;
	}
	

	//Finaliza el viaje, blanquea el Viaje, limpia su carga y vuelve los valores de capacidad y carga libres a maximo
	public void finalizarViaje () throws Exception{
		
		if (!this.enViaje)
			throw new Exception ("El transporte no se encuentra en viaje");
		
		else {
			this.enViaje=false;
			vaciarCarga();
			this.cargaLibre = this.cargaMaxima;
			this.capacidadLibre = this.capacidadMaxima;
			this.destino = null;
		}
	}
	
	//Vacia su carga
	public void vaciarCarga (){
		paquetes.clear();
	}

	public double calcularCostoTotal () throws Exception{
		
		if (this.destino != null)
			return this.destino.getDistancia() * this.costoKm;
		
		throw new Exception ("El transporte no tiene destino asignado");
	}
	
	public double darCostoTotal() throws Exception {
		return costosExtras + calcularCostoTotal();
	}
	
	public boolean reemplazablePor(Transporte t) {
		//Corrobora que el transporte que se envia este vacío, sin destino
		//Y que tenga las características necesarias para intercambiar la carga
		if (t.getDestino() == null &&
				t.cantPaquetes() == 0 &&
				t.getClass().equals(this.getClass()) &&
				t.getCapacidadMaxima() >= this.getCapacidadMaxima() &&
				t.getCargaMaxima() >= this.getCargaMaxima()){
			
			if ((t.isRefrigeracion() && this.isRefrigeracion()) ||
					(!t.isRefrigeracion() && !this.isRefrigeracion())){
				return true;
			}
			
		}
		return false;
	}
	
	public void reemplazarPor (Transporte t) throws Exception {
		//Le otorga al transporte nuevo el mismo destino
		//que el transporte averiado
		t.destino = this.destino;
		t.recibirCarga (this);
	}
	
	public void recibirCarga(Transporte t) throws Exception {
		
		//Le otorga los paquetes del transporte averiado al 
		//transporte nuevo
		this.paquetes.addAll(t.paquetes);
		
		double pesoOcupado = t.cargaMaxima - t.cargaLibre;
		double capacidadOcupada = t.capacidadMaxima - t.capacidadLibre;
		
		this.cargaLibre = this.cargaMaxima - pesoOcupado;
		this.capacidadLibre = this.capacidadMaxima - capacidadOcupada;

		//Vacia la carga del transporte averiado
		t.finalizarViaje();
		
	}
	
	public static String transportesList (ArrayList <Transporte> list) {
		
		StringBuilder str = new StringBuilder ();
		for (Transporte t: list) {
			str.append(t.toString()).append("\n");
		}
		
		return str.toString();
	}
	
	@Override
	public String toString () {
		if (this.refrigeracion)
			return this.id + "  " + "Tiene camara frigorifica";
		else
			return this.id + "  " + "No tiene camara frigorifica";
	}
	
	//Identifica si un transporte y un objeto son iguales
	//Debe tener mismo destino, ser el mismo tipo y llevar la misma carga
	@Override
	public boolean equals (Object obj) {
		
		if (obj == null)
			return false;
		
		if (obj instanceof Transporte) {
			//Casteo de objeto obj
			Transporte o = (Transporte) obj;
			
			//Verifica que tenga las mismas caracteristicas
			if (o.getDestino().equals(this.getDestino())
					&& o.getClass().equals(this.getClass())) {
				
				//Crea un nuevo array para no modificar el original
				ArrayList <Paquete> list = new ArrayList<>();
				//Copia del array original
				list.addAll(o.paquetes);

				//Verifica carga
				if (o.cantPaquetes() == this.cantPaquetes()) {
					
					Iterator <Paquete> it =this.paquetes.iterator();
					//Si no encuentra un paquete, frenará todo el ciclo
					boolean find = true;
					
					while (it.hasNext() && find) {
						Paquete p1 = it.next();
						find = false;
						
						Iterator <Paquete> kit = list.iterator();
						//Variable para para ejecución
						boolean stop = false;
						while (kit.hasNext() && !stop) {
							Paquete p2 = kit.next();
							
							//Si encuentra el paquete, lo elimina de la lista y para la ejecucion
							if (p1.equals(p2)) {
								kit.remove();
								stop = true;
								find = true;
							}
						}
					}
					//Si se eliminan todos los elementos de la lista
					//Todos los elementos se encontraban en la otra carga
					if (list.size() == 0)
						return true;
				}
				//Si tienen distinto numero de paquetes o es distinta carga
				return false;	
			}
			//Retorna falso no tiene las mismas caracteristicas
			return false;
		}
		//Retorna falso si no es un transporte
		return false;
	}








}
