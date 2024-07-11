import java.text.SimpleDateFormat;
import java.util.Date;

class Clock {
  private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
  private Date currentTime;

  public Clock() {
    currentTime = new Date();
  }

  // Synchronized method to update the current time
  public synchronized void updateTime() {
    currentTime = new Date();
  }

  // Synchronized method to display the current time
  public synchronized void displayTime() {
    System.out.print("\r" + dateFormat.format(currentTime));
    // Flush the output to ensure it's displayed immediately
    System.out.flush();
  }

  // Method to start the clock
  public void startClock() {
    // Using lambda expressions to create threads
    Thread updateTimeThread = new Thread(() -> {
      while (true) {
        updateTime();
        try {
          // Sleep for one second to update the time every second
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("Update time thread interrupted: " + e.getMessage());
        }
      }
    });

    Thread displayTimeThread = new Thread(() -> {
      while (true) {
        displayTime();
        try {
          // Sleep for one second to display the time every second
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          System.out.println("Display time thread interrupted: " + e.getMessage());
        }
      }
    });

    // Setting thread priorities
    displayTimeThread.setPriority(Thread.MAX_PRIORITY);
    updateTimeThread.setPriority(Thread.MIN_PRIORITY);

    // Starting the threads
    updateTimeThread.start();
    displayTimeThread.start();
  }

  public static void main(String[] args) {
    Clock clock = new Clock();
    clock.startClock();
  }
}
