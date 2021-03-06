package nachos.vm;

import nachos.machine.Lib;
import nachos.machine.Machine;
import nachos.machine.TranslationEntry;

import java.util.Hashtable;
import java.util.Vector;

public class InvertedPageTable {

	private static Hashtable<Pair<Integer,Integer>, TranslationEntry> pIDvpnVStranslationEntrytable = new Hashtable<>(); // TranslationEntry Against process id and virtual page number
	
	private static Vector<Pair<Integer,TranslationEntry>> pIDvsTranslationEntryTable = initGlobalTable(); // Process id of each Translation Entry

	private static Vector<Pair<Integer, TranslationEntry>> initGlobalTable() {
		Vector<Pair<Integer, TranslationEntry>> v = new Vector<>(Machine.processor().getNumPhysPages());
		for (int i = 0; i < Machine.processor().getNumPhysPages(); i++) {
			v.add(new Pair<>(0,null));
		}
		return v;
	}

	private InvertedPageTable(){
	}

	public static void set(Integer pID, TranslationEntry newEntry){
		Pair<Integer,Integer> key=new Pair<>(pID,newEntry.vpn);
		if(!pIDvpnVStranslationEntrytable.containsKey(key)){
			return;
		}
		TranslationEntry oldEntry= pIDvpnVStranslationEntrytable.get(key);
		if(oldEntry.valid){
			if (pIDvsTranslationEntryTable.get(oldEntry.ppn) != null) {
				pIDvsTranslationEntryTable.set(oldEntry.ppn, null);
			} else {
				return;
			}
		}
		if(newEntry.valid){
			if (pIDvsTranslationEntryTable.get(newEntry.ppn) == null) {
				pIDvsTranslationEntryTable.set(newEntry.ppn, new Pair<>(pID, newEntry));
			} else {
				return;
			}
		}
		pIDvpnVStranslationEntrytable.put(key, newEntry);

	}

	public static TranslationEntry get(Integer pID, Integer vpn){
		Pair<Integer,Integer> key=new Pair<>(pID,vpn);
		TranslationEntry entry=null;
		if (!pIDvpnVStranslationEntrytable.containsKey(key)) {
			return entry;
		}
		entry= pIDvpnVStranslationEntrytable.get(key);
		return entry;
	}
	
	public static void update(Integer pID, TranslationEntry entry){
		Pair<Integer,Integer> key=new Pair<>(pID,entry.vpn);
		if(pIDvpnVStranslationEntrytable.containsKey(key)){
			return;
		}
		TranslationEntry oldEntry= pIDvpnVStranslationEntrytable.get(key);

		TranslationEntry newEntry= dirtyHandler(entry,oldEntry);
		if(oldEntry!=null &&oldEntry.valid){
			if(pIDvsTranslationEntryTable.get(oldEntry.ppn) ==null){
				return;
			}
			pIDvsTranslationEntryTable.set(oldEntry.ppn, null);
		}
		if(newEntry.valid){
			if(pIDvsTranslationEntryTable.get(newEntry.ppn) !=null){
				return;
			}
			pIDvsTranslationEntryTable.set(newEntry.ppn, new Pair<>(pID, newEntry));
		}
		pIDvpnVStranslationEntrytable.put(key, newEntry);
	}
	
	private static TranslationEntry dirtyHandler(TranslationEntry entry1, TranslationEntry entry2){
		if (entry2 != null) {
			if (entry1.dirty || entry2.dirty) {
				entry1.dirty = true;
			}
			return entry1;
		} else {
			return entry1;
		}
	}

	public static Pair<Integer,TranslationEntry> entryToDelete(){
		Pair<Integer,TranslationEntry> entry=null;
		int index= Lib.random(pIDvsTranslationEntryTable.size());
		entry= pIDvsTranslationEntryTable.get(index);
		while (entry == null || !entry.getSecond().valid) {
			index = Lib.random(pIDvsTranslationEntryTable.size());
			entry = pIDvsTranslationEntryTable.get(index);
		} //while Translation Entry is not valid
		return entry;
	}

}
