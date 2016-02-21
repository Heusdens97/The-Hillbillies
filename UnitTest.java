package hillbillies.model.unit;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Unit;
import ogp.framework.util.ModelException;

public class UnitTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void constructor_LegalName() throws ModelException {
		int[] row = {1,2,3};
		Unit name = new Unit("James O'Hara",row);
		assertEquals("James O'Hara",name.getName());
	}
	
	@Test(expected = ModelException.class)
	public void constructor_IllegalName() throws ModelException {
		int[] row = {1,2,3};
		new Unit("B@rt",row);
	}
	
	@Test
	public void constructor_LegalPosition() throws ModelException {
		int[] row_0 = {0,0,0};
		Unit position_0 = new Unit("Jordy",row_0);
		assertEquals(row_0,position_0.getPosition());
		int n = Unit.getMaxSize()-1;
		int[] row_49 = {n,n,n};
		Unit position_49 = new Unit("Jordy",row_49);
		assertEquals(row_49,position_49.getPosition());
	}
	
	@Test (expected = ModelException.class)
	public void constructor_IllegalPosition() throws ModelException{
	  int[] row_50 = {Unit.getMaxSize(), Unit.getMaxSize(), Unit.getMaxSize()};
	  new Unit("Bart", row_50);
	}
}