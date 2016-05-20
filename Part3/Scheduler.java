package hillbillies.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


public class Scheduler {
	
	public List<Task> tasks = new ArrayList<>();
	
	public void addTask(Task task){
		tasks.add(task);
	}
	
	public void removeTask(Task task){
		tasks.remove(task);
	}
	
	public void replaceTask(List<Task> list,Task orignal, Task replacement){
		int i = list.indexOf(orignal);
		list.set(i, replacement);
	}
	
	public boolean areTasksPartOf(Collection<Task> task){
		return tasks.containsAll(task);
	}
	
	public Iterator<Task> getAllTasksIterator(){
		List<Task> copy = sort();
		return copy.iterator();
	}
	
	private boolean more(int v, int w){
		return v > w;
	}
	
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
	
	public List<Task> cloneList(List<Task> list) {
	    List<Task> clone = new ArrayList<Task>(list.size());
	    for(Task item: list){
	    	clone.add(item);
	    }
	    return clone;
	}
	
	private Faction faction;

	public Faction getFaction() {
		return faction;
	}

	public void setFaction(Faction faction) {
		this.faction = faction;
	}

	
}
