package hillbillies.tests.faction;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Faction;
import hillbillies.model.Unit;
import hillbillies.part2.facade.Facade;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class FactionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	private Facade facade;
	
	@Before
	public void setUp() throws Exception {
		this.facade = new Facade();
		facade.createWorld(new int[50][50][50], new DefaultTerrainChangeListener());
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void AddToFaction_NewFaction() throws ModelException {
		Unit unit = new Unit("Test", new int[]{1,1,1} , 50, 50, 50, 50, false);
		Faction factionUnit = unit.getFaction();
		assertTrue(factionUnit.getUnitsOfFaction().size() == 1);
		Unit unit_2 = new Unit("Test", new int[]{1,1,1} , 50, 50, 50, 50, false);
		Faction factionUnit_2 = unit_2.getFaction();
		assertTrue(factionUnit_2.getUnitsOfFaction().size() == 1);
		Unit unit_3 = new Unit("Test", new int[]{1,1,1} , 50, 50, 50, 50, false);
		Faction factionUnit_3 = unit_3.getFaction();
		assertTrue(factionUnit_3.getUnitsOfFaction().size() == 1);
		Unit unit_4 = new Unit("Test", new int[]{1,1,1} , 50, 50, 50, 50, false);
		Faction factionUnit_4 = unit_4.getFaction();
		assertTrue(factionUnit_4.getUnitsOfFaction().size() == 1);
		Unit unit_5 = new Unit("Test", new int[]{1,1,1} , 50, 50, 50, 50, false);
		Faction factionUnit_5 = unit_5.getFaction();
		assertTrue(factionUnit_5.getUnitsOfFaction().size() == 1);	
	}
	
	@Test
	public void AddToExistingFaction() throws ModelException{
		
	}

}
