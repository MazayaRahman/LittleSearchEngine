package lse;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SearchTest {
	
static Scanner sc = new Scanner(System.in);

static String getOption() 
{
	System.out.print("getKeyWord(): ");

	String response = sc.next();

	return response;

}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
				/*LittleSearchEngine search = new LittleSearchEngine();
				System.out.println("Enter a file: ");
				String firstFile = sc.nextLine();
				System.out.println("Enter another file: ");
				String secFile = sc.nextLine();
				System.out.println("Enter another file: ");
				String thFile = sc.nextLine();
				
				System.out.println("Enter noise file: ");
				String noiseFile = sc.nextLine();
				Scanner noise = new Scanner(new File(noiseFile));
				
				//fill up noiseWords hashset with noiseWord file
				while(noise.hasNext()) {
					search.noiseWords.add(noise.next());
				}
				
				HashMap<String,Occurrence> firstKeys = search.loadKeywordsFromDocument(firstFile);
				
				System.out.println(firstKeys.keySet());
				System.out.println(firstKeys);
				
				HashMap<String,Occurrence> secKeys = search.loadKeywordsFromDocument(secFile);
				
				System.out.println(secKeys.keySet());
				System.out.println(secKeys);
				
				HashMap<String,Occurrence> thKeys = search.loadKeywordsFromDocument(thFile);
				
				System.out.println(thKeys.keySet());
				System.out.println(thKeys);
				
				search.mergeKeywords(firstKeys); search.mergeKeywords(secKeys);
				search.mergeKeywords(thKeys);
				
				System.out.println(search.keywordsIndex);
				/*
				
				System.out.println("Please enter the file with all docs: ");
				String doc = sc.nextLine();
				System.out.println("please enter the noise file: ");
				String noise = sc.nextLine();
				
				search.makeIndex(doc, noise);
				
				ArrayList<String> result = search.top5search("deep", "world");

				//print the result
				if(result==null) {
					System.out.println("Null");
				}else {
					System.out.println("The search results are: ");
					for(String str : result) {
						System.out.println(str);
					}
				}*/
		
	//////////////////////////////////////////////////////////////////	
		
LittleSearchEngine lse = new LittleSearchEngine();

		

		try

		{

			lse.makeIndex("docTest.txt", "noisewords.txt");

		} 

		catch (FileNotFoundException e)

		{

		}		

		

//		String input;

//

		for (String hi : lse.keywordsIndex.keySet())

			System.out.println(hi+" "+lse.keywordsIndex.get(hi));
//				

//		while (!(input = getOption()).equals("q"))

//		{

//				System.out.println(lse.getKeyWord(input));

//		}	

		System.out.println(lse.top5search("smile", "next"));
		
		System.out.println(lse.keywordsIndex.get("smile"));
		System.out.println(lse.keywordsIndex.get("next"));


	}

}
