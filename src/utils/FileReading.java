package utils;

import job.PCB;
import queues.JobQueue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileReading implements Runnable {

    @Override
    public void run() {
        System.out.println();
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
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("[:;]");

                // Check if the line has exactly 4 parts (ProcessID, BurstTime, Priority, MemoryRequired)
                if (parts.length != 4) {
                    System.out.println("Error: Invalid format in line: " + line);
                    continue;
                }

                try {
                    int processID = Integer.parseInt(parts[0]);
                    int burstTime = Integer.parseInt(parts[1]);
                    int priority = Integer.parseInt(parts[2]);
                    int memoryRequired = Integer.parseInt(parts[3]);
                    PCB pcb = new PCB(processID, burstTime, priority, memoryRequired);
                    System.out.println(pcb);
                    JobQueue.addJob(pcb);
                } catch (NumberFormatException e) {
                    System.out.println("ERR: Invalid number format in line: " + line);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.toString());
        }
    }

}
