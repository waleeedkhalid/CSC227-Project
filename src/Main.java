import job.JobScheduler;
import job.PCB;
import queues.WaitingQueue;
import queues.JobQueue;
import queues.ReadyQueue;
import scheduling.FCFS;
import utils.FileReading;
import utils.Menu;

import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // create Menu for the user to select the scheduling algorithm
        Scanner scanner = new Scanner(System.in);
        JobQueue jobQueue = new JobQueue();

        FileReading fileReading = new FileReading(jobQueue);
        fileReading.start();
        sleep(100);

//        System.out.println("Job Queue size: " + jobQueue.getSize());
//        jobQueue.printJobQueue();

        ReadyQueue readyQueue = new ReadyQueue();
        WaitingQueue waitingQueue = new WaitingQueue();

        JobScheduler jobScheduler = new JobScheduler(jobQueue, readyQueue);
        jobScheduler.start();
        sleep(100);

//        System.out.println("Ready Queue: " + readyQueue.getSize());
//        readyQueue.printReadyQueue();

        FCFS fcfs = new FCFS(readyQueue);

        int choice;
        do {
            System.out.println();
            Menu.display();
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("FCFS: First Come First Serve");
                    fcfs.run();
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
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            if (choice == 4) {
                System.out.println("Number of active threads from the given thread: " + Thread.activeCount());
                jobScheduler.stopScheduler();
                fileReading.stopReading();
            }
        } while (choice != 4);
    }
}