
public class Verificacion {

	public static boolean verificarCuit (String cuit) {
		
		return cuit.matches("^[0-9]{11}$");
	}

	public static boolean verificarPatente (String patente) {
		
		if (patente.matches("^[A-Z]{3}[0-9]{3}$"))
				return true;
		
		if (patente.matches("^[A-Z]{2}[0-9]{3}[A-Z]{2}$"))
			return true;
		
		return false;
		
	}
	
}
