import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Empresa {

	private String nombre;
	private String cuit;
	private Map <String, Transporte> transportes;
	private ArrayList <Deposito> depositos;
	private	Map <String, Destino> destinos;
	
	//El nombre debe ser una cadena no vacia
	//El cuit debe contener 11 digitos numericos
	//	Los transportes representado con HashMap, las claves son strings con ids del transporte, y los valores el transporte correspondiente.
	//	No puede haber dos transportes con la misma identificacion
	//	Los destinos representados en un HashMap, donde las claves es un string con la ubicación y las claves los destinos correspondientes.
	//	Los depósitos estarán alojados en una lista, que llevaran en orden los Id de los depósitos, donde el primero llevará el Id número 1.

	
	public Empresa (String cuit,String nombre) throws Exception {
		
		//Se encarga de cumplir el IREP
		if (nombre == null)
			throw new RuntimeException ("La empresa debe tener un nombre");
			
		if (nombre.length() == 0)
			throw new RuntimeException ("La empresa debe tener un nombre");
		
		if (!Verificacion.verificarCuit(cuit)) 
			throw new RuntimeException ("El cuit debe tener 11 numeros");
		
		transportes = new HashMap<String, Transporte>();
		depositos = new ArrayList <Deposito>();
		destinos = new HashMap <String, Destino>();
		this.nombre = nombre;
		this.cuit = cuit;
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
		else if (!frigorifico){
			DepositoTercerizado nuevo = new DepositoTercerizado (frigorifico, capacidad, this.depositos.size()+1, 0);
			this.depositos.add (nuevo);
			return nuevo.getId();
		}
		
		//Devuelve cero, ya que no asigna costo por tonelada por ser tercerizado con frigorifico
		return 0;
		
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
	
		if (!transportes.containsKey(idTransporte))
			agregarTrailer (idTransporte, cargaMax, capacidad,frigorifico, costoKm, segCarga);
	}
	
	//Dado las caracteristicas, crea un transporte de acuerdo con los parametros otorgados
	public void agregarTransporte (String idTransporte, double cargaMax, double capacidad,
													boolean frigorifico, double costoKm, double segCarga,
													double costoFijo, double comida) throws Exception {
		
		if (!transportes.containsKey(idTransporte))
			agregarMegaTrailer (idTransporte, cargaMax, capacidad, frigorifico, costoKm, segCarga, costoFijo, comida);
	}
	
	//Dado las caracteristicas, crea un transporte de acuerdo con los parametros otorgados
	public void agregarTransporte (String idTransporte, double cargaMax, double capacidad, double costoKm,
										int acomp, double costoPorAcom) throws Exception {
		
		if (!transportes.containsKey(idTransporte))
			agregarFlete (idTransporte, cargaMax, capacidad, costoKm, acomp, costoPorAcom);
	}
	
	//Dado las caracteristicas de un camion trailer, lo crea y lo suma a la empresa
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
			Flete flete = new Flete (idTransporte,cargaMax,capacidad,costoKm, acomp, costoPorAcom);
			transportes.put(idTransporte, flete);
		}
		
	}
	
	//Dado las caracteristicas de un paquete, lo crea, lo suma a la empresa y lo asigna a un deposito
	//Devuelve true si es posible la incorporacion del paquete, de lo contrario otorga false
	public boolean incorporarPaquete (String destino, double peso, double volumen, boolean frio) throws Exception {
			Paquete paquete = new Paquete (destino,volumen,peso,frio);
			
			for (Deposito x: depositos) {
				if (x.agregarPaquete(paquete)) 
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
			
			if (destinos.containsKey(destino)) {
				Destino d = destinos.get(destino);
				transporte.asignarDestino(d);	
			}
			else
				throw new RuntimeException ("El destino no se encuentra en la empresa");
		}
		else
			throw new RuntimeException ("El transporte " + idTransporte + " no se encuentra en la empresa");
	}
	
	//Devuelve la cantidad de paquetes de un transporte
	public int cantPaquetesEnTransporte (String idTransporte) throws Exception {
		if (!this.transportes.containsKey(idTransporte))
			throw new RuntimeException ("El transporte no se encuentra en la empresa");
		
		return this.transportes.get(idTransporte).cantPaquetes();
	}
	
	//Devuelve el destino de un transporte
	public Destino destinoDeTransporte (String idTransporte) throws Exception {
		if (!this.transportes.containsKey(idTransporte))
			throw new RuntimeException ("El transporte no se encuentra en la empresa");
		
		return this.transportes.get(idTransporte).getDestino();
		
	}

	//Dado un transporte, le asigna la funcion de cargar paquetes correspondientes
	public double cargarTransporte (String idTransporte) throws Exception {
		
		double cargado = 0;
		
		//Verifica si el transporte se encuentra en la empresa
		if (this.transportes.containsKey(idTransporte)) {
			Transporte transporte = this.transportes.get(idTransporte);
			String destino = transporte.getDestino().getUbicacion();

			//Recorre todos los depositos para cargar los paquetes
			for (Deposito deposito: this.depositos) {
				deposito.cargarTransporte(transporte, destino);
			}
			cargado = transporte.volumenCargado();
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
	public double obtenerCostoViaje (String idTransporte) throws Exception {
		if (transportes.containsKey (idTransporte))
			return this.transportes.get(idTransporte).darCostoTotal();
		
		throw new RuntimeException ("El transporte no se encuentra en la empresa");
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

	public String intercambiarTransporte (String idTransporte) throws Exception {
		
		if (this.transportes.containsKey(idTransporte)) {
			
			Transporte transporte = this.transportes.get(idTransporte);
			
			//Recorre todos los transportes
			for (String id: this.transportes.keySet()) {
				
				//Si el transporte es optimo para el reemplazo, lo realiza
				Transporte t = this.transportes.get(id);
				if (transporte.reemplazablePor(t)) {
					transporte.reemplazarPor(t);
					return t.getId();
				}
			}
		}
		//Si no encuentra un Transporte con las caracteristicas necesarias
		return null;
	}
	
	public String toStringTransportesEnviaje () {
		StringBuilder str = new StringBuilder ();
		ArrayList <Transporte> fletes = new ArrayList<>();
		ArrayList <Transporte> trailers = new ArrayList<>();
		ArrayList <Transporte> megaTrailers = new ArrayList<>();
		
		Iterator<String> it = this.transportes.keySet().iterator();
		while (it.hasNext()) {
			Transporte t = (Transporte) this.transportes.get(it.next());
			if (t instanceof Flete && t.isEnViaje())
				fletes.add(t);
			
			if (t instanceof CamionTrailer && t.isEnViaje())
				trailers.add(t);
			
			if (t instanceof CamionMegaTrailer && t.isEnViaje())
				megaTrailers.add(t);
		}
		
		str.append("Transportes en viaje: ");
		int transporteEnViaje = fletes.size() + trailers.size() + megaTrailers.size();
		str.append(transporteEnViaje);
		str.append("\n");
		if (transporteEnViaje > 0){
			str.append("Fletes: ");
			str.append(fletes.size());
			str.append("\n");
			str.append(Transporte.transportesList(fletes));
			str.append("\n");
			
			str.append("Camiones Trailers: ");
			str.append(trailers.size());
			str.append("\n");
			str.append(Transporte.transportesList(trailers));
			str.append("\n");
			
			str.append("Camiones mega Trailers: ");
			str.append(megaTrailers.size());
			str.append("\n");
			str.append(Transporte.transportesList(megaTrailers));
		}
		return str.toString();
	}

	public String toStringPaquetesFrios () {
		int paquetesPropios = 0;
		int paquetesTercerizados = 0;
		for (Deposito deposito: this.depositos) {
			if (deposito.isRefrigeracion()) {
				if (deposito instanceof DepositoTercerizado)
					paquetesTercerizados += deposito.cantidadPaquetesFrios();
				else
					paquetesPropios += deposito.cantidadPaquetesFrios();
			}
		}
		
		StringBuilder str = new StringBuilder ();
		str.append("Paquetes que necesitan frio: ");
		int paquetesFrios = paquetesPropios + paquetesTercerizados;
		str.append(paquetesFrios);
		str.append("\n");
		str.append("Paquetes en depositos propios: ");
		str.append(paquetesPropios);
		str.append("\n");
		str.append("Paquetes en depositos Tercerizados: ");
		str.append(paquetesTercerizados);
		return str.toString();
	}

	public String toStringEnViajeYFrios () {
		StringBuilder str = new StringBuilder();
		str.append(this.toStringTransportesEnviaje());
		str.append("\n");
		str.append("\n");
		str.append(this.toStringPaquetesFrios());
		
		return str.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder ("Nombre: ");
		str.append (this.nombre);
		str.append ("\n");
		str.append ("Cuit: ");
		str.append (this.cuit);
		str.append ("\n");
		str.append ("\n");
		str.append ("Depositos: ");
		str.append(this.depositos.size());
		str.append ("\n");
		//Impresion de depositos
		for (Deposito e: depositos) {
			String tipo;
			
			if (e instanceof DepositoTercerizado)
				tipo = "Deposito Tercerizado";
			else
				tipo = "Deposito propio";
			
			str.append ("Depósito ");
			str.append(e.getId());
			str.append("  ");
			str.append(tipo);
			if (e.isRefrigeracion())
				str.append("  Con refrigeración");
			else
				str.append("  Sin refrigeración");
			str.append("  Capacidad libre: ");
			str.append(e.getCapacidadLibre());
			str.append(" L");
			str.append ("\n");
		}
		str.append ("\n");
		str.append ("\n");
		
		//Impresion de transportes
		str.append ("Transportes: ");
		str.append(this.transportes.size());
		str.append ("\n");
		Iterator<String> it = this.transportes.keySet().iterator();
		while (it.hasNext()) {
			Transporte transporte = this.transportes.get(it.next());
			//Paso la clase del transporte a cadena
			String tipo = String.valueOf(transporte.getClass());
			//Eliminacion de la palabra class
			tipo = tipo.substring(6, tipo.length());
			str.append ("Vehiculo ");
			str.append(transporte.getId());
			str.append(" " );
			str.append(tipo);
			str.append("\n");
		}
		
		str.append("\n");
		str.append(toStringEnViajeYFrios());
		str.append("\n");
		return str.toString();
	}
	
}


