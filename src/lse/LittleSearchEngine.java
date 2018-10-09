package lse;

import java.io.*;
import java.util.*;

/**
 * This class builds an index of keywords. Each keyword maps to a set of pages in
 * which it occurs, with frequency of occurrence in each page.
 *
 */
public class LittleSearchEngine {
	
	/**
	 * This is a hash table of all keywords. The key is the actual keyword, and the associated value is
	 * an array list of all occurrences of the keyword in documents. The array list is maintained in 
	 * DESCENDING order of frequencies.
	 */
	HashMap<String,ArrayList<Occurrence>> keywordsIndex;
	
	/**
	 * The hash set of all noise words.
	 */
	HashSet<String> noiseWords;
	
	/**
	 * Creates the keyWordsIndex and noiseWords hash tables.
	 */
	public LittleSearchEngine() {
		keywordsIndex = new HashMap<String,ArrayList<Occurrence>>(1000,2.0f);
		noiseWords = new HashSet<String>(100,2.0f);
	}
	
	/**
	 * Scans a document, and loads all keywords found into a hash table of keyword occurrences
	 * in the document. Uses the getKeyWord method to separate keywords from other words.
	 * 
	 * @param docFile Name of the document file to be scanned and loaded
	 * @return Hash table of keywords in the given document, each associated with an Occurrence object
	 * @throws FileNotFoundException If the document file is not found on disk
	 */
	public HashMap<String,Occurrence> loadKeywordsFromDocument(String docFile) 
	throws FileNotFoundException {
		/** COMPLETE THIS METHOD **/
		
		HashMap<String,Occurrence> keywords = new HashMap<String,Occurrence>();
		
		//scanner to traverse the file
		Scanner sc = new Scanner(new File(docFile));
		if(sc == null) {
			throw new FileNotFoundException();
		}
		while(sc.hasNext()) {
			String word = sc.next();
			word = getKeyword(word); //transform to a keyword
			
			if(word != null) {//make sure its not null
				if(!keywords.containsKey(word)) {
					//have to make a new occurrence
					Occurrence freq = new Occurrence(docFile,1);
					keywords.put(word, freq);
				}else {
					//increment the frequency field of the occurrence
					keywords.get(word).frequency++;
				}
			}
		}
		return keywords;
		
	}
	
	/**
	 * Merges the keywords for a single document into the master keywordsIndex
	 * hash table. For each keyword, its Occurrence in the current document
	 * must be inserted in the correct place (according to descending order of
	 * frequency) in the same keyword's Occurrence list in the master hash table. 
	 * This is done by calling the insertLastOccurrence method.
	 * 
	 * @param kws Keywords hash table for a document
	 */
	public void mergeKeywords(HashMap<String,Occurrence> kws) {
		/** COMPLETE THIS METHOD **/
		//for each keyword in the doc
		for(String str : kws.keySet()) {
			if(keywordsIndex.containsKey(str)) {
				//get the arraylist
				ArrayList<Occurrence> list = keywordsIndex.get(str);
				list.add(kws.get(str));
				insertLastOccurrence(list);//sorts the list?
				keywordsIndex.put(str, list);
				
			}else {
				//if it's not there yet
				ArrayList<Occurrence> list = new ArrayList<Occurrence>();
				list.add(kws.get(str));
				insertLastOccurrence(list);
				keywordsIndex.put(str, list);
			}
			//still needs to make sure it goes in order
		}
		
		
		
	}
	
	/**
	 * Given a word, returns it as a keyword if it passes the keyword test,
	 * otherwise returns null. A keyword is any word that, after being stripped of any
	 * trailing punctuation, consists only of alphabetic letters, and is not
	 * a noise word. All words are treated in a case-INsensitive manner.
	 * 
	 * Punctuation characters are the following: '.', ',', '?', ':', ';' and '!'
	 * 
	 * @param word Candidate word
	 * @return Keyword (word without trailing punctuation, LOWER CASE)
	 */
	public String getKeyword(String word) {
		/** COMPLETE THIS METHOD **/
		word = word.toLowerCase();
		
		while(word.length()>= 1 && !Character.isLetter(word.charAt(0))) {//strips non alphabetic chars in the beginning
			if(word.length()>1) {
				word = word.substring(1);
			}else {
				word = "";
			}		
		}
		
		while(word.length()>=1 && !Character.isLetter(word.charAt(word.length()-1))) {//strip from back
			if(word.length()>1) {
				word = word.substring(0,word.length()-1);
			}else {
				word = "";
			}
			
		}
		
		if(word.length()==0) {
			return null;
		}
		
		for(int i=0; i<word.length(); i++) {//check for punct in the middle
			if(!Character.isLetter(word.charAt(i))) {
				return null;
			}
		}
		
		if(noiseWords.contains(word)) {
			return null;
		}
		//System.out.println(word);
		return word;
	}
	
