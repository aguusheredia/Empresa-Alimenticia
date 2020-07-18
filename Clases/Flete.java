public class Flete extends Transporte {

	private double costoFijoPasajero;
	private int cantidadPasajeros;
	
	//El costo fijo por pasajero debe ser mayor a cero
	
	public Flete (String id,double cargaMaxima, double capacidadMaxima, double costoKm, int cantPasajeros,
					double costoFijoPasajero) throws Exception{
		
		super (id,cargaMaxima, capacidadMaxima, costoKm, false);
			
		//Excepciones para controlar el IREP
		if (costoFijoPasajero <= 0) 
			throw new RuntimeException ("El costo fijo por pasajero debe ser mayor a cero");
		
		if (cantPasajeros <= 0) 
			throw new RuntimeException ("La cantidad de pasajeros debe ser mayor a cero");
		
		this.costoFijoPasajero = costoFijoPasajero;
		this.cantidadPasajeros = cantPasajeros;
	}

	@Override
	public double calcularCosto() throws Exception {
		
		double cargado = super.calcularCosto() + (this.costoFijoPasajero * this.cantidadPasajeros);
		return cargado;
	}

	
}
