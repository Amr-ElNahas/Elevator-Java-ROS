package Processes;
import java.awt.Color;

import Enums.State;
import model.Elevator;
import model.Elevator.ProcessorGrapher;

public abstract class Process extends Thread implements Comparable{
	public PCB pcb;
	public int createTime;
	public int readyTime;
	public int firstRunTime=-1;
	public int terminateTime;
	
	public void create(){
		Elevator.memory.add(this);
		createTime=ProcessorGrapher.second;
		System.out.println("at t= "+ProcessorGrapher.second+" "+this);
		this.ready();
	}
	
	
	public void ready(){
		pcb.state=Enums.State.READY;
		readyTime=ProcessorGrapher.second;
		System.out.println("at t= "+ProcessorGrapher.second+" "+this);
	}
	
	
	public abstract void run();
	
	
	public void block(){
		pcb.state = Enums.State.BLOCKED;
		Elevator.inside.blocked.setBackground(Color.RED);
	}
	public void terminate(){
		pcb.state=Enums.State.DEAD;
		terminateTime=ProcessorGrapher.second;
		System.out.println("at t= "+ProcessorGrapher.second+" "+this);
		System.out.println("turnaroundtime= "+(terminateTime-createTime)+" seconds");
		Elevator.processes.remove(this);
		Elevator.memory.remove(this);
		Elevator.memsem.release();
	}
	@Override
	public int compareTo(Object process) {
		return ((Process)process).pcb.priority-pcb.priority;
	}
	
}
