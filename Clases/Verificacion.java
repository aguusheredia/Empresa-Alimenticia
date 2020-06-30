
public class Verificacion {

	public static boolean verificarCuit (String cuit) {
		
		if (cuit.length() == 11) {
			for (int i = 0 ; i < cuit.length(); i++) {
				if (!Character.isDigit(cuit.charAt(i)))
						return false;
			}
			return true;
		}
		return false;
	}

	public static boolean verificarPatente (String patente) {
		
		if (patente.length() == 6) {
			for (int i = 0; i < 3; i++) {
				if (!Character.isLetter(patente.charAt(i)))
					return false;
			}
			for (int j = 3; j < patente.length(); j++) {
				if (!Character.isDigit(patente.charAt(j)))
					return false;
			}
			
			return true;
		}
		
		if (patente.length() == 7) {
			for (int i = 0; i < 2; i++) {
				if (!Character.isLetter(patente.charAt(i)))
					return false;
			}
			for (int j = 2; j < 5; j++) {
				if (!Character.isDigit(patente.charAt(j)))
					return false;
			}
			for (int k = 5; k < patente.length(); k++) {
				if (!Character.isLetter(patente.charAt(k)))
					return false;
			}
			
			return true;
		}
		
		return false;
		
	}
	
}
