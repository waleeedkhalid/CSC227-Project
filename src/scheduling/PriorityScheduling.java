package scheduling;

import job.PCB;
import job.PCBState;
import queues.JobQueue;
import queues.ReadyQueue;
import utils.ExecutionEvent;
import utils.MemoryManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class PriorityScheduling {
    private int currentTime;
    private int totalTurnaroundTime;
    private int totalWaitingTime;
    private final List<ExecutionEvent> executionLog;
    private final List<PCB> completedJobs;

    public PriorityScheduling() {
        this.currentTime = 0;
        this.totalTurnaroundTime = 0;
        this.totalWaitingTime = 0;
        this.executionLog = new ArrayList<>();
        this.completedJobs = new ArrayList<>();
    }

    public List<ExecutionEvent> getExecutionLog() {
        return executionLog;
    }

    public List<PCB> getCompletedJobs() {
        return completedJobs;
    }

    public void schedule(PCB job) {
        job.setState(PCBState.RUNNING);
        int startTime = currentTime;
        int endTime = currentTime + job.getBurstTime();
        executionLog.add(new ExecutionEvent(job.getId(), startTime, endTime));

        System.out.println("Job ID: " + job.getId() + ", Priority: " + job.getPriority() + ", State: " + job.getState() +
                ", Selected at: " + currentTime +
                ", Starting Burst Time: " + startTime +
                ", Ending Burst Time: " + endTime);

        currentTime = endTime;

        job.setState(PCBState.TERMINATED);
        System.out.println("Job ID: " + job.getId() + ", State: " + job.getState());

        completedJobs.add(job);

        // Free memory if memory management is being used
        MemoryManager.deallocateMemory(job.getRequiredMemory());

        job.setTurnaroundTime(currentTime);
        job.setWaitingTime(currentTime - job.getBurstTime());

        totalTurnaroundTime += job.getTurnaroundTime();
        totalWaitingTime += job.getWaitingTime();
    }

    public void run() {
        MemoryManager.resetMemory(); // Reset memory to initial size
        Queue<PCB> jobQueue = JobQueue.getJobQueue();
        Queue<PCB> readyQueue = ReadyQueue.getReadyQueue();

        // Move all jobs from jobQueue to readyQueue
        while (!jobQueue.isEmpty()) {
            PCB job = jobQueue.poll(); // Remove from jobQueue
            if (job != null && job.getState() == PCBState.NEW) { // Only move NEW jobs
                readyQueue.add(job);
                System.out.println("New job added to Ready Queue: " + job);
            }
        }

        // Now create a list from readyQueue and sort it by priority descending
        List<PCB> sortedList = new ArrayList<>(readyQueue);
        sortedList.sort(Comparator.comparingInt(PCB::getPriority).reversed());
        System.out.println("Sorted jobs to schedule:");

        for (PCB job : sortedList) {
            System.out.println("Job ID: " + job.getId() + " Priority: " + job.getPriority());
        }
        System.out.println();

        int starvationThreshold = 40;

        // Process sorted list
        for (PCB job : sortedList) {
            int jobWaitingTime = currentTime;
            if (jobWaitingTime > starvationThreshold) {
                System.out.println("Job ID: " + job.getId() + " is starving!");
                job.setPriority(job.getPriority() + 1);
            }

            if (job.getRequiredMemory() <= MemoryManager.getAvailableMemory()) {
                // Allocate memory
                MemoryManager.allocateMemory(job.getRequiredMemory());
                schedule(job);
                // After job finishes, free the memory
                MemoryManager.deallocateMemory(job.getRequiredMemory());
            }
        }

        // After all jobs are processed, calculate average metrics
        if (!completedJobs.isEmpty()) {
            double averageTurnaroundTime = (double) totalTurnaroundTime / completedJobs.size();
            double averageWaitingTime = (double) totalWaitingTime / completedJobs.size();

            System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
            System.out.println("Average Waiting Time: " + averageWaitingTime);
        }
    }
}
