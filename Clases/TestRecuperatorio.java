import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestRecuperatorio {

	Empresa e;
	
	@Before
	public void setUp() throws Exception {
		e = new Empresa("23011222334","La Santafesina");
		e.agregarDestino("Rosario", 100);
		e.agregarDestino("Buenos Aires", 501);
		e.agregarDestino("Mar del Plata", 800);
		e.agregarDestino("Brasil", 800);
		
		e.agregarDeposito(1000, true, true);
		e.agregarDepTercerizFrio(200000, 311);
		
		e.incorporarPaquete("Buenos Aires", 10, 1, false);
		e.incorporarPaquete("Buenos Aires", 10, 1, false);
		e.incorporarPaquete("Buenos Aires", 10, 1, false);
		e.incorporarPaquete("Brasil", 10, 1, false);
		e.incorporarPaquete("Brasil", 10, 1, false);
		e.incorporarPaquete("Rosario", 10, 1, false);
	}
	
	
	@Test
	public void intercambiarTransporteValido () throws Exception {
		int paquetesTransporteAveriado;
		int paquetesTransporteNuevo;
		e.agregarFlete("EWR123", 4000, 350, 10, 1, 800);
		e.agregarFlete("ASD123", 4000, 350, 10, 1, 800);
		
		e.asignarDestino("ASD123", "Buenos Aires");
		e.cargarTransporte("ASD123");
		e.iniciarViaje("ASD123");
		
		e.intercambiarTransporte("ASD123");
		paquetesTransporteAveriado = e.transportes.get("ASD123").getPaquetes().size();
		paquetesTransporteNuevo = e.transportes.get("EWR123").getPaquetes().size();
		
		
		assertTrue (e.transportes.get("ASD123").getDestino() == null);
		assertTrue (e.transportes.get("EWR123").getDestino() != null);
		assertEquals (paquetesTransporteAveriado,0,0);
		assertEquals (paquetesTransporteNuevo, 3, 0);	
	}
	
	@Test
	public void intercambiarTransporteCapacidadMenor () throws Exception {
		int paquetesTransporteAveriado;
		int paquetesTransporteNuevo;
		e.agregarFlete("EWR123", 4000, 300, 10, 1, 800);
		e.agregarFlete("ASD123", 4000, 350, 10, 1, 800);
		
		e.asignarDestino("ASD123", "Buenos Aires");
		e.cargarTransporte("ASD123");
		e.iniciarViaje("ASD123");
		
		e.intercambiarTransporte("ASD123");
		paquetesTransporteAveriado = e.transportes.get("ASD123").getPaquetes().size();
		paquetesTransporteNuevo = e.transportes.get("EWR123").getPaquetes().size();
		
		assertTrue (e.transportes.get("ASD123").getDestino() != null);
		assertTrue (e.transportes.get("EWR123").getDestino() == null);
		assertEquals (paquetesTransporteAveriado,3,0);
		assertEquals (paquetesTransporteNuevo, 0, 0);	
	}
	
	@Test
	public void intercambiarTransporteDistintosTipo () throws Exception {
		int paquetesTransporteAveriado;
		int paquetesTransporteNuevo;
		e.agregarMegaTrailer("QWE123", 200, 200, false, 15, 12, 10, 1233);
		e.agregarFlete("ASD123", 4000, 350, 10, 1, 800);
		
		e.asignarDestino("ASD123", "Buenos Aires");
		e.cargarTransporte("ASD123");
		e.iniciarViaje("ASD123");
		
		e.intercambiarTransporte("ASD123");
		paquetesTransporteAveriado = e.transportes.get("ASD123").getPaquetes().size();
		paquetesTransporteNuevo = e.transportes.get("QWE123").getPaquetes().size();
		
		assertTrue (e.transportes.get("ASD123").getDestino() != null);
		assertTrue (e.transportes.get("QWE123").getDestino() == null);
		assertEquals (paquetesTransporteAveriado,3,0);
		assertEquals (paquetesTransporteNuevo, 0, 0);	
	}


	@Test
	public void imprimirTransportesYPaquetes() throws Exception {
		e.agregarMegaTrailer("ASD123", 200, 200, false, 15, 12, 10, 1233);
		e.agregarFlete("EWR123", 4000, 350, 10, 1, 800);
		e.agregarTrailer("JBY231", 5000, 2000, false, 22, 300);
		
		e.asignarDestino("ASD123", "Buenos Aires");
		e.asignarDestino("EWR123", "Brasil");
		
		e.cargarTransporte("EWR123");
		e.cargarTransporte("ASD123");
		
		e.iniciarViaje("EWR123");
		e.iniciarViaje("ASD123");
		
		
		System.out.print(e.enViajeYFrios());
	}
}
