import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;;

class CountLatchTest {

    static {
        System.loadLibrary("gvc");
    }

    static CountDownLatch latch  = new CountDownLatch(2);

    public static void main(String[] args) {

        Object graph = gv.graph("zz");
        Thread thread = new Thread(new TwoSteps());
        thread.start();

        String path = System.getProperty("java.library.path");

        System.out.printf("java.library.path = %s\n",path);
    
        System.out.print("wait 2 latch down.\n");

        try {

            latch.await();
            Thread.sleep(3000);
            
        } catch (Exception e) {
            //TODO: handle exception
        }

        System.out.print("all latch down.\n");
    }
}


class TwoSteps implements Runnable {

    Logger logger = Logger.getLogger("ThreeSteps");

    @Override
    public void run() {
        
    try {
        
        Thread.sleep(3000);
        logger.log(Level.INFO, "latch 1 down.");

        CountLatchTest.latch.countDown();

        Thread.sleep(3000);
        logger.log(Level.INFO, "latch 2 down.");

        CountLatchTest.latch.countDown();

    } catch (Exception e) {
        //TODO: handle exception
    }
    }
}