package scheduling;

import job.PCB;
import job.PCBState;
import queues.JobQueue;
import queues.ReadyQueue;
import utils.ExecutionEvent;
import utils.GanttChart;
import utils.MemoryManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobin {
    int currentTime;
    int totalTurnaroundTime;
    int totalWaitingTime;
    int timeQuantum = 0;
    List<ExecutionEvent> executionLog;
    List<PCB> completedJobs;

    public RoundRobin(int timeQuantum) {
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
        this.timeQuantum = timeQuantum;
        this.executionLog = new ArrayList<>();
        this.completedJobs = new ArrayList<>();
    }



    public void run() {
        System.out.println("\nExecuting Round-Robin (RR) Scheduling with quantum = " + timeQuantum + "ms...");
        Queue<PCB> queue = new LinkedList<>(JobQueue.getJobQueue());
        currentTime = 0;

        // Initialize processes
        for (PCB job : queue) {
            job.setState(PCBState.READY);
            job.setRemainingTime(job.getBurstTime());
            job.setTurnaroundTime(0);
            job.setWaitingTime(0);
        }

        while (!queue.isEmpty()) {
            PCB job = queue.poll();
            if (job == null) {
                continue; // Skip if job is null
            }

            int executionTime = Math.min(timeQuantum, job.getRemainingTime());

            job.setState(PCBState.RUNNING);
            System.out.println("Job ID: " + job.getId() + ", State: " + job.getState() + ", Selected at: " + currentTime + ", Starting Execution Time: " + currentTime + ", Execution Time: " + executionTime + ", Ending Execution Time: " + (currentTime + executionTime));

            // Execute the job for up to quantum time units
            executionLog.add(new ExecutionEvent(job.getId(), currentTime, currentTime + executionTime));
            currentTime += executionTime;
            job.setRemainingTime(job.getRemainingTime() - executionTime);

            if (job.getRemainingTime() > 0) {
                // If job is not completed, put back in queue
                queue.add(job);
            } else {
                // Job is completed
                job.setTurnaroundTime(currentTime);
                job.setWaitingTime(job.getTurnaroundTime() - job.getBurstTime());

                // Calculate average turnaround and waiting times
                totalTurnaroundTime += job.getTurnaroundTime();
                totalWaitingTime += job.getWaitingTime();

                job.setState(PCBState.TERMINATED);
                System.out.println("Job ID: " + job.getId() + ", State: " + job.getState());
                completedJobs.add(job);
            }
        }



        double averageTurnaroundTime = (double) totalTurnaroundTime / JobQueue.getJobQueue().size();
        double averageWaitingTime = (double) totalWaitingTime / JobQueue.getJobQueue().size();
        if(completedJobs.isEmpty()) {
            return;
        }
        GanttChart.printTimes(averageTurnaroundTime, averageWaitingTime);
    }

    public List<ExecutionEvent> getExecutionLog() {
        return executionLog;
    }

    public List<PCB> getCompletedJobs() {
        return completedJobs;
    }
}
