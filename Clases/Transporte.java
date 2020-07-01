import java.util.ArrayList;
import java.util.Iterator;

public abstract class  Transporte {

	private String id;
	private double cargaMaxima;
	private double capacidadMaxima;
	private double cargaLibre;
	private double capacidadLibre;
	private double costoKm;
	private boolean refrigeracion;
	private ArrayList <Paquete> paquetes = new ArrayList <Paquete>();
	private Destino destino;
	private boolean enViaje;
	private double costoTotal;
	
	//La id del transporte debe tener una longitud de 6 o 7 caracteres alfanumericos
	//carga maxima, capacidad maxima y costo por Km deben ser mayor a cero
	
	public Transporte (String id, double cargaMaxima, double capacidadMaxima, double costoKm, boolean refrigeracion) throws Exception{
		
		this.refrigeracion = refrigeracion;
		this.costoTotal=0;
		if (Verificacion.verificarPatente(id) && cargaMaxima > 0 && capacidadMaxima > 0 && costoKm > 0) {
			this.id = id;
			this.cargaMaxima = cargaMaxima;
			this.cargaLibre = cargaMaxima;
			this.capacidadMaxima = capacidadMaxima;
			this.capacidadLibre = capacidadMaxima;
			this.costoKm = costoKm;
			
		}
		
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

	public double getCostoTotal() {
		return costoTotal;
	}
	
	protected void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}
	
	public int cantPaquetes () {
		return this.paquetes.size();
	}
	
	public void asignarDestino (Destino destino) throws Exception {
		
		//Excepciones para controlar kilometraje con tipo de transporte
		if (this instanceof CamionTrailer && destino.getDistancia() > 500)
			throw new Exception ("Los camiones trailer solo hacer viajes menores a 500 KM");
		if (this instanceof CamionMegaTrailer && destino.getDistancia() <= 500)
			throw new Exception ("Los camiones mega trailer solo hacer viajes mayores a 500 KM");
		
		this.destino = destino;
		calcularCostoTotal();
	}
	
	private void blanquearDestino () {
		this.destino = null;
	}

	//Dado un paquete, revisa sus caraceristicas y de ser posible lo carga
	//Devuelve el valor en L cargado
	public double cargar (Paquete paquete, Deposito d) {
		
		double cargado = 0;
		try {
			if (paquete.getPeso() <= this.cargaLibre
				&& paquete.getVolumen() <= this.capacidadLibre) {
					
				if (this.refrigeracion && paquete.isFrio() || !this.refrigeracion 
					&& !paquete.isFrio()) {
						
						this.paquetes.add(paquete);
						cargaLibre -= paquete.getPeso();
						capacidadLibre -= paquete.getVolumen();
						cargado += paquete.getVolumen();
						if (d instanceof DepositoTercerizado && d.isRefrigeracion()) 
							//Agrega un proporcional por deposito tercerizado
							this.costoTotal += 
							(paquete.getPeso() * ((DepositoTercerizado) d).getPrecioTonelada() / 1000);
				}
			}
		} catch (NullPointerException e) {
		   System.out.print ("El transporte no tiene un destino asignado");
		}
		return cargado;
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
	
	//Dependiendo el tipo de transporte, calcula el costo del viaje
	public abstract void calcularCostoTotal();

	public double costoKms (){
		
		if (this.destino != null)
			return this.destino.getDistancia() * this.costoKm;
		else
			return 0;
	}
	
	public void recibirCarga(Transporte t) {
		
		//Le otorga los paquetes del transporte averiado al 
		//transporte nuevo
		this.paquetes.addAll(t.paquetes);
		
		//Vacia la carga del transporte averiado
		t.vaciarCarga();
		
		//Blanquea su destino
		t.blanquearDestino();
		
		//Le otorga al nuevo transporte el costo del transporte anterior
		this.costoTotal = t.getCostoTotal();
		
	}
	
	public static String transportesList (ArrayList <Transporte> list) {
		
		StringBuilder str = new StringBuilder ();
		for (Transporte t: list) {
			str.append(t.toString() + "\n");
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
		
		if (obj instanceof Transporte) {
			//Casteo de objeto obj
			Transporte o = (Transporte) obj;
			
			//Verifica que tenga las mismas caracteristicas
			if (o.getDestino().getUbicacion().equals(this.getDestino().getUbicacion())
					&& o.getClass().equals(this.getClass())) {
				
				//Crea un nuevo array para no modificar el original
				ArrayList <Paquete> list = new ArrayList<>();
				//Copia del array original
				list.addAll(o.paquetes);

				//Verifica carga
				if (o.cantPaquetes() == this.cantPaquetes()) {
					for (int i = 0 ; i < this.cantPaquetes() ; i++) {
						for (int j = 0; j < list.size(); j++) {
							//Si encuentra un paquete igual, lo elimina de la lista
							if (this.paquetes.get(i).equals(list.get(j))) {
								list.remove(j);
								System.out.println(list.size());
								j = list.size();
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
