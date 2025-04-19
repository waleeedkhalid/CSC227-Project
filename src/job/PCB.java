package job;
// Each job has a PCB that contains all required information to identify the job such as
// id, state, burst time, required memory, required statistics which include
// turnaround time for each job and waiting time for each job.

public class PCB {
    int id;
    PCBState state;
    int burstTime;
    int requiredMemory;
    int priority;
    int turnaroundTime;
    int waitingTime;

    public PCB(int id, int burstTime, int priority, int requiredMemory) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
        this.requiredMemory = requiredMemory;
        this.state = PCBState.NEW;
    }

    public int getId() {
        return id;
    }

    public PCBState getState() {
        return state;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getRequiredMemory() {
        return requiredMemory;
    }

    public int getPriority() {
        return priority;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    @Override
    public String toString() {
        return "PCB{" +
                "id=" + id +
                ", state=" + state +
                ", burstTime=" + burstTime +
                ", requiredMemory=" + requiredMemory +
                ", priority=" + priority +
                ", turnaroundTime=" + turnaroundTime +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
