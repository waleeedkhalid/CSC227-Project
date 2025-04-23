import job.JobScheduler;
import queues.WaitingQueue;
import queues.JobQueue;
import queues.ReadyQueue;
import scheduling.FCFS;
import utils.FileReading;
import utils.MemoryManager;
import utils.Menu;

import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // MEMORY MANAGER SECTION
        MemoryManager.setAvailableMemory(2048);

        // RR QUANTUM TIME SECTION



        // create Menu for the user to select the scheduling algorithm
        Scanner scanner = new Scanner(System.in);
        JobQueue jobQueue = new JobQueue();

        FileReading fileReading = new FileReading(jobQueue);
        fileReading.start();
        sleep(100);


        ReadyQueue readyQueue = new ReadyQueue();
        WaitingQueue waitingQueue = new WaitingQueue();

        JobScheduler jobScheduler = new JobScheduler(jobQueue, readyQueue);
        jobScheduler.start();
        sleep(100);

        int choice;
        do {
            if(jobQueue.isEmpty()) {
                System.out.println("No jobs in the queue");
                System.out.println("Exiting...");
                System.exit(0);
                return;
            }
            System.out.println("Loading jobs...");
            System.out.println();
            Menu.display();
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();

            // Instantiate the CPU scheduling algorithms
            FCFS fcfs = new FCFS(readyQueue);

            switch (choice) {
                case 1:
                    System.out.println("FCFS: First Come First Serve");
                    fcfs.run(true);
                    fcfs.printStatistics();
                    break;
                case 2:
                    System.out.println("Round Robin");
                    break;
                case 3:
                    System.out.println("Priority Scheduling");
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0); // System Call
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (choice != 4);
    }
}