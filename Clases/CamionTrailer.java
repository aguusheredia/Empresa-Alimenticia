public class CamionTrailer extends Transporte  {

	private double seguroDeCarga;
	
	//El seguro de carga debe ser mayor a cero
	
	public CamionTrailer (String id,double cargaMaxima, double capacidadMaxima, double costoKm, boolean refrigeracion,
			double seguroDeCarga) throws Exception{
		
		super (id,cargaMaxima, capacidadMaxima, costoKm, refrigeracion);
		
		if (seguroDeCarga > 0)
			this.seguroDeCarga = seguroDeCarga;
		
		//While para cumplir el IREP
		if (seguroDeCarga <= 0) 
			throw new Exception ("El seguro de carga debe ser mayor a cero");
	}
	
	
	public void calcularCostoTotal() {
		
		if (this.destino != null)
			this.costoTotal = costoKms() + this.seguroDeCarga;
		else 
			System.out.print("El flete no tiene un destino asignado");
	}
}
