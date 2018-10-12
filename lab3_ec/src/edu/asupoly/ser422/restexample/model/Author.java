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
public class Author {
	public Author() {}

	public Author(int id, String lname, String fname) {
		__id = id;
		__lastName  = lname;
		__firstName = fname;
	}
	public int getAuthorId() {
		return __id;
	}
	public String getLastName() {
		return __lastName;
	}
	public String getFirstName() {
		return __firstName;
	}
	public void setAuthorId(int __id) {
		this.__id = __id;
	}
	public void setLastName(String __lastName) {
		this.__lastName = __lastName;
	}
	public void setFirstName(String __firstName) {
		this.__firstName = __firstName;
	}

	public String toString() {
		return "Author ID " + getAuthorId() + ", lastName " + getLastName() + ", firstName " + getFirstName();
	}
	public HALLink getSelf() {
		return (HALLink) this.self;
	}
	public void setSelf(HALLink link) {
		this.self = link;
	}
	public HALLink getSelfLink() {
		return this.self;
	}
	public void setSelfLink(String link) {
		this.self = new HALLink.Builder(link).build();
	}
	public HALLink getBookRel() {
		return (HALLink) this.bookRel;
	}
	public void setBookRel(HALLink link) {
		this.bookRel = link;
	}
	public List<HALLink> getAuthoredBookRel() {
		return this.authoredBookRel;
	}
	public void setAuthoredBookRel(List<Book> bookList) {
		this.authoredBookRel.clear();
		for(int i=0;i<bookList.size();i++)
		{
			this.authoredBookRel.add((bookList.get(i).getSelf()));
			//this.authoredBookRel.add(new HALLink.Builder("http://localhost/rest/1").build());	
		}

	}
	public HALLink getSubjectRel() {
		return (HALLink) this.subjectRel;
	}
	public void setSubjectRel(HALLink link) {
		this.subjectRel = link;
	}
	public List<HALLink> getAuthoredSubjectRel() {
		return this.authoredSubjectRel;
	}
	public void setAuthoredSubjectRel(List<Subject> subjectList) {
		this.authoredSubjectRel.clear();
		for(int i=0;i<subjectList.size();i++)
		{
			this.authoredSubjectRel.add(new HALLink.Builder((subjectList.get(i)).getSelfLink()).build());
		//	System.out.println(subjectList.get(i).getSelfLink());
			//this.authoredBookRel.add(new HALLink.Builder("http://localhost/rest/1").build());	
		}

	}

	private int    __id;
	private String __lastName;
	private String __firstName;
	@Link
	HALLink self;

	@Link("rel:book")
	HALLink bookRel;


	@Link("rel:authoredBookRel")
	List<HALLink> authoredBookRel = new ArrayList<HALLink>();


	//String newLink = getAuthoredBookRel().get(0);
	//List<HALLink> authoredBookRel = new ArrayList<HALLink>();



	@EmbeddedResource
	Book book;

	@Link("rel:subject")
	HALLink subjectRel;
	
	@Link("rel:authoredSubjectRel")
	List<HALLink> authoredSubjectRel = new ArrayList<HALLink>();

	@EmbeddedResource
	Subject subject;

}
