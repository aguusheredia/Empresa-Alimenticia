public class DepositoTercerizado extends Deposito{
	
	private double precioTonelada; 

	//El precio por tonelada debe ser mayor a cero
	
	public DepositoTercerizado(boolean refrigeracion, 
			double capacidadMaxima, int id, double precioTonelada) throws Exception {
		super(refrigeracion, capacidadMaxima, id);
		
		if (precioTonelada <= 0 && refrigeracion)
			throw new Exception ("El precio por tonelada debe ser mayor a cero");
		
		this.precioTonelada = precioTonelada;
	}

	public double getPrecioTonelada() {
		return precioTonelada;
	}
}
