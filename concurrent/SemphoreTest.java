import java.util.concurrent.Semaphore;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

class SemphoreTest {

    // 4 site
    static Semaphore semaphore = new Semaphore(4);

    static CountDownLatch latch  = new CountDownLatch(10);

    public static void main(String[] args) {

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Student(" "+i));
            t.start();
        }


        try {
            latch.await();
            System.out.printf("all done.\n");
        } catch (Exception e) {
            //TODO: handle exception
        }
    }
}



class Student implements Runnable {

    Random random = new Random();

    public String name;

    public Student (String name){
        this.name = name;
    }

    @Override
    public void run() {

        try {
            SemphoreTest.semaphore.acquire();
            eat();
        
            leave();
            SemphoreTest.semaphore.release();

            SemphoreTest.latch.countDown();

        } catch (Exception e) {
            //TODO: handle exception
        }
    }


    public void leave(){

        try {
            System.out.printf("i am %s, i am leaving...\n",name);
            Thread.sleep(Math.abs(random.nextInt(15) * 1000));
        } catch (Exception e) {
            //TODO: handle exception
        }
    }


    public void eat(){

        try {

            System.out.printf("i am %s, i am eatting...\n",name);
            Thread.sleep(Math.abs(random.nextInt(15) * 1000));
        } catch (Exception e) {
            //TODO: handle exception
        }
        
    }
}