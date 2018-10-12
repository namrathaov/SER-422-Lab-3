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
public class Book {
	@Link
	HALLink self;
	
	@Link("rel:subject")
    HALLink subjectRel;
	
	@EmbeddedResource
	Subject subject;
	
	@EmbeddedResource
	Author author;
	
	@Link("rel:author")
	HALLink authorRel;

	@Link("rel:bookSubjectRel")
	HALLink bookSubjectRel = new HALLink();
	
	@Link("rel:bookAuthorRel")
	HALLink bookAuthorRel = new HALLink();
	
	private int id;
	private String title;
	private int authorId;
	private int subjectId;
	
	public Book() {	
	}
	public Book(int id, String t, int aid, int sid) {
		this.id = id;
		this.title = t;
		this.authorId = aid;
		this.subjectId = sid;
	}
	public Book(int id, String t, int aid, int sid, String self) {
		this.id = id;
		this.title = t;
		this.authorId = aid;
		this.subjectId = sid;
		this.self =new HALLink.Builder(self).build();
	}
	public Book(int id, String t, int aid, int sid, HALLink self) {
		this.id = id;
		this.title = t;
		this.authorId = aid;
		this.subjectId = sid;
		this.self =self;
	}
	
	public int getBookId() {
		return id;
	}
	public void setBookId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}
	
	public HALLink getSelf() {
        return this.self;
    }
	public void setSelf(HALLink link) {
        this.self = link;
    }
	public HALLink getBookSubjectRel() {
		return this.bookSubjectRel;
	}
	public void setBookSubjectRel(Subject subject) {
			this.bookSubjectRel = new HALLink.Builder(subject.getSelfLink()).build();
			//this.authoredBookRel.add(new HALLink.Builder("http://localhost/rest/1").build());	
		}
	public HALLink getBookAuthorRel() {
		return this.bookAuthorRel;
	}
	public void setBookAuthorRel(Author author) {
			this.bookAuthorRel = author.getSelfLink();
			System.out.println(author.getSelfLink());
			//this.authoredBookRel.add(new HALLink.Builder("http://localhost/rest/1").build());	
		}
	public HALLink getSubjectRel() {
		return (HALLink) this.subjectRel;
	}
	public void setSubjectRel(HALLink link) {
		this.subjectRel = link;
	}
	public HALLink getAuthorRel() {
		return (HALLink) this.authorRel;
	}
	public void setAuthorRel(HALLink link) {
		this.authorRel = link;
	}
}
