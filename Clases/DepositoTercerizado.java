public class DepositoTercerizado extends Deposito{
	
	private double precioTonelada; 

	//El precio por tonelada debe ser mayor a cero
	
	public DepositoTercerizado(boolean refrigeracion, 
			double capacidadMaxima, double precioTonelada) throws Exception {
		super(refrigeracion, capacidadMaxima);
		
		if (precioTonelada > 0 || !refrigeracion)
			this.precioTonelada = precioTonelada;
		
		if (precioTonelada <= 0 && refrigeracion)
			throw new Exception ("El precio por tonelada debe ser mayor a cero");
	}

	public double getPrecioTonelada() {
		return precioTonelada;
	}
}
