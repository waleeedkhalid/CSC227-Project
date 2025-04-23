package utils;

import job.PCB;
import queues.JobQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReading implements Runnable {

    @Override
    public void run() {
        System.out.println("Reading file");
        read();
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public void read() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("job.txt"));
            String line;
            while ((line=reader.readLine()) != null) {
                String[] parts = line.split("[:;]");
                // ProcessID:BurstTime:Priority:MemoryRequired
                try {
                    int processID = Integer.parseInt(parts[0]);
                    int burstTime = Integer.parseInt(parts[1]);
                    int priority = Integer.parseInt(parts[2]);
                    int memoryRequired = Integer.parseInt(parts[3]);
                    PCB pcb = new PCB(processID, burstTime, priority, memoryRequired);
                    System.out.println(pcb);
                    JobQueue.addJob(pcb);
                } catch (NumberFormatException e) {
                    System.out.println("ERR:"+line);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
