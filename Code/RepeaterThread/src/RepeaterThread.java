public class RepeaterThread extends Thread {

		private String id;
		private int count;

		public RepeaterThread(String id, int count) {
			this.id = id;
			this.count = count;
		}
			
		public void run() {

			System.out.println("Inside : " + Thread.currentThread().getName());

			do {
				System.out.println(id + ": " + count);
				count--;

				try {
					Thread.sleep(1000); // 1000ms = 1s
				} catch (InterruptedException ignore) {
				}
				
			} while (count > 0);

			System.out.println(id + ": end");

		}
	
		public static void main(String[] args) {
				
				System.out.println("Inside : " + Thread.currentThread().getName());
        
        for (int i = 0; i < 3; i++) {
						String id = String.valueOf((char) ('A' + i));
						int count = (int) (Math.random() * 10);
					
						RepeaterThread repeater = new RepeaterThread(id, count);
						repeater.start();
				}

				System.out.println("Repeaters ...");

    }
}