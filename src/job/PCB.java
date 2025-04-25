package job;
// Each job has a PCB that contains all required information to identify the job such as
// id, state, burst time, required memory, required statistics which include
// turnaround time for each job and waiting time for each job.

public class PCB {
    int id;
    PCBState state;
    int requiredMemory;
    int priority;

    int burstTime;
    int turnaroundTime;
    int waitingTime;
    int startTime;
    int remainingTime; // RR
    int originalBurstTime; // RR
    boolean starvation; // Priority

    public void setId(int id) {
        this.id = id;
    }

    public void setRequiredMemory(int requiredMemory) {
        this.requiredMemory = requiredMemory;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public void setStarvation(boolean starvation) {
        this.starvation = starvation;
    }

    public PCB(int id, int burstTime, int priority, int requiredMemory) {
        this.id = id;
        this.burstTime = burstTime;
        this.priority = priority;
        this.requiredMemory = requiredMemory;
        this.state = PCBState.NEW;
        this.turnaroundTime = 0;
        this.waitingTime = 0;
        this.startTime = 0;
        this.remainingTime = burstTime;
        this.starvation = false; // Priority
    }

    public int getId() {
        return id;
    }

    public PCBState getState() {
        return state;
    }

    public void setState(PCBState state) {
        this.state = state;
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

    public int getStartTime() {
        return startTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }


    public boolean isStarvation() {
        return starvation;
    }

    public int getOriginalBurstTime() {
        return originalBurstTime;
    }
    public void setOriginalBurstTime(int originalBurstTime) {
        this.originalBurstTime = originalBurstTime;
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
                ", startTime=" + startTime +
                ", remainingTime=" + remainingTime +
                ", starvation=" + starvation +
                '}';
    }
}
