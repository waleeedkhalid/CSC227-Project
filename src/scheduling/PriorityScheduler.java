package scheduling;

import job.PCB;
import job.PCBState;
import queues.ReadyQueue;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityScheduler {
    private int currentTime;
    private int totalTurnaroundTime;
    private int totalWaitingTime;
    private ReadyQueue readyQueue;
    private int totalJobs;
    private PriorityQueue<PCB> starvationQueue;

    public PriorityScheduler(ReadyQueue readyQueue) {
        this.readyQueue = readyQueue;
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
        this.totalJobs = readyQueue.getSize();

        // Comparator for priority (higher priority first)
        Comparator<PCB> priorityComparator = Comparator.comparingInt(PCB::getPriority).reversed();
        this.starvationQueue = new PriorityQueue<>(priorityComparator);
    }

    public void schedule(PCB job) {
        job.setState(PCBState.RUNNING);
        int startTime = currentTime;
        int endTime = currentTime + job.getBurstTime();

        System.out.println("Job ID: " + job.getId() +
                ", Priority: " + job.getPriority() +
                ", State: " + job.getState() +
                ", Selected at: " + currentTime +
                ", Burst: " + startTime + "-" + endTime);

        currentTime = endTime;
        job.setState(PCBState.TERMINATED);

        // Calculate and update statistics
        int turnaroundTime = currentTime;
        int waitingTime = currentTime - job.getBurstTime();

        job.setTurnaroundTime(turnaroundTime);
        job.setWaitingTime(waitingTime);

        totalTurnaroundTime += turnaroundTime;
        totalWaitingTime += waitingTime;
    }

    public void checkStarvation() {
        for (PCB job : starvationQueue) {
            int waitingTime = currentTime - job.getArrivalTime();
            if (waitingTime > job.getPriority() * 10) { // Threshold for starvation
                System.out.println("STARVATION WARNING: Job " + job.getId() +
                        " (Priority " + job.getPriority() +
                        ") waiting for " + waitingTime + "ms");
                // Implement aging if needed
                job.setPriority(job.getPriority() + 1); // Boost priority
            }
        }
    }

    public void run() {
        // Transfer all jobs to priority queue
        while (!readyQueue.isEmpty()) {
            PCB job = readyQueue.removeJob();
            if (job != null) {
                starvationQueue.add(job);
            }
        }

        // Process jobs in priority order
        while (!starvationQueue.isEmpty()) {
            PCB job = starvationQueue.poll();
            if (job != null) {
                checkStarvation();
                schedule(job);
            }
        }

        printStatistics();
    }

    public double getAverageTurnaroundTime() {
        return (double) totalTurnaroundTime / totalJobs;
    }

    public double getAverageWaitingTime() {
        return (double) totalWaitingTime / totalJobs;
    }

    public void printStatistics() {
        System.out.println("\nPriority Scheduling Statistics:");
        System.out.println("Average Turnaround Time: " + getAverageTurnaroundTime());
        System.out.println("Average Waiting Time: " + getAverageWaitingTime());
    }
}

