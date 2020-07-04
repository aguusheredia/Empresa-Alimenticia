public class Destino {

	private String ubicacion;
	private int distancia;
	
	//El destino debe ser una cadena no vacia
	//La distancia debe ser mayor a cero
	
	public Destino (String ubicacion, int distancia) throws Exception{
		
		//Excepciones para cumplir el IREP
		if (ubicacion == null)
			throw new Exception ("El destino debe tener una ubicación");
		
		if (ubicacion.length() == 0) 
			throw new Exception ("El destino no debe ser vacio");
		
		if (distancia <= 0)
			throw new Exception ("La distancia debe ser mayor a cero");
		
		this.ubicacion = ubicacion;
		this.distancia = distancia;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public int getDistancia() {
		return distancia;
	}
	
	@Override
	public boolean equals (Object obj) {
		if (obj == null)
			return false;
		
		if (obj instanceof Destino) {
			Destino d = (Destino) obj;
			if (this.ubicacion.equals(d.ubicacion) && this.distancia == d.distancia)
				return true;
		}
		return false;
	}
	
	@Override
	public String toString () {
		return "Destino: " + this.ubicacion + "\n" + "Km: " + this.distancia;
	}
	
	
}
