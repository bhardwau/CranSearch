package com.utk.ir.tcd;
import com.utk.ir.tcd.MainCranSearcher;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Parser {
	private String pathDocs ;
	private String pathQuery;
	
	public Parser(String pathDocs, String pathQuery) {
		this.pathDocs = pathDocs;
		this.pathQuery = pathQuery;
		
	}
	
// Parsing documents on the basis of index
	public void documents() throws IOException{
		ClassLoader classloader = new MainCranSearcher().getClass().getClassLoader();
		InputStream is = new FileInputStream(classloader.getResource("cran.all.1400").getFile());
		InputStreamReader isr = new InputStreamReader(is);
	
		BufferedReader br = new BufferedReader(isr);
		
		String docs = br.readLine();
		
		int count = 1;
		
		while(docs != null) {
			FileWriter fr = new FileWriter(pathDocs + File.separator + "cran.doc_ID" + count + ".txt");
			fr.append(docs).append("\n");
			docs = br.readLine();
			while(docs != null && !docs.startsWith(".I")) {
				fr.append(docs).append("\n");
				docs = br.readLine();
			}
			count ++;
			fr.close();
			
			
		}
		br.close();
	}
	
	//function to make documents on the basis of Authors, title, and description text
    public Cranfield makeDoc(int idx) throws IOException {
        FileReader fr = new FileReader(new File(pathDocs + File.separator + "cran.doc_ID" + idx +".txt") );
        BufferedReader br = new BufferedReader(fr);
        String docs = br.readLine();
        docs = br.readLine();
        StringBuilder title = new StringBuilder();
        StringBuilder authors = new StringBuilder();
        StringBuilder biblo = new StringBuilder();
        StringBuilder desc = new StringBuilder();
        while (docs != null) {
            switch (docs) {
                case ".T":
                    docs = br.readLine();
                    while (!docs.equals(".A")) {
                        title.append(docs).append("\n");
                        docs = br.readLine();
                    }
                    break;
                case ".A":
                    docs = br.readLine();
                    while (!docs.equals(".B")) {
                        authors.append(docs).append("\n");
                        docs = br.readLine();
                    }
                    break;
                case ".B":
                    docs = br.readLine();
                    while (!docs.equals(".W")) {
                        biblo.append(docs).append("\n");
                        docs = br.readLine();
                    }
                    break;
                case ".W":
                    docs = br.readLine();
                    while (docs != null) {
                        desc.append(docs).append("\n");
                        docs = br.readLine();
                    }
                    break;
                default:
                    break;
            }
        }
        br.close();
        String currentId = Integer.toString(idx);
        return new Cranfield(currentId, title.toString(), authors.toString(), biblo.toString(), desc.toString());
    }
	
   // Creating queries on the basis of index and query 
    public void createQueries() throws IOException {
    	ClassLoader classloader = new MainCranSearcher().getClass().getClassLoader();
		InputStream is = new FileInputStream(classloader.getResource("cran.qry").getFile());
    	InputStreamReader isr = new InputStreamReader(is);
       
        BufferedReader br = new BufferedReader(isr);
        String queries = br.readLine();
        int count = 1;
        while (queries != null) {
            FileWriter fw = new FileWriter(pathQuery + File.separator + "cran.qry." + count +".txt");
            fw.append(queries).append("\n");
            queries = br.readLine();
            while (queries != null && !queries.startsWith(".I")) {
                fw.append(queries).append("\n");
                queries = br.readLine();
            }
            count++;
            fw.close();
        }
        br.close();
    }
    
    public queryDocs queryMaker(int idx) throws IOException{
    	FileReader fr = new FileReader(new File(pathQuery + File.separator + "cran.qry." + idx +".txt"));
    	BufferedReader br = new BufferedReader(fr );
    	String queries = br.readLine();
    	StringBuilder desc = new StringBuilder();
    	
    	while(queries != null) {
    		desc.append(queries).append("\n");
    		queries = br.readLine();
    		
    	}
    	br.close();
    	String cIdx  = Integer.toString(idx);
    	return new queryDocs(cIdx, desc.toString());
    	
    	
    }
    
    
}
