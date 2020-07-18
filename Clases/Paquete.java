public class Paquete {

	private String destino;
	private double volumen;
	private double peso;
	private boolean frio;

	//El destino debe ser una cadena no vacia
	//El peso y el volumen deben ser mayor a cero
	
	public Paquete(String destino, double volumen, double peso, boolean frio) throws Exception {
		
		//Excepsiones para cumplir el IREP
		if (destino.length() == 0) 
			throw new RuntimeException ("Debe asignar un destino");
		
		if (peso <= 0) 
			throw new RuntimeException ("El peso debe ser mayor a cero");
		
		if (volumen <= 0) 
			throw new RuntimeException ("El volumen debe ser mayor a cero");
		
		this.frio = frio;
		this.destino = destino;
		this.volumen = volumen;
		this.peso = peso;
	}


	public String getDestino() {
		return destino;
	}

	public double getVolumen() {
		return volumen;
	}

	public double getPeso() {
		return peso;
	}

	public boolean isFrio() {
		return frio;
	}

	@Override
	public boolean equals (Object obj) {
		
		if (obj instanceof Paquete) {
			Paquete p = (Paquete) obj;
			if (this.destino.equals (p.getDestino()) && 
					this.volumen == p.getVolumen() &&
					this.peso == p.getPeso() && 
					this.frio == p.isFrio())
				return true;
		}
		return false;
	}
	
	
	@Override
	public String toString () {
		StringBuilder ret = new StringBuilder ("Paquete");
		ret.append ("\n");
		ret.append ("Destino ");
		ret.append(this.destino);
		ret.append("\n");
		ret.append ("Peso ");
		ret.append(this.peso);
		ret.append(" KG  Volumen ");
		ret.append( this.volumen);
		ret.append(" L");
		ret.append ("\n");
		if (isFrio())
			ret.append("Requiere frio");
		else
			ret.append ("No requiere frio");
		
		return ret.toString();


		
	}
}
