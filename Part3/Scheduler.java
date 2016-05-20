package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * A class of schedulers.
 *
 * @author  Bart Jacobs and Jordy Heusdens
 * @version 1.0
 */
public class Scheduler {
	
	/**
	 * List that keeps track of all the tasks in the schedule.
	 */
	public List<Task> tasks = new ArrayList<>();
	
	/**
	 * Adds a task to the schedule.
	 * 
	 * @param task
	 * 		The task we want to add to the schedule.
	 * @post
	 * 		|tasks.contains(task)
	 */
	public void addTask(Task task){
		tasks.add(task);
	}
	
	/**
	 * Removes a given task from the schedule
	 * 
	 * @param task
	 * 		The task we want to remove from the schedule.
	 * @post
	 * 		|!tasks.contains(task)
	 */
	public void removeTask(Task task){
		tasks.remove(task);
	}
	
	/**
	 * Replaces a task in the schedule
	 * 
	 * @param orignal
	 * 		The task we want to remove from the schedule.
	 * @param replacement
	 * 		The task we want to add to the schedule on the location of the orignal.
	 * @post
	 * 		|tasks.get(i) == replacement
	 */
	public void replaceTask(List<Task> list,Task orignal, Task replacement){
		int i = list.indexOf(orignal);
		list.set(i, replacement);
	}
	
	/**
	 * Check if the collection of tasks is part of the schedule.
	 * 
	 * @param task
	 * 		The collection of tasks we want to check.
	 * @return A boolean that determines whether these tasks are in the scheduler or not.
	 * 		| result == tasks.containsAll(task)
	 */
	public boolean areTasksPartOf(Collection<Task> task){
		return tasks.containsAll(task);
	}
	
	/**
	 * Create an iterator for the sorted version of the schedule.
	 * 
	 * @return An iterator for the sorted array.
	 * 		|List<Task> copy = sort()
	 * 		|result == copy.iterator()
	 */
	public Iterator<Task> getAllTasksIterator(){
		List<Task> copy = sort();
		return copy.iterator();
	}
	
	private boolean more(int v, int w){
		return v > w;
	}
	
	/**
	 * Sorts a list using insertion sort.
	 * 
	 * @return A sorted copy of the list.
	 * 		|result == clone.sort()
	 */
	public List<Task> sort(){
		List<Task> clone = cloneList(tasks);
		int n = tasks.size();
		for(int i = 1; i < n; i++){
			for(int j = i; j > 0 && more(clone.get(j).getPriority(),clone.get(j-1).getPriority()); j--){
				exch(clone,j,j-1);
			}
		}
		for (int i = 0; i < clone.size() ; i++) {
			tasks.set(i, clone.get(i));
		}
		return clone;
	}
	
	
	
	private void exch(List<Task> list, int i, int j){
		Task temp = list.get(i);
		replaceTask(list,list.get(i), list.get(j));
		replaceTask(list,list.get(j), temp);
	}
	
	/**
	 * Clones a list.
	 * 
	 * @param list
	 * 		The list we want to clone.
	 * @return The clone of the list.
	 * 		|result == list.clone()
	 */
	public List<Task> cloneList(List<Task> list) {
	    List<Task> clone = new ArrayList<Task>(list.size());
	    for(Task item: list){
	    	clone.add(item);
	    }
	    return clone;
	}
	
	private Faction faction;

	/**
	 * Request this scheduler's faction.
	 * 
	 * @return This scheduler's faction.
	 * 		|result == faction
	 */
	public Faction getFaction() {
		return faction;
	}

	/**
	 * Set the faction to this schedule.
	 * 
	 * @param faction
	 *		The faction we want to assign to this scheduler.
	 * @post
	 * 		new.getFaction() == faction
	 */
	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	
}
