


import java.util.Scanner;

public class BankersAlgorithm {
    private int[][] maximum; // Maximum resources required by each process
    private int[][] allocation; // Resources allocated to each process
    private int[][] need; // Remaining resources needed by each process
    private int[] available; // Available resources in the system
    private int numProcesses; // Number of processes in the system
    private int numResources; // Number of resource types

    public BankersAlgorithm(int numProcesses, int numResources) {
        this.numProcesses = numProcesses;
        this.numResources = numResources;

        maximum = new int[numProcesses][numResources];
        allocation = new int[numProcesses][numResources];
        need = new int[numProcesses][numResources];
        available = new int[numResources];
    }

    public void input() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the maximum resources required by each process:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                maximum[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter the resources allocated to each process:");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                allocation[i][j] = scanner.nextInt();
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }

        System.out.println("Enter the available resources:");
        for (int j = 0; j < numResources; j++) {
            available[j] = scanner.nextInt();
        }
    }

    public boolean isSafe() {
        int[] work = available.clone();
        boolean[] finish = new boolean[numProcesses];
        StringBuilder allocationOrder = new StringBuilder();

        int count = 0;
        while (count < numProcesses) {
            boolean found = false;
            for (int i = 0; i < numProcesses; i++) {
                if (!finish[i] && hasEnoughResources(work, need[i])) {
                    allocationOrder.append("P").append(i).append(" ");
                    finish[i] = true;
                    count++;
                    for (int j = 0; j < numResources; j++) {
                        work[j] += allocation[i][j];
                    }
                    found = true;
                }
            }
            if (!found) {
                break; // No process found with enough resources, the system is unsafe
            }
        }

        if (count < numProcesses) {
            System.out.println("The system is unsafe, cannot allocate resources!");
            return false;
        } else {
            System.out.println("The system is safe.");
            System.out.println("Order : " + allocationOrder.toString());
            return true;
        }
    }

    private boolean hasEnoughResources(int[] work, int[] need) {
        for (int i = 0; i < numResources; i++) {
            if (work[i] < need[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        System.out.print("Enter the number of resource types: ");
        int numResources = scanner.nextInt();

        BankersAlgorithm bankersAlgorithm = new BankersAlgorithm(numProcesses, numResources);
        bankersAlgorithm.input();
        bankersAlgorithm.isSafe();
    }
}

