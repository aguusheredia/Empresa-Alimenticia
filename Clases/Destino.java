public class Destino {

	private int id;
	private String ubicacion;
	private int distancia;
	
	//El destino debe ser una cadena no vacia
	//La distancia debe ser mayor a cero
	
	public Destino (String ubicacion, int distancia) throws Exception{
		
		if (ubicacion.length() > 0 && distancia >0) {
			this.ubicacion = ubicacion;
			this.distancia = distancia;
		}
		
		//Excepciones para cumplir el IREP
		if (ubicacion.length() == 0) 
			throw new Exception ("El destino no debe ser vacio");
		
		if (distancia <= 0)
			throw new Exception ("La distancia debe ser mayor a cero");
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public int getDistancia() {
		return distancia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString () {
		StringBuilder ret = new StringBuilder ("Destino: ");
		ret.append(this.ubicacion + ("\n"));
		ret.append("Km: " + this.distancia);
		return ret.toString();
	}
	
	
}
