package com.utk.ir.tcd;


public class queryDocs {
	 private String idx;
	    private String query;

	    
	    public queryDocs(String idx, String query) {
	        this.idx = idx;
	        this.query = query;
	    }

	    
	    public String getID() {
	        return idx;
	    }

	    
	    public String getQuery() {
	        return query;
	    }
}
