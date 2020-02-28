package com.utk.ir.tcd;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.lucene.queryparser.classic.ParseException;


public class SearchEngine {

	public static void main(String[] args) throws IOException, ParseException{
		// TODO Auto-generated method stub
		String docs_path = "./Docs/crandocs";
		String queries_path_directory = "./Docs/queries";
		String results_path = "./Docs/results";
		
		Parser par = new Parser(docs_path,queries_path_directory);
		Indexer indx = new Indexer();
		
		//calling indexing open function to initilize analyzer and scoring methods
		indx.open();
		par.documents();
		File docsDirectory = new File(docs_path);
		File[] filesDoc =  docsDirectory.listFiles();
		
		//indexing documents
		int count = 0 ;
		 for (File file : Objects.requireNonNull(filesDoc)) {
	            if (file.isFile()) {
	                indx.indexingDocuments(par.makeDoc(++count));
	            }
	        }
		
		 indx.close();
		 par.createQueries();
		 
		 File queryDirectory = new File(queries_path_directory);
		 File[] filesQuery =  queryDirectory.listFiles();
		 FileWriter res = new FileWriter(results_path);
		 int count1 = 1;
		 //querying documents and storing the result in the results file
	       while (count1 < Objects.requireNonNull(filesQuery).length) {
	            queryDocs query = par.queryMaker(count1++);
	            String result = "";
	            String queryString = query.getQuery();
	            ArrayList<UIComponents> resultList = indx.search(queryString);
	            for (UIComponents currentResult : resultList) {
	                res.append(query.getID()).append(" 0 ").append(currentResult.getID());
	                res.append(" ").append(String.valueOf(currentResult.getRank())).append(" ");
	                res.append(String.valueOf(currentResult.getScore())).append(" exp_0").append("\n");
	            }
	            res.write(result);
	        }
	       System.out.print("Done");
	        res.close();
		
		
		

	}

}
