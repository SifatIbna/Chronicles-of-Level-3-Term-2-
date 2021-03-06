package nachos.vm;

import nachos.machine.Lib;
import nachos.machine.OpenFile;
import nachos.machine.Processor;
import nachos.threads.ThreadedKernel;

import java.util.*;

public class DiskSwapSpace {

	private static Integer pageSize = Processor.pageSize;

	private static String name = "Hard Disk";

	private static OpenFile swapFile = ThreadedKernel.fileSystem.open(name, true);

	
	private static Hashtable<Pair<Integer,Integer>,Integer> swapTable = new Hashtable<>(); // Location Against process id and virtual page number
	
	private static HashSet<Pair<Integer,Integer>> unallocatedPidandVPN = new HashSet<>(); // unallocated process id and virtual page number
	
	private static LinkedList<Integer> freeSpaces = new LinkedList<>();
	
	protected final static char dbgVM='v';

	private DiskSwapSpace(){
	}

	public static Integer makeSpace(Integer pid, Integer vpn){
		Pair<Integer,Integer> key= new Pair<>(pid, vpn);
		if (!unallocatedPidandVPN.contains(key)) {
			Integer index=-1;
			index=swapTable.get(key);
			if(index==-1){
			}
			return index;
		} else {
			unallocatedPidandVPN.remove(key);
			if (!freeSpaces.isEmpty()) {
			} else {
				freeSpaces.add(swapTable.size());
			}
			Integer index= freeSpaces.removeFirst();
			swapTable.put(key, index);
			return index;
		}
	}
	
	public static byte[] read(Integer pid, Integer vpn){
		Integer position= search(pid,vpn);
		switch (position) {
			case -1:
				return new byte[pageSize];
		}
		byte[] reader=new byte[pageSize];
		int length=swapFile.read(position*pageSize, reader, 0, pageSize);
		if(length==-1){
			return new byte[pageSize];
		}
		return reader;
	}
	
	private static Integer search(Integer pid, Integer vpn) {
        Integer position = swapTable.get(new Pair<>(pid, vpn));
		return Objects.requireNonNullElse(position, -1);
    }
	
	
	public static Integer write(Integer pid, Integer vpn, byte[] page, Integer offset){
		Integer position= makeSpace(pid,vpn);
		if (position != -1) {
			swapFile.write(position * pageSize, page, offset, pageSize);
			return position;
		} else {
			return -1;
		}
	}
}
