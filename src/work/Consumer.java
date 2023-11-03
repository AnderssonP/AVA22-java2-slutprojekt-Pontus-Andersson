package work;

public class Consumer implements Runnable{

	
	Buffer buffer = null;
	int timeMax=10;
	int timeMin=1;
	boolean isRunning = true;
	
	public Consumer(Buffer buffer) {
		this.buffer = buffer;
	}
	
	@Override
	public void run() {
		int timeToConsume = (int)(Math.random()*(timeMax - timeMin + 1))+timeMin;
		while(isRunning) {
			try {
				Thread.sleep(timeToConsume *1000);
				System.out.println("Consumed: " + buffer.remove());
					}catch(InterruptedException e){
					System.out.println(e);
					}
					
			}
		}
	
}
