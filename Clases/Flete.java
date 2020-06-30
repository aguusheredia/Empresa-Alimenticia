public class Flete extends Transporte {

	private double costoFijoPasajero;
	private int cantidadPasajeros;
	
	//El costo fijo por pasajero debe ser mayor a cero
	
	public Flete (String id,double cargaMaxima, double capacidadMaxima, double costoKm,
					double costoFijoPasajero) throws Exception{
		
		super (id,cargaMaxima, capacidadMaxima, costoKm, false);
			
		if (costoFijoPasajero > 0)
			this.costoFijoPasajero = costoFijoPasajero;
		
		if (costoFijoPasajero <= 0) 
			throw new Exception ("El costo fijo por pasajero debe ser mayor a cero");
	}
	
	public void setCantidadPasajeros (int n){
		if (n > 0)
			this.cantidadPasajeros = n;
		
		else
			System.out.println("La cantidad de pasajero debe ser mayor a cero. No se realizo el cambio");
	}

	public void calcularCostoTotal() {
		
		if (this.destino != null)
			this.costoTotal = costoKms() + this.costoFijoPasajero * this.cantidadPasajeros;
		else 
			System.out.print("El flete no tiene un destino asignado");
	}

	
}
