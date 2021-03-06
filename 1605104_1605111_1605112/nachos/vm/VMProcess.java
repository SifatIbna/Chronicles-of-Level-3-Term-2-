package nachos.vm;

import nachos.machine.*;
import nachos.threads.*;
import nachos.userprog.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

/**
 * A <tt>UserProcess</tt> that supports demand-paging.
 */
public class VMProcess extends UserProcess {

    protected Hashtable<Integer,Pair<Integer,Integer>> vpnToSecNumAndCoffAddr; // section number and page address of CoffPages against vpn

    protected ArrayList<Integer> allocatedPages;

    protected Vector<TranslationEntry> tlb;

    protected static Lock pageLock=new Lock();

    /**
     * Allocate a new process.
     */
    public VMProcess() {
        super();
        vpnToSecNumAndCoffAddr = new Hashtable<>();
        allocatedPages= new ArrayList<>();
        tlb = new Vector<>(Machine.processor().getTLBSize());
        int i=0;
        while (i<Machine.processor().getTLBSize()) {
            tlb.add(i,new TranslationEntry(0, 0, false, false, false, false));
            i++;
        }
    }

    /**
     * Save the state of this process in preparation for a context switch.
     * Called by <tt>UThread.saveState()</tt>.
     */
    @Override
    public void saveState() {
        super.saveState();
        int i=0;
        while (i<Machine.processor().getTLBSize()) {
            tlb.set(i, Machine.processor().readTLBEntry(i));
            if(tlb.get(i).valid){
                InvertedPageTable.update(pID, tlb.get(i));
            }
            i++;
        }
    }

    /**
     * Restore the state of this process after a context switch. Called by
     * <tt>UThread.restoreState()</tt>.
     */
    @Override
    public void restoreState() {
//        super.restoreState();
        int i = 0;
        while (i< tlb.size()) {
            if (!tlb.get(i).valid) {
                Machine.processor().writeTLBEntry(i, new TranslationEntry(0,0,false,false,false,false));
            } else {
                Machine.processor().writeTLBEntry(i, tlb.get(i));
                //can be swapped out by other processes
                TranslationEntry entry= InvertedPageTable.get(pID, tlb.get(i).vpn);
                Machine.processor().writeTLBEntry(i, entry != null && entry.valid ? entry : new TranslationEntry(0, 0, false, false, false, false));
            }
            i++;
        }
    }

    /**
     * Initializes page tables for this process so that the executable can be
     * demand-paged.
     *
     * @return    <tt>true</tt> if successful.
     */
    @Override
    protected boolean loadSections() {
//        return super.loadSections();
        int s=0;
        while (s<coff.getNumSections()) {
            CoffSection section=coff.getSection(s);

            for(int i=0;i<section.getLength();i++){
                int vpn=section.getFirstVPN()+i;
                Pair<Integer,Integer> coffPageAddress=new Pair<>(s,i); //CoffPageAddress
                vpnToSecNumAndCoffAddr.put(vpn, coffPageAddress);

            }
            s++;
        }
        return true;
    }

    /**
     * Release any resources allocated by <tt>loadSections()</tt>.
     */
    @Override
    protected void unloadSections() {
        super.unloadSections();
    }

    /**
     * Handle a user exception. Called by
     * <tt>UserKernel.exceptionHandler()</tt>. The
     * <i>cause</i> argument identifies which exception occurred; see the
     * <tt>Processor.exceptionZZZ</tt> constants.
     *
     * @param    cause    the user exception that occurred.
     */
    @Override
    public void handleException(int cause) {
        Processor processor = Machine.processor();

        if (cause == Processor.exceptionTLBMiss) {
            int address = processor.readRegister(Processor.regBadVAddr);
            int vpn = Processor.pageFromAddress(address);
            Lib.debug(dbgVM, "\thandleException:TLB miss exception:address " + address + " virtual page " + vpn);
            pageLock.acquire();
            boolean isSuccessful = handleTLBFault(address);
            if (isSuccessful) {
                Lib.debug(dbgVM, "\thandleException:TLB miss handled sucessfully");
            } else {
                UThread.finish();
            }
            pageLock.release();
        } else {
            super.handleException(cause);
        }
    }

    protected boolean handleTLBFault(int vaddr){
        int vpn= Processor.pageFromAddress(vaddr);
        TranslationEntry entry = vaddrToPageTableEntry(vaddr) ;
        if(entry==null){
            return false;
        }
        if(!entry.valid){
            int ppn= getPage();
            swapIn(ppn,vpn);
            entry = pageTable[vpn];
        }
        int victim= getTLBReplace();
        replaceTLBEntry(victim,entry);
        return true;
    }
    protected void replaceTLBEntry(int index,TranslationEntry newEntry){
        TranslationEntry oldEntry=Machine.processor().readTLBEntry(index);
        if(oldEntry.valid){
            InvertedPageTable.update(pID, oldEntry);
        }
        Machine.processor().writeTLBEntry(index, newEntry);
    }

