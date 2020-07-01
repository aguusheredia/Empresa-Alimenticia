import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Empresa {

	private String nombre;
	private String cuit;
	private Map <String, Transporte> transportes = new HashMap<String, Transporte>();
	private ArrayList <Deposito> depositos = new ArrayList <Deposito>();
	private	Map <String, Destino> destinos = new HashMap <String, Destino>();
	
	//El nombre debe ser una cadena no vacia
	//El cuit debe contener 11 digitos numericos
	
	public Empresa (String cuit,String nombre) throws Exception {
		
		if (Verificacion.verificarCuit(cuit) && nombre.length()>0) {
			this.nombre = nombre;
			this.cuit = cuit;
		}
		
		//Se encarga de cumplir el IREP
		if (nombre.length() == 0)
			throw new Exception ("La empresa debe tener un nombre");
		
		if (!Verificacion.verificarCuit(cuit)) 
			throw new Exception ("El cuit debe tener 11 numeros");
	}
	
	public Transporte darTransporte (String id) throws Exception {
		if (!this.transportes.containsKey(id))
			throw new Exception ("El transporte no esta en la empresa");
		
		return this.transportes.get(id);
	}

	
	//Dado las caracteriticas, crea y asigna un deposito nuevos a la empresa
	//Dicho deposito es propio o tercerizado sin frigorifico
	public int agregarDeposito (double capacidad, boolean frigorifico, boolean propio) throws Exception {
		

		//Si el deposito es propio
		if (propio) {
			Deposito nuevo = new Deposito (frigorifico, capacidad, this.depositos.size()+1);
			this.depositos.add (nuevo);
			return nuevo.getId();	
		}
		//Si es tercerizado
		else {
			DepositoTercerizado nuevo = new DepositoTercerizado (frigorifico, capacidad, this.depositos.size()+1, 0);
			this.depositos.add (nuevo);
			return nuevo.getId();
		}
		
	}

	//Dado las caracteristica de un deposito Tercerizado con frigorifico, lo crea y lo suma  a la empresa
	public int agregarDepTercerizFrio (double capacidad, double costoTonelada) throws Exception {
		DepositoTercerizado nuevo = new DepositoTercerizado (true, capacidad, this.depositos.size()+1, costoTonelada);
		this.depositos.add (nuevo);
		return nuevo.getId();
	}

	//Dado las caracteristicas, crea un transporte de acuerdo con los parametros otorgados
	public void agregarTransporte (String idTransporte, double cargaMax, double capacidad,
											boolean frigorifico, double costoKm, double segCarga) throws Exception {
	
			agregarTrailer (idTransporte, cargaMax, capacidad,
											frigorifico, costoKm, segCarga);
	}
	
	//Dado las caracteristicas, crea un transporte de acuerdo con los parametros otorgados
	public void agregarTransporte (String idTransporte, double cargaMax, double capacidad,
													boolean frigorifico, double costoKm, double segCarga,
													double costoFijo, double comida) throws Exception {
		
		agregarMegaTrailer (idTransporte, cargaMax, capacidad,
													frigorifico, costoKm, segCarga,
													costoFijo, comida);
	}
	
	//Dado las caracteristicas, crea un transporte de acuerdo con los parametros otorgados
	public void agregarTransporte (String idTransporte, double cargaMax, double capacidad, double costoKm,
										int acomp, double costoPorAcom) throws Exception {
		agregarFlete (idTransporte, cargaMax, capacidad, costoKm,
										 acomp, costoPorAcom);
	}
	
	//Dado las caracterÃ­sticas de un camion trailer, lo crea y lo suma a la empresa
	public void agregarTrailer (String idTransporte, double cargaMax, double capacidad,
											boolean frigorifico, double costoKm, double segCarga) throws Exception {
		
		
		if (!transportes.containsKey(idTransporte)) {
			CamionTrailer nuevo = new CamionTrailer (idTransporte, cargaMax, capacidad,
					costoKm, frigorifico, segCarga);
			transportes.put(idTransporte, nuevo);
		}
		
	}

	//Dado las caracteristicas de un camion mega trailer, lo crea y lo suma a la empresa
	public void agregarMegaTrailer (String idTransporte, double cargaMax, double capacidad,
													boolean frigorifico, double costoKm, double segCarga,
													double costoFijo, double comida) throws Exception {
		
		if (!transportes.containsKey(idTransporte)) {
			CamionMegaTrailer nuevo = new CamionMegaTrailer (idTransporte,cargaMax,capacidad,
					costoKm,frigorifico,segCarga,costoFijo,comida);
			transportes.put(idTransporte, nuevo);
		}
	}

	//Dado las caracteristicas de un flete, lo crea y lo suma a la empresa
	public void agregarFlete (String idTransporte, double cargaMax, double capacidad, double costoKm,
										int acomp, double costoPorAcom) throws Exception {
		
		if (!transportes.containsKey(idTransporte)) {
			Flete flete = new Flete (idTransporte,cargaMax,capacidad,costoKm,costoPorAcom);
			flete.setCantidadPasajeros (acomp + 1);
			transportes.put(idTransporte, flete);
		}
		
	}
	
	//Dado las caracteristicas de un paquete, lo crea, lo suma a la empresa y lo asigna a un deposito
	//Devuelve true si es posible la incorporacion del paquete, de lo contrario otorga false
	public boolean incorporarPaquete (String destino, double peso, double volumen, boolean frio) throws Exception {
			Paquete paquete = new Paquete (destino,volumen,peso,frio);
			
			for (Deposito x: depositos) {
				if ((boolean) x.agregarPaquete(paquete)) 
					return true;
			}
			return false;
	}
	
	//Crea un destino con su ubicacion y la cantidad de Km y lo suma a la lista de la empresa
	public void agregarDestino (String destino, int km) throws Exception {
		
		Destino d = new Destino (destino, km);
		destinos.put(destino, d);
	}

	//Dado un transporte y un destino, le asigna el viaje correspondiente al transporte
	public void asignarDestino (String idTransporte, String destino) throws Exception {
		
		if (this.transportes.containsKey(idTransporte)) {
			
			//Asignacion de transporte en una variable para evitar la busqueda del mismo
			//en cada interacción
			Transporte transporte = this.transportes.get(idTransporte);
			if (transporte.cantPaquetes() > 0)
				throw new Exception ("El transporte ya esta cargado");
			
			if (destinos.containsKey(destino)) {
				Destino d = destinos.get(destino);
				transporte.asignarDestino(d);	
			}
			else
				throw new Exception ("El destino no se encuentra en la empresa");
		}
		else
			throw new Exception ("El transporte " + idTransporte + " no se encuentra en la empresa");
	}

	//Dado un transporte, le asigna la funcion de cargar paquetes correspondientes
	public double cargarTransporte (String idTransporte) {
		
		double cargado = 0;
		if (this.transportes.containsKey(idTransporte)) {
			Transporte t = this.transportes.get(idTransporte);
			String destino = t.getDestino().getUbicacion();
		
			for (Deposito deposito: this.depositos) {
				Map <String, ArrayList<Paquete>> map = deposito.getPaquetes();
				if (map.containsKey(destino)) {
					cargado += deposito.entregarPaquetes(map.get(destino), t);
				}
			}
		}
		return cargado;
	}
	
	//Dado un transporte, le indica que debe iniciar viaje
	public void iniciarViaje (String idTransporte) throws Exception {
		if (transportes.containsKey (idTransporte))
			this.transportes.get(idTransporte).iniciarViaje();
	}
	
	//Dado un transporte, le indica que debe finalizar viaje
	public void finalizarViaje (String idTransporte) throws Exception {
		if (transportes.containsKey (idTransporte))
			this.transportes.get(idTransporte).finalizarViaje();
	}
	
	//Dado un transporte, le indica que le otorgue el costo total por el viaje que tiene asignado
	public double obtenerCostoViaje (String idTransporte) {
		if (transportes.containsKey (idTransporte))
			return this.transportes.get(idTransporte).getCostoTotal();
		
		return 0;
	}
	
	//Dado un transporte, busca en la lista de transportes otro que tenga las mismas caracteristicas
	//Devuelve la identificacion del transporte igual
	public String obtenerTransporteIgual (String idTransporte) {
		
		//Verifica que el transporte se encuentre en la empresa
		if (this.transportes.containsKey(idTransporte)) {
			
			//Asignacion de transporte en una variable para evitar la busqueda del mismo
			//en cada interacción
			Transporte transporte = this.transportes.get(idTransporte);
			
			//Recorre todos los transportes de la empresa en busca de uno que sea igual
			Iterator<String> it = this.transportes.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				Transporte t = this.transportes.get(key);
				
				if (t.equals(transporte) && !t.getId().equals(transporte.getId()))
					return t.getId();
			}
		}
		return null;
	}

	public boolean intercambiarTransporte (String idTransporte) throws Exception {
		
		if (this.transportes.containsKey(idTransporte)) {
			
			Transporte transporte = this.transportes.get(idTransporte);
			
			for (String id: this.transportes.keySet()) {
				
				Transporte t = this.transportes.get(id);
				//Corrobora que el transporte que se envia este vacío, sin destino
				//Y que tenga las características necesarias para intercambiar la carga
				if (t.getDestino() == null &&
					t.cantPaquetes() == 0 &&
					t.getClass().equals(transporte.getClass()) &&
					t.getCapacidadMaxima() >= transporte.getCapacidadMaxima() &&
					t.getCargaMaxima() >= transporte.getCargaMaxima()){
					
					if ((t.isRefrigeracion() && transporte.isRefrigeracion()) ||
						(!t.isRefrigeracion() && !transporte.isRefrigeracion())){
							//Le otorga al transporte nuevo el mismo destino
							//que el transporte averiado
							this.asignarDestino(t.getId(), transporte.getDestino().getUbicacion());
							t.recibirCarga (transporte);
							//Limpia el destino del transporte anterior
							return true;
						}
				}
			}
		}
		//Si no encuentra un Transporte con las caracteristicas necesarias
		return false;
	}
	
	public String TransportesEnviaje () {
		StringBuilder str = new StringBuilder ();
		ArrayList <Transporte> fletes = new ArrayList<>();
		ArrayList <Transporte> trailers = new ArrayList<>();
		ArrayList <Transporte> megaTrailers = new ArrayList<>();
		
		Iterator<String> it = this.transportes.keySet().iterator();
		while (it.hasNext()) {
			Transporte t = (Transporte) this.transportes.get(it.next());
			if (t instanceof Flete && t.isEnViaje())
				fletes.add((Flete) t);
			
			if (t instanceof CamionTrailer && t.isEnViaje())
				trailers.add((CamionTrailer) t);
			
			if (t instanceof CamionMegaTrailer && t.isEnViaje())
				megaTrailers.add((CamionMegaTrailer) t);
		}
		
		str.append("Transportes en viaje: " + (fletes.size() + trailers.size() + megaTrailers.size())  +"\n");
		if (fletes.size() + trailers.size() + megaTrailers.size() > 0){
			str.append("Fletes: " + fletes.size() + "\n");
			str.append(Transporte.transportesList(fletes));
			str.append("\n");
			
			str.append("Camiones Trailers: " + trailers.size() + "\n");
			str.append(Transporte.transportesList(trailers));
			str.append("\n");
			
			str.append("Camiones mega Trailers: " + megaTrailers.size() + "\n");
			str.append(Transporte.transportesList(megaTrailers));
		}
		return str.toString();
	}

	public String paquetesFrios () {
		int paquetesPropios = 0;
		int paquetesTercerizados = 0;
		for (Deposito deposito: this.depositos) {
			
			if (deposito.isRefrigeracion()){
				Map<String, ArrayList<Paquete>> map = deposito.getPaquetes();
				Iterator<String> it = map.keySet().iterator();
				while (it.hasNext()) {
					ArrayList <Paquete> list = (ArrayList<Paquete>) map.get(it.next());
					for (Paquete paquete: list) {
						if (paquete.isFrio()) {
							if (deposito instanceof DepositoTercerizado)
								paquetesTercerizados++;
							else
								paquetesPropios++;
						}
					}
				}
			}
		}
		
		StringBuilder str = new StringBuilder ();
		str.append("Paquetes que necesitan frio: " + (paquetesPropios + paquetesTercerizados));
		str.append("\n");
		str.append("Paquetes en depositos propios: " + paquetesPropios + "\n");
		str.append("Paquetes en depositos Tercerizados: " + paquetesTercerizados);
		return str.toString();
	}

	public String enViajeYFrios () {
		StringBuilder str = new StringBuilder();
		str.append(this.TransportesEnviaje());
		str.append("\n");
		str.append("\n");
		str.append(this.paquetesFrios());
		
		return str.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder ret = new StringBuilder ("Nombre: ");
		ret.append (this.nombre);
		ret.append ("\n");
		ret.append ("Cuit: ");
		ret.append (this.cuit);
		ret.append ("\n");
		ret.append ("\n");
		ret.append ("Depositos: " + this.depositos.size());
		ret.append ("\n");
		//Impresion de depositos
		for (Deposito e: depositos) {
			ret.append ("Depósito " + e.getId());
			if (e.isRefrigeracion())
				ret.append("  Con refrigeración");
			else
				ret.append("  Sin refrigeración");
			ret.append("  Capacidad: " + e.getCapacidad() + " L");
			ret.append ("\n");
		}
		ret.append ("\n");
		ret.append ("\n");
		
		//Impresion de transportes
		ret.append ("Transportes: " + this.transportes.size());
		ret.append ("\n");
		Iterator<String> it = this.transportes.keySet().iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			ret.append ("Vehiculo " + this.transportes.get(key).getId() + "\n");
		}
		return ret.toString();
	}
	
}


