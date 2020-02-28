package com.utk.ir.tcd;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

import org.apache.lucene.search.similarities.BM25Similarity;

import org.apache.lucene.search.similarities.ClassicSimilarity;

import org.apache.lucene.search.similarities.MultiSimilarity;

import org.apache.lucene.search.similarities.Similarity;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.en.EnglishAnalyzer;

public class Indexer {
	
	
	private static String INDEX_DIRECTORY = "./docs/index";
	private static String stop_words = "C:/docs/stopwords/stopwords.txt";
	private IndexWriter indexw;
	private static CharArraySet stopWordSet = new CharArraySet(1000, true);
	

    public void open() throws IOException {
        
    
    	 InputStream is = new FileInputStream(stop_words);
    	 InputStreamReader inputStreamReader = new InputStreamReader(is);
         BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
         
         while (bufferedReader.ready()) {
             stopWordSet.add(bufferedReader.readLine());
         }
         bufferedReader.close();
         
   
         EnglishAnalyzer analyzer = new EnglishAnalyzer(stopWordSet);

    	Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
        Similarity similarity[] = {
                new BM25Similarity(),
                new ClassicSimilarity()

        };

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        config.setSimilarity(new MultiSimilarity(similarity));
        indexw = new IndexWriter(directory, config);
    }
	
    
	public ArrayList<UIComponents> search(String cranQuery) throws IOException, ParseException {
        DirectoryReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX_DIRECTORY)));
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        Similarity similarity[] = {
                new BM25Similarity(),
                new ClassicSimilarity()
        };
        
        
        indexSearcher.setSimilarity(new MultiSimilarity(similarity));
        String fields[] = {"Title", "Description"};
        String queryString = QueryParser.escape(cranQuery);
        
   
        InputStream is = new FileInputStream(stop_words);
   	 	InputStreamReader inputStreamReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        
        while (bufferedReader.ready()) {
            stopWordSet.add(bufferedReader.readLine());
        }
        bufferedReader.close();
        EnglishAnalyzer analyzer = new EnglishAnalyzer(stopWordSet);
        
//        Analyzer analyzer = new EnglishAnalyzer(stopWordSet);
        QueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
        Query query = queryParser.parse(queryString);
        TopDocs topDocs = indexSearcher.search(query, 1000);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        ArrayList<UIComponents> resultDocs = new ArrayList<UIComponents>();
        int i = 1;
        
        for (ScoreDoc scoreDoc : scoreDocs) {
            String currentID = indexSearcher.doc(scoreDoc.doc).get("ID");
            UIComponents currentDoc = new UIComponents(currentID, scoreDoc.score, i++);
            resultDocs.add(currentDoc);
        }
        return resultDocs;
    }
	
	
	public void indexingDocuments(Cranfield crans) throws IOException{
		
        
        Document doc = new Document();
        
        doc.add(new StringField("ID", crans.getIdx(), Field.Store.YES));
        TextField titleField = new TextField("Title", crans.getTitle(), Field.Store.NO);
        
        doc.add(titleField);
        doc.add(new TextField("Authors", crans.getAuthors(), Field.Store.NO));
        doc.add(new TextField("Biblographical references", crans.getbiblo(), Field.Store.NO));
        doc.add(new TextField("Description", crans.getDescription(), Field.Store.YES));
        indexw.addDocument(doc);
        
       
	}
	
    public void close() throws IOException {
        indexw.close();
    }

}
