package com.utk.ir.tcd;

public class Cranfield {
	private String idx;
	private String title;
	private String authors;
	private String biblo;
	private String description;
	
	public Cranfield(String idx, String title, String authors, String biblo, String description ) {
		this.idx = idx;
		this.title = title;
		this.authors = authors;
		this.biblo = biblo;
		this.description = description;
		
		
		
	}
	
	public String getIdx() {
		return idx;
	}
	public String getTitle() {
		return title;
	}
	public String getAuthors() {
		return authors;
	}
	public String getbiblo() {
		return biblo;
	}
	public String getDescription() {
		return description;
	}
}
