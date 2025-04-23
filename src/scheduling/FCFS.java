package scheduling;

import job.PCB;
import job.PCBState;
import queues.ReadyQueue;

public class FCFS {
    private int currentTime;
    private int totalTurnaroundTime;
    private int totalWaitingTime;
    private ReadyQueue readyQueue;
    private int totalJobs;

    public FCFS(ReadyQueue readyQueue) {
        this.readyQueue = readyQueue;
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
        this.totalJobs = readyQueue.getSize();
    }

    public void schedule(PCB job) {
        job.setState(PCBState.RUNNING);
        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState() + ", Selected at: " + currentTime + ", Starting Burst Time: " + currentTime + ", Ending Burst Time: " + (currentTime + job.getBurstTime()));
        currentTime += job.getBurstTime(); //25 + 13 + 20 + 10 = 68
        job.setState(PCBState.TERMINATED);
        totalTurnaroundTime += currentTime; // 25 + 38 + 58 + 68 = 189
        totalWaitingTime += (currentTime - job.getBurstTime());// 25 - 25 = 0, 38 - 13 = 25, 58 - 20 = 38, 68 - 10 = 58, Waiting time = 0 + 25 + 38 + 58 = 121
    }

    public double getAverageTurnaroundTime(int totalJobs) {
        return (double) totalTurnaroundTime / totalJobs;
    }

    public double getAverageWaitingTime(int totalJobs) {
        return (double) totalWaitingTime / totalJobs;
    }

    public void printStatistics() {
        System.out.println("Average Turnaround Time: " + getAverageTurnaroundTime(totalJobs));
        System.out.println("Average Waiting Time: " + getAverageWaitingTime(totalJobs));
    }

    public void run() {
        totalJobs = readyQueue.getSize();
        int counter = 1;
        while (counter <= totalJobs) {
            PCB job = readyQueue.removeJob();
            if (job != null) {
                schedule(job);
            }
            readyQueue.addJob(job);
            counter++;
        }
    }
}
