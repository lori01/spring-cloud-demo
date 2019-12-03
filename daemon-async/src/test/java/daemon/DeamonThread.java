package daemon;

public class DeamonThread extends Thread {

	public void run() {
		try {
			for (int i = 0; i < 100; i++) {
				Thread.sleep(1000);
				System.out.println("DeamonThread run:"+i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("main thread start");
		Thread thread=new DeamonThread();
		thread.setDaemon(true);
		thread.start();
		System.out.println("main thread end");
	}

}
