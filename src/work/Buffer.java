package work;

import java.util.LinkedList;
import java.util.Queue;

import swing.JSwing;
public class Buffer {
	
	static Queue<Item> buffer = new LinkedList<Item>();

	public synchronized void add(Item item) {
		buffer.add(item);
		notify();
		System.out.println(buffer);
		JSwing.getProgressBar().setValue(buffer.size());;
	}
	
	public synchronized Item remove() {
		
		if(buffer.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		JSwing.getProgressBar().setValue(buffer.size());
		return buffer.remove();
	}
	
	public static Queue<Item> getBuffer() {
		return buffer;
	}
	

}
