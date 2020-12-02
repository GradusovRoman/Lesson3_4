package geekbrains.lesson3_4gb;


public class PrintABC {
    static volatile char c = 'A';
    static Object monitor = new Object();

    static class doOrderABC implements Runnable {
        private char currentLetter;
        private char nextLetter;

        public doOrderABC (char currentLetter, char nextLetter) {
            this.currentLetter = currentLetter;
            this.nextLetter = nextLetter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (monitor) {
                    try {
                        while (c != currentLetter)
                            monitor.wait();
                        System.out.print(currentLetter);
                        c = nextLetter;
                        monitor.notifyAll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        //Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
        new Thread(new doOrderABC('A', 'B')).start();
        new Thread(new doOrderABC('B', 'C')).start();
        new Thread(new doOrderABC('C', 'A')).start();
    }
}