	/**
	 * Inserts the last occurrence in the parameter list in the correct position in the
	 * list, based on ordering occurrences on descending frequencies. The elements
	 * 0..n-2 in the list are already in the correct order. Insertion is done by
	 * first finding the correct spot using binary search, then inserting at that spot.
	 * 
	 * @param occs List of Occurrences
	 * @return Sequence of mid point indexes in the input list checked by the binary search process,
	 *         null if the size of the input list is 1. This returned array list is only used to test
	 *         your code - it is not used elsewhere in the program.
	 */
	public ArrayList<Integer> insertLastOccurrence(ArrayList<Occurrence> occs) {
		/** COMPLETE THIS METHOD **/
		ArrayList<Integer> midPts = new ArrayList<Integer>();
		ArrayList<Integer> fr = new ArrayList<Integer>();
		Occurrence last = occs.get(occs.size()-1);
		int freq = last.frequency;
		
		
		
		
		int l = 0;
		int r = occs.size()-2;
		int mid = 0;
		while(l<=r) {
			mid = (l+r)/2;
			midPts.add(mid);
			if(occs.get(mid).frequency<freq) {
				r = mid-1;
			}else if(occs.get(mid).frequency>freq) {
				l = mid+1;
			}else if(occs.get(mid).frequency == freq) {
				break;
			}
		}
		
		if(occs.get(mid).frequency >= freq) {//insert after it 
			occs.add(mid+1, last);
		}else {
			if(mid==0) {
				occs.add(0, last);
			}else {
				occs.add(mid-1, last);
			}
		}
		
		occs.remove(occs.size()-1);//delete the last one
		/////////////////////////try seq
	
		
		
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		
		return midPts;
	}
	
	/**
	 * This method indexes all keywords found in all the input documents. When this
	 * method is done, the keywordsIndex hash table will be filled with all keywords,
	 * each of which is associated with an array list of Occurrence objects, arranged
	 * in decreasing frequencies of occurrence.
	 * 
	 * @param docsFile Name of file that has a list of all the document file names, one name per line
	 * @param noiseWordsFile Name of file that has a list of noise words, one noise word per line
	 * @throws FileNotFoundException If there is a problem locating any of the input files on disk
	 */
	public void makeIndex(String docsFile, String noiseWordsFile) 
	throws FileNotFoundException {
		// load noise words to hash table
		Scanner sc = new Scanner(new File(noiseWordsFile));
		while (sc.hasNext()) {
			String word = sc.next();
			noiseWords.add(word);
		}
		
		// index all keywords
		sc = new Scanner(new File(docsFile));
		while (sc.hasNext()) {
			String docFile = sc.next();
			HashMap<String,Occurrence> kws = loadKeywordsFromDocument(docFile);
			mergeKeywords(kws);
		}
		sc.close();
	}
	
	/**
	 * Search result for "kw1 or kw2". A document is in the result set if kw1 or kw2 occurs in that
	 * document. Result set is arranged in descending order of document frequencies. (Note that a
	 * matching document will only appear once in the result.) Ties in frequency values are broken
	 * in favor of the first keyword. (That is, if kw1 is in doc1 with frequency f1, and kw2 is in doc2
	 * also with the same frequency f1, then doc1 will take precedence over doc2 in the result. 
	 * The result set is limited to 5 entries. If there are no matches at all, result is null.
	 * 
	 * @param kw1 First keyword
	 * @param kw1 Second keyword
	 * @return List of documents in which either kw1 or kw2 occurs, arranged in descending order of
	 *         frequencies. The result size is limited to 5 documents. If there are no matches, returns null.
	 */
	public ArrayList<String> top5search(String kw1, String kw2) {
		/** COMPLETE THIS METHOD **/
		ArrayList<String> results = new ArrayList<String>();
		ArrayList<Occurrence> first = keywordsIndex.get(kw1);
		ArrayList <Occurrence> sec = keywordsIndex.get(kw2);
		
		int f = 0; //index for first list
		int s = 0; //index for sec list
		int count = 0; //# of docs in results
	
		if(first == null && sec == null) {
			return null;
		}else if(first == null) {
			while(count<5 && s<sec.size()) {
				results.add(sec.get(s).document);
				s++;
			}
		}else if(sec==null) {
			while(count<5 && f<first.size()) {
				results.add(first.get(f).document);
				f++;
			}
		}else {
			while(f<first.size() && s<sec.size() && count<5){
				if(first.get(f).frequency < sec.get(s).frequency) {
					if(!results.contains(sec.get(s).document)) {
						results.add(sec.get(s).document);
						count++;
					}
					s++;
				}else if(first.get(f).frequency > sec.get(s).frequency) {
					if(!results.contains(first.get(f).document)) {
						results.add(first.get(f).document);
						count++;
					}
					f++;
				}else {
					if(!results.contains(first.get(f).document)) {
						results.add(first.get(f).document);
						count++;
						f++;
					}else if(!results.contains(sec.get(s).document)) {
						results.add(sec.get(s).document);
						count++;
						s++;
					}
					
				}
			}
			
			while(count<5 && (f<first.size() || s<sec.size())) {
				if(f==first.size()) {//end of first list, move on to sec
					if(!results.contains(sec.get(s).document)) {
						results.add(sec.get(s).document);
						count++;
					}
					s++;
				}else {//end of sec list, copy first
					if(!results.contains(first.get(f).document)) {
						results.add(first.get(f).document);
						count++;
					}
					f++;
				}
			}
		}
		
		
		
		// following line is a placeholder to make the program compile
		// you should modify it as needed when you write your code
		return results;
	
	}
}
