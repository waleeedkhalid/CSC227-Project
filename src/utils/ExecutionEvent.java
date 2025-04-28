package utils;

import job.PCB;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ExecutionEvent {
    String processID;
    int startTime;
    int endTime;

    public ExecutionEvent(String processID, int startTime, int endTime) {
        this.processID = processID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getProcessId() {
        return processID;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
}