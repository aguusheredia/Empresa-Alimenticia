public class CamionMegaTrailer extends Transporte  {

	private double seguroDeCarga;
	private double costoFijo;
	private double gastoComida;
	
	//El seguro de carga, el costo fijo y gasto de comida deben ser mayor a cero
	
	public CamionMegaTrailer (String id, double cargaMaxima, double capacidadMaxima, double costoKm, boolean refrigeracion,
			double seguroDeCarga, double costoFijo, double gastoComida) throws Exception{
		
		super (id,cargaMaxima, capacidadMaxima, costoKm, refrigeracion);
		
		if (seguroDeCarga > 0 && costoFijo > 0 && gastoComida >0) {
			this.seguroDeCarga = seguroDeCarga;
			this.costoFijo = costoFijo;
			this.gastoComida = gastoComida;
		}
		
		//Excepciones para cumplir el IREP
		if (seguroDeCarga <= 0)
			throw new Exception ("El seguro de carga debe ser mayor a cero");
		
		if (seguroDeCarga <= 0) 
			throw new Exception ("El costo Fijo debe ser mayor a cero");
		
		if (seguroDeCarga <= 0) 
			throw new Exception ("El gasto de comida debe ser mayor a cero");
		
	}
	
	public void calcularCostoTotal() {
		
		if (getDestino() != null)
			setCostoTotal (costoKms() + this.seguroDeCarga + this.costoFijo + this.gastoComida);
		else 
			System.out.print("El flete no tiene un destino asignado");
	}

	
}