    protected int getTLBReplace(){
        int i=0;
        while (i<Machine.processor().getTLBSize()) {
            if(!Machine.processor().readTLBEntry(i).valid){
                return i;
            }
            i++;
        }
        return Lib.random(Machine.processor().getTLBSize());
    }

    protected void swapIn(int ppn,int vpn){
        TranslationEntry entry= InvertedPageTable.get(pID, vpn);
        if(entry==null){
            return;
        }
        if(entry.valid){
            return;
        }
        boolean dirty,used;

        if(vpnToSecNumAndCoffAddr.containsKey(vpn)){
            loadPage(vpn,ppn);
            dirty=true;
            used=true;
        }else{
            byte[] memory=Machine.processor().getMemory();
            byte[] page= DiskSwapSpace.read(pID, vpn);
            System.arraycopy(page, 0, memory, ppn*pageSize, pageSize);
            dirty=false;
            used=false;
        }
        TranslationEntry newEntry=new TranslationEntry(vpn,ppn,true,false,used,dirty);
        InvertedPageTable.set(pID, newEntry);

    }

    protected void swapOut(int pID,int vpn){
        //		PidAndVpn key=new PidAndVpn(pID,vpn);
        TranslationEntry entry= InvertedPageTable.get(pID, vpn);
        if(entry==null){
            return;
        }
        if(!entry.valid){
            return;
        }

        int i=0;
        while (i<Machine.processor().getTLBSize()) {
            TranslationEntry tlbEntry=Machine.processor().readTLBEntry(i);
            if (tlbEntry.vpn != entry.vpn || tlbEntry.ppn != entry.ppn || !tlbEntry.valid) {
                i++;
            } else {
                InvertedPageTable.update(pID, tlbEntry);
                entry = InvertedPageTable.get(pID, vpn);
                tlbEntry.valid = false;
                Machine.processor().writeTLBEntry(i, tlbEntry);
                break;
            }
        }
        if(entry.dirty){
            byte[] memory=Machine.processor().getMemory();
            DiskSwapSpace.write(pID, vpn, memory,entry.ppn*pageSize);
        }
    }

    protected void loadPage(int vpn, int ppn){

        Pair<Integer,Integer> coffPageAddress= vpnToSecNumAndCoffAddr.remove(vpn);
        if(coffPageAddress == null){
            return;
        }
        CoffSection section=coff.getSection(coffPageAddress.getFirst()); // Section Number
        section.loadPage(coffPageAddress.getSecond(), ppn); // Page Offset

    }

    protected int getPage() {
        int ppn = VMKernel.newPage();

        switch (ppn) {
            case -1:
                Pair<Integer, TranslationEntry> victim = InvertedPageTable.entryToDelete(); // Victim's processid and translation Entry
                ppn = victim.getSecond().ppn;
                swapOut(victim.getFirst(), victim.getSecond().vpn);
                break;
        }

        return ppn;
    }

    @Override
    public int readVirtualMemory(int vaddr,byte[] data,int offset,int length){
        pageLock.acquire();

        int vpn= Processor.pageFromAddress(vaddr);
        TranslationEntry entry= InvertedPageTable.get(pID, vpn);
        if(!entry.valid){
            //			Lib.debug(dbgVM, "\treadVirtualMemory:entry is invalid");
            int ppn= getPage();
            swapIn(ppn,vpn);
        }
        entry.used=true;
        InvertedPageTable.set(pID, entry);

        pageLock.release();

        return super.readVirtualMemory(vaddr, data, offset, length);
    }

    @Override
    public int writeVirtualMemory(int vaddr,byte[] data,int offset,int length){
        pageLock.acquire();

        int vpn= Processor.pageFromAddress(vaddr);
        swap(Processor.pageFromAddress(vaddr));
        TranslationEntry entry = vaddrToPageTableEntry(vaddr);
        if (entry != null) {
            entry.dirty = true;
            entry.used = true;
            InvertedPageTable.set(pID, entry);

            pageLock.release();

            return super.writeVirtualMemory(vaddr, data, offset, length);
        } else {
            return -1;
        }
    }

    protected void swap(int vpn) {
        TranslationEntry entry = hasEntry(vpn);
        //	        Lib.assertTrue(entry != null, "page " + vpn + " not in PageTable");

        if (entry.valid)
            return;

        int ppn = getPage();
        swapIn(ppn, vpn);

    }


    protected TranslationEntry hasEntry(int vpn) {
        return InvertedPageTable.get(pID ,vpn);
    }

    protected TranslationEntry vaddrToPageTableEntry(int vaddr) {
        return InvertedPageTable.get(pID,Processor.pageFromAddress(vaddr));
    }

    private static final int pageSize = Processor.pageSize;
    private static final char dbgProcess = 'a';
    private static final char dbgVM = 'v';
}
