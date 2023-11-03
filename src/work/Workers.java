package work;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;

public class Workers implements Runnable {

    static int workers = 0;
    Buffer buffer = null;
    static Queue<String> employee = new LinkedList<String>();
    int timeToProduce;
    int min=1;
    int max=10;

    public Workers(Buffer buffer) {
        this.buffer = buffer;
        timeToProduce = (int)(Math.random()*(max - min + 1)) + min;
    }

    @Override
    public void run() {
        String worker = "Worker ID: " + workers + " / Time: " + timeToProduce;
        employee.add(worker);
        String path = "src/Files/Employee.dat";
        writeData((LinkedList<String>) employee, path);
        System.out.println(employee);
        
        while (workers > 0) {
            try {
                Thread.sleep(timeToProduce * 1000);
                buffer.add(new Item("Paper"));
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void writeData(LinkedList<String> stringlist, String path) {
        try (BufferedWriter br = new BufferedWriter(new FileWriter(path, true))) {
            for (String line : stringlist) {
                br.write(line);
                br.newLine();
                br.flush();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public static void addWorkers() {
    	workers++;
    }

    public static void fireWorkers() {
    	workers--;
    	employee.remove();
    }

    public static int getWorkers() {
        return workers;
    }
}

