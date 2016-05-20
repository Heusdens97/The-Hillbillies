package hillbillies.tests.scheduler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hillbillies.model.Scheduler;
import hillbillies.model.Task;

public class schedulerTest {


	@Test
	public void testSchedule() {
		Task task = new Task("name", 8, null, null);
		Task task2 = new Task("name2", 6, null, null);
		Scheduler schedule = new Scheduler();
		schedule.addTask(task);
		assertTrue(schedule.tasks.size() == 1);
		
		schedule.replaceTask(schedule.tasks, task, task2);
		assertTrue(schedule.tasks.get(0) == task2);
		
		schedule.addTask(task);
		List <Task> taskss = new ArrayList<>();
		taskss.add(task2);taskss.add(task);
		assertTrue(schedule.areTasksPartOf(taskss));
		
		schedule.sort();
		assertTrue(schedule.tasks.get(0) == task);
	}

}
