package GrokingJavaConcurrency.UnixBathRoomProblem;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        final UnixBathRoom bathRoom = new UnixBathRoom();
        Thread f1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bathRoom.womenUseBathRoom("Lisa");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread m1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bathRoom.menUseBathRoom("Deepak");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread m2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bathRoom.menUseBathRoom("Lovish");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread m3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bathRoom.menUseBathRoom("John");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread m4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bathRoom.menUseBathRoom("Adam");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        f1.start();
        m1.start();
        m2.start();
        m3.start();
        m4.start();
        m4.join();
        f1.join();
        m1.join();
        m2.join();
        m3.join();

    }
}
