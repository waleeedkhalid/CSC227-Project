import job.JobScheduler;
import job.PCB;
import job.WaitingQueue;
import queues.JobQueue;
import queues.ReadyQueue;
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
        System.out.println("Job Queue: " + jobQueue.getSize());
        ReadyQueue readyQueue = new ReadyQueue();
        WaitingQueue waitingQueue = new WaitingQueue();
        JobScheduler jobScheduler = new JobScheduler(jobQueue, readyQueue);
        jobScheduler.start();
        sleep(100);
        System.out.println("Ready Queue: " + readyQueue.getSize());
//       Menu.display();
        int choice = scanner.nextInt();
        do {
            switch (choice) {
                case 1:
                    System.out.println("FCFS");
                    break;
                case 2:
                    System.out.println("SJF");
                    break;
                case 3:
                    System.out.println("Priority Scheduling");
                    break;
                case 4:
                    System.out.println("Round Robin");
                    break;
                case 5:
                    System.out.println("Exit");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
        } while (choice != 5);
    }
}