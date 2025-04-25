package scheduling;

import job.PCB;
import job.PCBState;
import queues.ReadyQueue;

public class RoundRobin {
    int currentTime;
    int totalTurnaroundTime;
    int totalWaitingTime;
    int timeQuantum = 0;

    public RoundRobin(int timeQuantum) {
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
        this.timeQuantum = timeQuantum;
    }

    public void schedule(PCB job) {
        job.setState(PCBState.RUNNING);
        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState() + ", Selected at: " + currentTime + ", Starting Burst Time: " + currentTime + ", Ending Burst Time: " + (currentTime + Math.min(job.getBurstTime(), timeQuantum)));
        int burstTime = Math.min(job.getBurstTime(), timeQuantum); // Get the burst time for the job
        currentTime += burstTime; // Update the current time (small time)
        job.setBurstTime(job.getBurstTime() - burstTime); // Decrease the burst time of the job
        if (job.getBurstTime() == 0) {
            job.setState(PCBState.TERMINATED); // If the job is finished, set its state to terminated
            totalTurnaroundTime += currentTime; // Update the total turnaround time
            totalWaitingTime += (currentTime - job.getBurstTime()); // Update the total waiting time
        } else {
            job.setState(PCBState.READY); // If the job is not finished, set its state to ready
        }
    }

    public double getAverageTurnaroundTime(int totalJobs) {
        return (double) totalTurnaroundTime / totalJobs;
    }

    public double getAverageWaitingTime(int totalJobs) {
        return (double) totalWaitingTime / totalJobs;
    }

    public void run() {
        while (!ReadyQueue.getReadyQueue().isEmpty()) {
            PCB job = ReadyQueue.getReadyQueue().poll();
            schedule(job);
            if (job.getState() == PCBState.READY) {
                ReadyQueue.getReadyQueue().add(job);
            }
        }
    }
}
