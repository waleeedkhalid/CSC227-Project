package scheduling;

import job.PCB;
import job.PCBState;
import queues.JobQueue;
import queues.ReadyQueue;
import utils.FileReading;

import java.util.Queue;

public class FCFS {
    private int currentTime;
    private int totalTurnaroundTime;
    private int totalWaitingTime;

    public FCFS() {
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
    }

    public void schedule(PCB job) {
        job.setState(PCBState.RUNNING);
        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState() + ", Selected at: " + currentTime + ", Starting Burst Time: " + currentTime + ", Ending Burst Time: " + (currentTime + job.getBurstTime()));

        currentTime += job.getBurstTime(); //25 + 13 + 20 + 10 = 68

        job.setState(PCBState.TERMINATED);
        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState());



        totalTurnaroundTime += currentTime; // 25 + 38 + 58 + 68 = 189
        totalWaitingTime += (currentTime - job.getBurstTime());// 25 - 25 = 0, 38 - 13 = 25, 58 - 20 = 38, 68 - 10 = 58, Waiting time = 0 + 25 + 38 + 58 = 121
    }

    public double getAverageTurnaroundTime(int totalJobs) {
        return (double) totalTurnaroundTime / totalJobs;
    }

    public double getAverageWaitingTime(int totalJobs) {
        return (double) totalWaitingTime / totalJobs;
    }

    public void printAverageTimes(int totalJobs) {
        System.out.println();
        System.out.println("Average Turnaround Time: " + getAverageTurnaroundTime(totalJobs));
        System.out.println("Average Waiting Time: " + getAverageWaitingTime(totalJobs));
    }

    public void run() {
        int totalJobs = 0;

        while (!ReadyQueue.isEmpty() || !JobQueue.isEmpty()) {
            PCB job = ReadyQueue.removeJob(); // removes & deallocates memory
            if (job != null) {
                schedule(job);
                totalJobs++;
            } else {
                try {
                    Thread.sleep(100); // wait for jobs to arrive
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        printAverageTimes(totalJobs);
    }
}
