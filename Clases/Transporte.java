import java.util.ArrayList;

public abstract class  Transporte {

	protected String id;
	protected double cargaMaxima;
	protected double capacidadMaxima;
	protected double cargaLibre;
	protected double capacidadLibre;
	protected double costoKm;
	protected boolean refrigeracion;
	protected ArrayList <Paquete> paquetes = new ArrayList <Paquete>();
	protected Destino destino;
	protected boolean enViaje;
	protected double costoTotal;
	
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
	
	public void setDestino(Destino destino) {
		this.destino = destino;
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

	public ArrayList<Paquete> getPaquetes() {
		return paquetes;
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
	
	public void setCostoTotal(double costoTotal) {
		this.costoTotal = costoTotal;
	}

	//Dado un paquete, revisa sus caraceristicas y de ser posible lo carga
	//Devuelve el valor en L cargado
	public double cargar (Paquete paquete, Deposito d) {
		
		double cargado = 0;
		try {
			if (paquete.getPeso() <= this.cargaLibre) {
				if(paquete.getVolumen() <= this.capacidadLibre) {
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
					else
						System.out.println("El Transporte requiere otras condiciones de frio");
				}
				else
					System.out.println("El volumen del paquete "+ "es mayor a la capacidad libre");
			}
			else {
				System.out.println ("El peso del paquete "  +"es mayor al peso libre");
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
		
		if (this.enViaje == false)
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
	
	public void otorgarCarga(Transporte t) {
		
		for (Paquete p: t.getPaquetes()) {
			this.getPaquetes().add(p);
		}
		//Vacia la carga del transporte averiado
		t.vaciarCarga();
		
		//Blanquea su destino
		t.setDestino(null);
		
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
		
		//Verifica que no tenga la misma posición de memoria
		if (this == obj)
			return false;
		//Casteo de objeto obj
		Transporte o = (Transporte) obj;
		
		if (o.getDestino().getUbicacion().equals(this.getDestino().getUbicacion())
				&& o.getClass().equals(this.getClass())) {
			
			boolean retOr = false;
			boolean retAnd = true;
			
			if (o.getPaquetes().size() == this.paquetes.size()) {
				for (int i = 0 ; i < this.paquetes.size() ; i++) {
					for (int j = 0 ; j < o.getPaquetes().size(); j ++) {
						retOr = retOr || o.getPaquetes().get(j).equals(this.paquetes.get(i));
					}
					retAnd = retAnd && retOr;
					retOr = false;
				}
			}
			else
				return false;
			
			return retAnd;
		}
		else
			return false;		
	}


}
