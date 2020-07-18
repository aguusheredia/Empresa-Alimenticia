public class DepositoTercerizado extends Deposito{
	
	private double precioTonelada; 

	//El precio por tonelada debe ser mayor a cero
	
	public DepositoTercerizado(boolean refrigeracion, 
			double capacidadMaxima, int id, double precioTonelada) throws Exception {
		super(refrigeracion, capacidadMaxima, id);
		
		if (precioTonelada <= 0 && refrigeracion)
			throw new RuntimeException ("El precio por tonelada debe ser mayor a cero");
		
		if (refrigeracion)
			this.precioTonelada = precioTonelada;
	}
	
	@Override
	public boolean cargarPaqueteATransporte (Transporte transporte, Paquete paquete) throws Exception {
		if (super.cargarPaqueteATransporte(transporte, paquete)){
			if (paquete != null && paquete.isFrio()){
				double costoExtra = (paquete.getPeso() * this.precioTonelada) / 1000;
				transporte.sumarCostosExtras(costoExtra);
			}
			return true;
		}
		
		return false;
	}
}
