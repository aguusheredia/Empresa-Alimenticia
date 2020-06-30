import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestEnunciado {

	double cargado;
	Empresa e;
	
	@Before
	public void setUp() throws Exception {
		e = new Empresa("23011222334","La Santafesina");
		e.agregarDestino("Rosario", 100);
		e.agregarDestino("Buenos Aires", 400);
		e.agregarDestino("Mar del Plata", 800);
	}
	
	@Test
	public void agregarDeposito() throws Exception {
		e.agregarDepTercerizFrio(40000,10);
		e.agregarDeposito(50000,true,true);
		e.agregarDeposito(80000,false,true);
		e.agregarDeposito (90000, false, false);
	}
	
	@Test
	public void agregarTransporte() throws Exception {
		e.agregarTrailer ("AA333XQ", 10000, 60, true, 2, 100);
		e.agregarMegaTrailer("AA444PR", 15000, 100, false, 3, 150, 200, 50);
		e.agregarFlete ("AB555MN", 5000, 20, 4, 2, 300);
	}
	
	@Test
	public void asignarDestino () throws Exception {
		e.asignarDestino("AA333XQ", "Buenos Aires");
		e.asignarDestino("AB555MN", "Rosario");
	}
	
	@Test
	public void paquetesFrio () throws Exception {
		e.agregarDepTercerizFrio(40000,10);
		e.agregarDeposito(50000,true,true);
		e.incorporarPaquete("Buenos Aires", 100, 2, true);
		e.incorporarPaquete("Buenos Aires", 150, 1, true);
		e.incorporarPaquete("Mar del Plata", 100, 2, true);
		e.incorporarPaquete("Mar del Plata", 150, 1, true);
		e.incorporarPaquete("Rosario", 100, 2, true);
		e.incorporarPaquete("Rosario", 150, 1, true);
	}
	
	@Test
	public void paquetesSinFrio () throws Exception {
		e.agregarDepTercerizFrio(40000,10);
		e.agregarDeposito(50000,true,true);
		e.incorporarPaquete("Buenos Aires", 200, 3, false);
		e.incorporarPaquete("Buenos Aires", 400, 4, false);
		e.incorporarPaquete("Mar del Plata", 200, 3, false);
		e.incorporarPaquete("Rosario", 80, 2, false);
		e.incorporarPaquete("Rosario", 250, 2, false);
	}
	
	@Test
	public void carga () throws Exception {
		e.agregarDeposito(50000,true,true);
		e.agregarTrailer ("AA333XQ", 10000, 60, false, 2, 100);
		e.asignarDestino("AA333XQ","Buenos Aires");
		e.incorporarPaquete("Buenos Aires", 200, 3, false);
		e.incorporarPaquete("Buenos Aires", 400, 4, false);
		e.incorporarPaquete("Mar del Plata", 200, 3, false);
		cargado = e.cargarTransporte("AA333XQ");
		assertEquals (cargado,7, 1.0);
	}

}
