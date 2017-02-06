package storm;

public class Tread {
	
	public static void main(String[] args) throws InterruptedException {
		System.out.println("main begin");
		syso();
		System.out.println("main end");
		Thread.sleep(10000);
	}

	public static void syso() throws InterruptedException{
		System.out.println("我比多线程先执行");
		Thread.sleep(5000);
		new Thread(){
			public void run(){
				for(int i = 0 ; i < 100000 ; i++){
					System.out.println("thread\t"+i);
				}
			}
		}.start();
		for(int i = 0 ; i < 1000 ; i++){
			System.err.println("no\t"+i);
		}
		System.out.println("yes");
	}
	
}
