public class CamionTrailer extends Transporte  {

	private double seguroDeCarga;
	
	//El seguro de carga debe ser mayor a cero
	
	public CamionTrailer (String id,double cargaMaxima, double capacidadMaxima, double costoKm, boolean refrigeracion,
			double seguroDeCarga) throws Exception{
		
		super (id,cargaMaxima, capacidadMaxima, costoKm, refrigeracion);
		
		//Excepcion para cumplir el IREP
		if (seguroDeCarga <= 0) 
			throw new RuntimeException ("El seguro de carga debe ser mayor a cero");
		
		this.seguroDeCarga = seguroDeCarga;
	}
	
	@Override
	public void asignarDestino (Destino destino) throws Exception {
		if (destino.getDistancia() > 500)
			throw new RuntimeException ("Los camiones trailer solo hacer viajes menores a 500 KM");
		
		super.asignarDestino(destino);
	}
	
	@Override
	public double calcularCosto() throws Exception {
		
			double cargado = super.calcularCosto() + this.seguroDeCarga;
			return cargado;
	}
}
