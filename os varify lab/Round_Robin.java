import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Round_Robin {

    int ID, AT, BT, CT, TAT, WT, RT;
    boolean visit;

    static float avgwt, avgta;

    static int size, completedProcesses = 0, timeQuantum;
    static int ST = 0, iteration = 0;

    static Queue<Round_Robin> processes = new LinkedList<>();
    static Queue<Round_Robin> readyQueue = new LinkedList<>();

    public Round_Robin(int id, int arr, int time) {
        this.ID = id;
        this.AT = arr;
        this.BT = time;
        this.RT = time;
        this.CT = 0;
        this.visit=false;
    }

    public static void main(String[] args)  {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of process: ");
        size = sc.nextInt();
        System.out.print("Enter the Time Quantum: ");
        timeQuantum = sc.nextInt();

        for (int i = 0; i < size; i++) {
            int arr, time;

            System.out.println("\nEnter Process" + (i + 1));

            System.out.print("Arrival Time: ");
            arr = sc.nextInt();
            System.out.print("Burst Time: ");
            time = sc.nextInt();

            processes.add(new Round_Robin((i + 1), arr, time));

        }

        peek_first();
        calculation_table();
        disp_table();
    }

    private static void disp_table() {
        System.out.println();
        System.out.println(" |Proc| "+" |Arri| "+" |Burs| "+" |Compl| "+" |Turn| "+" |Wait| ");

        for(Round_Robin j: processes){
            System.out.println("   P"+j.ID+"\t    "+j.AT+"\t   "+ j.BT+"\t    "+j.CT+"\t    "+ j.TAT+"\t    "+j.WT);
            System.out.println();
        }

        System.out.println("\naverage waiting time: "+(float) (avgwt/size));     // printing average waiting time.
        System.out.println("average turnaround time:"+(float) (avgta/size));
    }


    private static void peek_first() {
        for (Round_Robin i:processes) {
            if(ST >= i.AT && i.RT !=0 ){
                readyQueue.add(i);
                i.visit=true;
                break;
            }
        }
    }

    public static void calculation_table() {

        while (completedProcesses < processes.size()) {

            Round_Robin temp;

            temp= readyQueue.poll();
            // if(temp.RT==0) calculation_table();


            if(temp.RT <= timeQuantum && temp.RT!=0){
                ST+=temp.RT;
                temp.CT=ST;
                temp.RT=0;
                completedProcesses++;

                temp.TAT= temp.CT-temp.AT;
                temp.WT= temp.TAT - temp.BT;

                avgta+=temp.TAT;
                avgwt+=temp.WT;
            }else{

                ST+= timeQuantum;
                temp.RT-=timeQuantum;
            }


            for (Round_Robin i:processes) {

                if(ST >= i.AT && i.RT !=0 && i.ID != temp.ID && i.visit==false){
                    readyQueue.add(i);
                    i.visit=true;
                }

            }
            if(temp.RT!=0)
                readyQueue.add(temp);
        }
    }


}
