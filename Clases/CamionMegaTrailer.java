public class CamionMegaTrailer extends Transporte  {

	private double seguroDeCarga;
	private double costoFijo;
	private double gastoComida;
	
	//El seguro de carga, el costo fijo y gasto de comida deben ser mayor a cero
	
	public CamionMegaTrailer (String id, double cargaMaxima, double capacidadMaxima, double costoKm, boolean refrigeracion,
			double seguroDeCarga, double costoFijo, double gastoComida) throws Exception{
		
		super (id,cargaMaxima, capacidadMaxima, costoKm, refrigeracion);

		//Excepciones para cumplir el IREP
		if (seguroDeCarga <= 0)
			throw new RuntimeException ("El seguro de carga debe ser mayor a cero");
		
		if (seguroDeCarga <= 0) 
			throw new RuntimeException ("El costo Fijo debe ser mayor a cero");
		
		if (seguroDeCarga <= 0) 
			throw new RuntimeException ("El gasto de comida debe ser mayor a cero");
		
			this.seguroDeCarga = seguroDeCarga;
			this.costoFijo = costoFijo;
			this.gastoComida = gastoComida;
		}
	
	@Override
	public void asignarDestino (Destino destino) throws Exception {
		if (destino.getDistancia() <= 500)
			throw new RuntimeException ("Los camiones mega trailer solo hacer viajes mayores a 500 KM");
		
		super.asignarDestino(destino);
	}
	
	@Override
	public double calcularCosto() throws Exception {
		
		double cargado = super.calcularCosto() + this.seguroDeCarga + this.costoFijo + this.gastoComida;
		return cargado;
	}

	
}
