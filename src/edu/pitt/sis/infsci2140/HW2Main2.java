package edu.pitt.sis.infsci2140;

import java.io.IOException;

import edu.pitt.sis.infsci2140.index.MyIndexReader;

/**
 * !!! YOU CANNOT CHANGE ANYTHING IN THIS CLASS !!!
 * 
 * Main class for running your HW2.
 * 
 */
public class HW2Main2 {
	
	public static void main(String[] args) {
		
		args = new String[] {
				"docset.trectext.index",
				"information"
			};
		
		if( args==null || args.length<2 ) {
			System.out.println("Usage:");
			System.out.println("  args[0]: path of the index's directory.");
			System.out.println("  args[1]: a token to be searched for.");
			System.exit(0);
		}
		long begin = System.nanoTime();
		String path_dir = args[0];
		String token = args[1];
		
		MyIndexReader ixreader = null;
		try {
			// Initiate the index reader ...
			ixreader = new MyIndexReader(path_dir);
		}catch(Exception e){
			System.out.println("ERROR: cannot initiate index directory.");
			e.printStackTrace();
		}
		
		try{
			int df = ixreader.DocFreq(token);
			long ctf = ixreader.CollectionFreq(token);
			System.out.println(" >> the token \""+token+"\" appeared in "+df+" documents and "+ctf+" times in total");
			if(df>0){
				long[][] posting = ixreader.getPostingList(token);
				for(int ix=0;ix<posting.length;ix++){
					long docid = posting[ix][0];
					long freq = posting[ix][1];
					String docno = ixreader.getDocno(docid);
					System.out.printf("    %20s    %6d    %6d\n", docno, docid, freq);
				}
			}
		}catch(IOException e){
			System.err.println(" >> cannot read index ");
			e.printStackTrace();
		}
		
		try{
			ixreader.close();
		}catch(Exception e){}
		long end = System.nanoTime();
		System.out.println("Loading time: " + (end - begin) + " ns");
	}
	
}
