package hillbillies.tests.scheduler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.statements.Statement;

public class SchedulerTest {

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
	public void Sorttest() {
		List<Task> test = new ArrayList<Task>();
		Scheduler s = new Scheduler();
		for (int i = 0; i < 10; i++) {
			String name = "test";
			int[] pos = {0,0,0};
			Task t =new Task(name, i, new Statement(), pos);
			test.add(t);
			s.AddTask(t);
		}
		print(s.tasks);
		List<Task> t = s.sort();
		System.out.println("\n");
		print(t);
	}
	
	public void print(List<Task> list){
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getPriority());
		}
	}

}
