package scheduling;

import job.PCB;
import job.PCBState;
import queues.JobQueue;
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
        job.setOriginalBurstTime(job.getBurstTime());

        job.setState(PCBState.RUNNING);
        int burstTime = Math.min(job.getBurstTime(), timeQuantum); // Get the burst time for the job

        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState()
                + ", Selected at: " + currentTime + ", Starting Burst Time: " + currentTime
                +
                ", Executing for: " + burstTime
                + ", Ending Burst Time: " + (currentTime + burstTime));

        currentTime += burstTime; // Update the current time (small time)
        job.setBurstTime(job.getBurstTime() - burstTime); // Decrease the burst time of the job

        if (job.getBurstTime() == 0) {
            job.setState(PCBState.TERMINATED); // If the job is finished, set its state to terminated
            System.out.println("Job ID: " + job.getId() + ", State: " + job.getState());

            int turnaround = currentTime;
            int waiting = turnaround - job.getOriginalBurstTime(); // Calculate waiting time

            totalTurnaroundTime += turnaround; // Update the total turnaround time
            totalWaitingTime += waiting; // Update the total waiting time
        }

        else {
            job.setState(PCBState.READY); // If the job is not finished, set its state to ready
        }
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
                if (job.getState() == PCBState.READY) {
                    ReadyQueue.getReadyQueue().add(job);
                }
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
