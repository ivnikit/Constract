import java.io.*;
import java.util.Arrays;

public class Constract {
    public static void main(String[] args) throws IOException {
        PipedInputStream pin = new PipedInputStream();
        PipedOutputStream pon = new PipedOutputStream();
        pin.connect(pon);
        Producer pr = new Producer(Producer.howMany, pon);
        Consumer co = new Consumer(pin);
        pr.start();
        try {
            pr.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        co.start();
    }
}


class Producer extends Thread {
    public static int howMany = 5;
    private DataOutputStream os;

    public Producer(int howMany, OutputStream os) {
        this.howMany = howMany;
        this.os = new DataOutputStream(os);
    }


    public void run() {
        int[] n = new int[howMany];
        try {
            for (int i = 0; i < howMany; i++) {
                n[i] = (int) (Math.random() * 100);
                System.out.println("Вход в поток: " + n[i]);
                byte[] f = new byte[howMany];
                f[i] = (byte) n[i];

                os.write(f[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class Consumer extends Thread {
    private DataInputStream is;

    public Consumer(InputStream is) {
        this.is = new DataInputStream(is);
    }


    public void run() {
        byte n = 0;

        try {

            for (int i = 0; i < Producer.howMany; i++) {
                n = (byte) is.read();
                System.out.println("Выход из потока: " + n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

