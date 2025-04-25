import job.JobScheduler;
import queues.JobQueue;
import queues.ReadyQueue;
import scheduling.FCFS;
//import scheduling.PriorityScheduling;
import scheduling.RoundRobin;
import utils.*;

import java.util.PriorityQueue;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final int MEMORY_SIZE = 2048; // Memory size in MB
        final int TIME_QUANTUM = 7; // Round Robin time quantum (ms)

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            initializeJobQueue(MEMORY_SIZE);

            // Instantiate the CPU scheduling algorithms
            FCFS fcfs = new FCFS();
//            PriorityScheduling priorityScheduling = new PriorityScheduling();
            RoundRobin roundRobin = new RoundRobin(TIME_QUANTUM);

            System.out.println();
            Menu.display();
            System.out.println("Enter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("FCFS: First Come First Serve");
                    fcfs.run();
                    GanttChart.displayGanttChart(fcfs.getExecutionLog());
                    GanttChart.displayStatistics(fcfs.getCompletedJobs());
                    break;
                case 2:
                    System.out.println("Round Robin");
                    roundRobin.run();
                    GanttChart.displayGanttChart(roundRobin.getExecutionLog());
                    GanttChart.displayStatistics(roundRobin.getCompletedJobs());
                    break;
                case 3:
                    System.out.println("Priority Scheduling");
//                    priorityScheduling.run();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    System.exit(0); // System Call
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (true);
    }

    private static void initializeJobQueue(int memory_size) throws InterruptedException {
        // Clear existing queues
        while (!JobQueue.isEmpty()) {
            JobQueue.removeJob();
        }
        while (!ReadyQueue.isEmpty()) {
            ReadyQueue.removeJob();
        }

        // Reset memory
        MemoryManager.setAvailableMemory(memory_size);

        // Read new jobs from file
        FileReading fileReading = new FileReading();
        fileReading.start();
        sleep(100);

        if (JobQueue.isEmpty()) {
            System.out.println("No jobs in the queue");
            System.out.println("Exiting...");
            System.exit(0);
        }

        // Schedule jobs
        JobScheduler jobScheduler = new JobScheduler();
        jobScheduler.start();
        sleep(100);
    }
}