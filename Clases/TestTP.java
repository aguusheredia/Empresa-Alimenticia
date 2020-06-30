import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestTP {
	Empresa emp;
	double carga, ctoViaje;

	@Before
	public void setUp() throws Exception {
		emp = new Empresa("30112223334","La Santafesina");
		emp.agregarDestino("Cordoba", 350);
		emp.agregarDestino("Corrientes", 900);
		emp.agregarDestino("Parana", 30);
	}

	@Test
	public void testAgregarDeposito() throws Exception {
		int dep1, dep2;
		dep1 = emp.agregarDeposito(30000, false, true);
		dep2 = emp.agregarDeposito(40000, true, true);
		// Los numeros de depositos son unicos
		assertNotEquals(dep1,dep2);
	}

	@Test
	public void testIncorporarPaqueteSinLugar() throws Exception {
		emp.agregarDeposito(30000, false, true);
		// No hay deposito para un paquete que necesita frio
		assertFalse(emp.incorporarPaquete("Cordoba", 100, 5, true));
	}

	@Test
	public void testIncorporarPaqueteConLugar() throws Exception {
		emp.agregarDeposito(30000, true, true);
		// El paquete debe haberse incorporado al deposito
		assertTrue(emp.incorporarPaquete("Cordoba", 100, 5, true));
	}

	@Test
	public void testTrailerFrio() throws Exception {
		emp.agregarDeposito(30000, false, false);
		emp.agregarDepTercerizFrio(80000, 50);
		emp.agregarTrailer("AC314PI", 12000, 60, true, 5, 100);
		emp.asignarDestino("AC314PI", "Cordoba");
		emp.incorporarPaquete("Cordoba", 100, 5, true);
		emp.incorporarPaquete("Cordoba", 250, 10, true);
		emp.incorporarPaquete("Cordoba", 150, 8, true);
		emp.incorporarPaquete("Cordoba", 50, 2.5, false);
		emp.incorporarPaquete("Cordoba", 300, 15, true);
		emp.incorporarPaquete("Cordoba", 400, 12, true);
		emp.incorporarPaquete("Cordoba", 125, 5, false);
		carga = emp.cargarTransporte("AC314PI");
		assertEquals(carga,50.0,1.0);
		emp.iniciarViaje("AC314PI");
		ctoViaje = emp.obtenerCostoViaje("AC314PI");
		assertEquals(ctoViaje,1910.0,10.0);
	}

	@Test
	public void testMegaTrailer() throws Exception {
		emp.agregarDeposito(30000, false, false);
		emp.agregarDeposito(30000, true, true);
		emp.agregarMegaTrailer("AD161AU", 18000, 120, false, 10, 150, 500, 300);
		emp.asignarDestino("AD161AU", "Corrientes");
		emp.incorporarPaquete("Corrientes", 100, 5, true);
		emp.incorporarPaquete("Corrientes", 400, 12, true);
		emp.incorporarPaquete("Corrientes", 50, 2.5, false);
		emp.incorporarPaquete("Corrientes", 125, 5, false);
		emp.incorporarPaquete("Corrientes", 75, 4, false);
		emp.incorporarPaquete("Corrientes", 150, 7.5, false);
		emp.incorporarPaquete("Corrientes", 200, 6, false);
		carga = emp.cargarTransporte("AD161AU");
		assertEquals(carga,25.0,0.5);
		emp.iniciarViaje("AD161AU");
		ctoViaje = emp.obtenerCostoViaje("AD161AU");
		assertEquals(ctoViaje,9950.0,10.0);
	}

	@Test
	public void testTransIguales() throws Exception {
		emp.agregarDeposito(40000, false, false);

		emp.agregarFlete("AB271NE", 8000, 40, 3, 2, 200);
		emp.asignarDestino("AB271NE", "Parana");
		emp.incorporarPaquete("Parana", 100, 5, false);
		emp.incorporarPaquete("Parana", 400, 12, false);
		emp.incorporarPaquete("Parana", 50, 8, false);
		emp.cargarTransporte("AB271NE");

		emp.agregarFlete("AD235NP", 6000, 30, 2, 1, 100);
		emp.asignarDestino("AD235NP", "Parana");
		emp.incorporarPaquete("Parana", 400, 12, false);
		emp.incorporarPaquete("Parana", 50, 8, false);
		emp.incorporarPaquete("Parana", 100, 5, false);
		emp.cargarTransporte("AD235NP");
		
		assertEquals(emp.obtenerTransporteIgual("AB271NE"),"AD235NP");
	}

}