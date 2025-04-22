package scheduling;

import job.PCB;
import job.PCBState;
import queues.ReadyQueue;

public class FCFS implements Runnable {
    private int currentTime;
    private int totalTurnaroundTime;
    private int totalWaitingTime;
    private ReadyQueue readyQueue;

    public FCFS(ReadyQueue readyQueue) {
        this.readyQueue = readyQueue;
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
    }

    public void schedule(PCB job) {
        job.setState(PCBState.RUNNING);
        System.out.println("Running job: " + job.getId());
        currentTime += job.getBurstTime();
        job.setState(PCBState.TERMINATED);
        totalTurnaroundTime += currentTime;
        totalWaitingTime += (currentTime - job.getBurstTime());
    }

    public double getAverageTurnaroundTime(int totalJobs) {
        return (double) totalTurnaroundTime / totalJobs;
    }

    public double getAverageWaitingTime(int totalJobs) {
        return (double) totalWaitingTime / totalJobs;
    }

    public void printStatistics(int totalJobs) {
        System.out.println("Average Turnaround Time: " + getAverageTurnaroundTime(totalJobs));
        System.out.println("Average Waiting Time: " + getAverageWaitingTime(totalJobs));
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (!readyQueue.isEmpty()) {
            PCB job = readyQueue.removeJob();
            if (job != null) {
                System.out.println("Scheduling job: " + job.getId());
                schedule(job);
            }
        }
        printStatistics(readyQueue.getSize());
    }
}
