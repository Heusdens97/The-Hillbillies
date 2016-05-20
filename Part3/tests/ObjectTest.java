package hillbillies.test.model.Objects;

import static org.junit.Assert.*;

import java.lang.reflect.Array;
import java.util.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Log;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.facade.IFacade;
import ogp.framework.util.ModelException;

public class objectTest {


	private Facade facade;

	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;

	@Before
	public void setup() {
		this.facade = new Facade();
	}

	@Test
	public void testLog() {
		int[][][] types = new int[3][3][3];
		World world = new World(types, new DefaultTerrainChangeListener());
		int[] pos = {1,1,1};
		Log log = new Log(world, pos);
		double[] compareArray = {1.5,1.5,1.5};
		assertTrue(Arrays.equals(compareArray,log.getPosition()));
		assertTrue(Arrays.equals(pos,log.getCubeCoordinate()));
		assertTrue(log.getWeight() >= 10 && log.getWeight() <= 50);

	}
	@Test
	public void testAdvancetime() throws ModelException {
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_AIR;
		types[1][1][2] = TYPE_AIR;
		World world = new World(types, new DefaultTerrainChangeListener());
		int[] pos = {1,1,2};
		Log log = new Log(world, pos);
		advanceTimeFor(facade, world, 100, 0.02);
		System.out.println(log.getPosition()[2]);
		assertFalse(Arrays.equals(pos, log.getCubeCoordinate()));
		double[] check = {1,1,1.5};
		assertTrue((int)(log.getPosition()[2]) == (int)check[2]);
		
	}
	private static void advanceTimeFor(IFacade facade, World world, double time, double step) throws ModelException {
		int n = (int) (time / step);
		for (int i = 0; i < n; i++)
			facade.advanceTime(world, step);
		facade.advanceTime(world, time - n * step);
	}
}
