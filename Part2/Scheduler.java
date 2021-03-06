package hillbillies.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class Scheduler {

	public Scheduler(){
		
	}
	
	public List<Task> tasks = new ArrayList<>();
	
	public void AddTask(Task task){
		tasks.add(task);
	}
	
	private void RemoveTask(Task task){
		tasks.remove(task);
	}
	
	public void replaceTask(Task orignal, Task replacement){
		int i = tasks.indexOf(orignal);
		tasks.set(i, replacement);
	}
	
	public boolean areTasksPartOf(Collection<Task> task){
		return tasks.containsAll(task);
	}
	
	public Iterator<Task> getAllTasksIterator(){
		List<Task> copy = sort();
		return copy.iterator();
	}
	
	public List<Task> sort(){
		List<Task> clone = cloneList(tasks);
		int n = tasks.size();
		for(int i = 0; i < n; i++){
			int max = tasks.get(i).getPriority();
			for(int j = i+1; j < n; j++){
				if(tasks.get(j).getPriority()> max)
					max = tasks.get(j).getPriority();
				exch(i,max);
			}
		}
		for (int i = 0; i < clone.size() ; i++) {
			tasks.set(i, clone.get(i));
		}
		return clone;
		//nakijken
	}
	
	private void exch(int i, int j){
		Task temp = tasks.get(i);
		replaceTask(tasks.get(i), tasks.get(j));
		replaceTask(tasks.get(j), temp);
	}
	
	public List<Task> cloneList(List<Task> list) {
	    List<Task> clone = new ArrayList<Task>(list.size());
	    for(Task item: list){
	    	clone.add(item);
	    }
	    return clone;
	}
	
	
	
	
}
