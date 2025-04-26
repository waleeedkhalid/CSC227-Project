package scheduling;

import job.PCB;
import job.PCBState;
import queues.JobQueue;
import queues.ReadyQueue;
import utils.ExecutionEvent;
import utils.FileReading;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class FCFS {
    private int currentTime;
    private int totalTurnaroundTime;
    private int totalWaitingTime;
    private List<ExecutionEvent> executionLog;
    private List<PCB> completedJobs;

    public FCFS() {
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
        this.executionLog = new ArrayList<>();
        this.completedJobs = new ArrayList<>();
    }

    private void schedule(PCB job) {
        job.setState(PCBState.RUNNING);

        int startTime = currentTime;
        int endTime = currentTime + job.getBurstTime();
        executionLog.add(new ExecutionEvent(job.getId(), startTime, endTime));

        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState() +
                ", Selected at: " + currentTime + ", Starting Burst Time: " + currentTime +
                ", Ending Burst Time: " + endTime);

        currentTime += job.getBurstTime();

        job.setState(PCBState.TERMINATED);
        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState());
        completedJobs.add(job);

        job.setTurnaroundTime(endTime); // arrivalTime assumed 0
        job.setWaitingTime(endTime - job.getBurstTime());

        totalTurnaroundTime += job.getTurnaroundTime();
        totalWaitingTime += job.getWaitingTime();
    }


    public List<ExecutionEvent> getExecutionLog() {
        return executionLog;
    }
    public List<PCB> getCompletedJobs() {
        return completedJobs;
    }

    public void run() {
        Queue<PCB> jobQueue = JobQueue.getJobQueue();
        Queue<PCB> readyQueue = ReadyQueue.getReadyQueue();

        while (!jobQueue.isEmpty()) {
            PCB job = jobQueue.poll(); // Remove from jobQueue
            if (job != null && job.getState() == PCBState.NEW) { // Only move NEW jobs
                readyQueue.add(job);
                System.out.println("New job added to Ready Queue: " + job);
            }
        }

        while (!readyQueue.isEmpty()) {
            PCB job = readyQueue.poll(); // Get the next job
            if (job != null) {
                schedule(job); // Execute it
            }
        }


        // Display the average turnaround and waiting times
        double averageTurnaroundTime = (double) totalTurnaroundTime / (double) completedJobs.size();
        double averageWaitingTime = (double) totalWaitingTime / (double) completedJobs.size();
        System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
        System.out.println("Average Waiting Time: " + averageWaitingTime);
    }
}
