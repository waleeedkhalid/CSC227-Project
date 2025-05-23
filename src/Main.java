import job.JobScheduler;
import queues.JobQueue;
import queues.ReadyQueue;
import scheduling.FCFS;
import scheduling.PriorityScheduling;
import scheduling.RoundRobin;
import utils.*;
import utils.MemoryManager;

import java.util.PriorityQueue;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    // TO CHANGE MEMORY SIZE, GO TO MemoryManager.java
    static final int TIME_QUANTUM = 7; // Round Robin time quantum (ms)

    static FileReading fileReading = new FileReading();
    static JobScheduler jobScheduler = new JobScheduler();

    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            initializeJobQueue();

            // Instantiate the CPU scheduling algorithms
            FCFS fcfs = new FCFS();
            PriorityScheduling priorityScheduling = new PriorityScheduling();
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
                    priorityScheduling.run();
                    GanttChart.displayGanttChart(priorityScheduling.getExecutionLog());
                    GanttChart.displayStatistics(priorityScheduling.getCompletedJobs());
                    break;
                case 4:
                    System.out.println("Comparative Analysis");
                    System.out.println("FCFS vs Round Robin vs Priority Scheduling");
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("FCFS: First Come First Serve");
                    fcfs.run();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("Round Robin");
                    initializeJobQueue();
                    roundRobin.run();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("Priority Scheduling");
                    initializeJobQueue();
                    priorityScheduling.run();
                    GanttChart.displayGanttChart(fcfs.getExecutionLog());
                    GanttChart.displayStatistics(fcfs.getCompletedJobs());
                    GanttChart.displayGanttChart(roundRobin.getExecutionLog());
                    GanttChart.displayStatistics(roundRobin.getCompletedJobs());
                    GanttChart.displayGanttChart(priorityScheduling.getExecutionLog());
                    GanttChart.displayStatistics(priorityScheduling.getCompletedJobs());
                    System.out.println("Comparative analysis completed.");
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0); // System Call
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } while (true);
    }

    private static void initializeJobQueue() throws InterruptedException {
        // Clear existing queues
        while (!JobQueue.isEmpty()) {
            JobQueue.removeJob();
        }
        while (!ReadyQueue.isEmpty()) {
            ReadyQueue.removeJob();
        }

        // Reset memory
        MemoryManager.setAvailableMemory(MemoryManager.MEMORY_SIZE);

        // Read new jobs from file
        fileReading.start();
        sleep(100);

        if (JobQueue.isEmpty()) {
            System.out.println("No jobs in the queue");
            System.out.println("Exiting...");
            System.exit(0);
        }

        // Schedule jobs
        jobScheduler.start();
        sleep(100);
    }
}
