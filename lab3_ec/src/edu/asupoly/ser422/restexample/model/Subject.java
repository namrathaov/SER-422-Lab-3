package edu.asupoly.ser422.restexample.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import dk.nykredit.jackson.dataformat.hal.HALLink;
import dk.nykredit.jackson.dataformat.hal.annotation.EmbeddedResource;
import dk.nykredit.jackson.dataformat.hal.annotation.Link;
import dk.nykredit.jackson.dataformat.hal.annotation.Resource;

@XmlRootElement
@Resource
public class Subject {
	private int id;
	private String subject;
	private String location;
	private String selfLink;
	
	public Subject (int id, String s, String l) {
		this.id = id; 
		subject = s;
		location = l;
	}
	public Subject () {
		
	}
	public int getSubjectId() {
		return id;
	}
	public String getSubject() {
		return subject;
	}
	public String getLocation() {
		return location;
	}
	public void setSubjectId(int id) {
		this.id = id;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setLocation(String location) {
		this.location=location;
	}
	public String getSelfLink() {
		return this.selfLink;
	}
	public void setSelfLink(String base) {
		this.selfLink = base;
	}
	public void setSelf(HALLink link) {
		this.self = link;
	}
	public HALLink getSelf() {
		return this.self;
	}
	public void setSelf(String link) {
		this.self = new HALLink.Builder(link).build();
	}
	public HALLink getBookRel() {
		return (HALLink) this.bookRel;
	}
	public void setBookRel(HALLink link) {
		this.bookRel = link;
	}
	public List<HALLink> getSubjectBookRel() {
		return this.subjectBookRel;
	}
	public void setSubjectBookRel(List<Book> bookList) {
		this.subjectBookRel.clear();
		for(int i=0;i<bookList.size();i++)
		{
			this.subjectBookRel.add(bookList.get(i).getSelf());
			//this.authoredBookRel.add(new HALLink.Builder("http://localhost/rest/1").build());	
		}

	}
	public HALLink getAuthorRel() {
		return (HALLink) this.authorRel;
	}
	public void setAuthorRel(HALLink link) {
		this.authorRel = link;
	}
	public List<HALLink> getSubjectAuthorRel() {
		return this.subjectAuthorRel;
	}
	public void setSubjectAuthorRel(List<Author> authorList) {
		this.subjectAuthorRel.clear();
		for(int i=0;i<authorList.size();i++)
		{
			this.subjectAuthorRel.add(authorList.get(i).getSelfLink());
		//	System.out.println(subjectList.get(i).getSelfLink());
			//this.authoredBookRel.add(new HALLink.Builder("http://localhost/rest/1").build());	
		}

	}
	@Link
	HALLink self;

	@Link("rel:book")
	HALLink bookRel;


	@Link("rel:subjectBookRel")
	List<HALLink> subjectBookRel = new ArrayList<HALLink>();


	//String newLink = getAuthoredBookRel().get(0);
	//List<HALLink> authoredBookRel = new ArrayList<HALLink>();



	@EmbeddedResource
	Book book;

	@Link("rel:author")
	HALLink authorRel;
	
	@Link("rel:subjectAuthorRel")
	List<HALLink> subjectAuthorRel = new ArrayList<HALLink>();

	@EmbeddedResource
	Author author;

}
