package edu.asupoly.ser422.restexample.services.impl;

import java.util.List;

import javax.ws.rs.core.Response;

import dk.nykredit.jackson.dataformat.hal.HALLink;

import java.util.ArrayList;

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.model.Subject;
import edu.asupoly.ser422.restexample.services.BooktownService;

//A simple impl of interface BooktownService
public class SimpleBooktownServiceImpl implements BooktownService {

	// Author section
	private final static String[] fnames = {"Laura", "Hillary", "Jackie",};
	private final static String[] lnames = {"Bush", "Clinton", "Kennedy"};
	private ArrayList<Author> __authors = null;

	public List<Author> getAuthors() {
		List<Author> deepClone = new ArrayList<Author>();
		for (Author a : __authors) {
			deepClone.add(new Author(a.getAuthorId(), a.getFirstName(), a.getLastName()));
		}
		return deepClone;
	}

	public int createAuthor(String lname, String fname) {
		int max = -1;
		for (Author a : __authors) {
			if (a.getAuthorId() > max) {
				max = a.getAuthorId();
			}
		}
		__authors.add(new Author(max+1, fname, lname));
		return max+1;
	}

	public boolean deleteAuthor(int authorId) {
		try {
			return (__authors.remove(authorId) != null);
		} catch (Exception exc) {
			return false;
		}
	}

	public Author getAuthor(int id) {
		for (Author a : __authors) {
			if (a.getAuthorId() == id) {
				return a;
			}
		}
		return null;
	}

	@Override
	public boolean updateAuthor(Author author) {
		boolean rval = false;
		for (Author a : __authors) {
			if (a.getAuthorId() == author.getAuthorId()) {
				rval = true;
				a.setFirstName(author.getFirstName());
				a.setLastName(author.getLastName());
			}
		}
		return rval;
	}

	// Book section
	private final static String[] titles = {"Sisters First", "My Turn", "Four Days"};
	private ArrayList<Book> __books = null;

	public List<Book> getBooks() {
		List<Book> deepClone = new ArrayList<Book>();
		for (Book b : __books) {
			deepClone.add(new Book(b.getBookId(), b.getTitle(), b.getAuthorId(), b.getSubjectId()));
		}
		return deepClone;
	}
	public Book getBook(int id) {
		for (Book b : __books) {
			if (b.getBookId() == id) {
				return b;
			}
		}
		return null;	
	}
	public List<Book> getBooksByAuthorID(int aid, String base) {
		List<Book> deepClone = new ArrayList<Book>();
		for (Book b : __books) {
			if (b.getAuthorId() == aid) {
				b.setSelf(new HALLink.Builder(base+"v2/books/"+b.getBookId()).build());
				deepClone.add(new Book(b.getBookId(),b.getTitle(),b.getAuthorId(), b.getSubjectId(),b.getSelf()));
			}
		}
		return deepClone;
	}
	public int createBook(String title, int aid, int sid) {
		int max = -1;
		for (Book b : __books) {
			if (b.getBookId() > max) {
				max = b.getBookId();
			}
		}
		__books.add(new Book((max+1), title, aid, sid));
		return max+1;
	}
	
	public Author findAuthorOfBook(int bookId) {
		Author a = null;
		Book b = getBook(bookId);
		if (b != null) {
			a = getAuthor(b.getAuthorId());
		}
		return a;
	}
	public Author findAuthorOfBook(int bookId, String base) {
		Author a = null;
		Book b = getBook(bookId);
		if (b != null) {
			a = getAuthor(b.getAuthorId());
		}
		a.setSelfLink(base+"v2/authors/"+b.getAuthorId());
		return a;
	}

	public boolean deleteBook(int id) {
		for (Book b : __books) {
			if (b.getBookId() == id) {
				__books.remove(id);
			}
		}
		return true;
	}
	// Subject section
	private final static String[] subjects = {"Humor", "Politics", "Drama"};
	private final static String[] locations = {"Midland, TX", "Little Rock, AR", "Dallas, TX"};
	private ArrayList<Subject> __subjects = null;

	public List<Subject> getSubjects() {
		List<Subject> deepClone = new ArrayList<Subject>();
		for (Subject s : __subjects) {
			deepClone.add(new Subject(s.getSubjectId(), s.getSubject(), s.getLocation()));
		}
		return deepClone;
	}
	public Subject getSubject(int id) {
		for (Subject s : __subjects) {
			if (s.getSubjectId() == id) {
				return s;
			}
		}
		return null;
	}
	public Subject getSubject(int id, String base) {
		for (Subject s : __subjects) {
			if (s.getSubjectId() == id) {
				s.setSelfLink(base+"v2/subjects/"+s.getSubjectId());
				return s;
			}
		}
		return null;
	}
	public List<Book> findBooksBySubject(int subjectId) {
		List<Book> deepClone = new ArrayList<Book>();
		for (Book s : __books) {
			if(s.getSubjectId()==subjectId)
				deepClone.add(new Book(s.getBookId(),s.getTitle(),s.getAuthorId(),s.getSubjectId()));
		}
		return deepClone;
	}
	public List<Book> findBooksBySubject(int subjectId, String base) {
		List<Book> deepClone = new ArrayList<Book>();
		for (Book s : __books) {
			if(s.getSubjectId()==subjectId)
			{	
				s.setSelf(new HALLink.Builder(base).build());
				deepClone.add(new Book(s.getBookId(),s.getTitle(),s.getAuthorId(),s.getSubjectId()));
			}
		}
		return deepClone;
	}

	public boolean updateLocation(String location, int id) {
		{
			boolean rval = false;
			for (Subject a : __subjects) {
				if (a.getSubjectId() == id) {
					rval = true;
					a.setLocation(location);
				}
			}
			return rval;
		}
	}

	// Only instantiated by factory?
	public SimpleBooktownServiceImpl() {
		__authors = new ArrayList<Author>();
		__books = new ArrayList<Book>();
		__subjects = new ArrayList<Subject>();
		for (int i = 0; i < fnames.length; i++) {
			Author a = new Author(i, fnames[i], lnames[i]);
			__authors.add(a);
			Subject s = new Subject(i, subjects[i], locations[i]);
			__subjects.add(s);
			__books.add(new Book(i, titles[i], a.getAuthorId(), s.getSubjectId()));
		}
	}

	public List<Author> getAuthorList(String location) {
		List<Author> deepClone = new ArrayList<Author>();
		List<Book> bookClone = new ArrayList<Book>();		
		for (Subject s : __subjects) {
			if(s.getLocation().contains(location)) {
				bookClone.addAll(findBooksBySubject(s.getSubjectId()));
				for(Book book: bookClone) {
					deepClone.add(findAuthorOfBook(book.getBookId()));
				}
			}
		}
		return deepClone;

	}

	public List<Subject> getSubjectsByAuthorID(int aid, String base) {
		List<Subject> deepClone = new ArrayList<Subject>();
		List<Book> bookClone = getBooksByAuthorID(aid, base);		

		for (Book s : bookClone) {
			Subject sub = getSubject(s.getSubjectId());
			sub.setSelfLink(base+"v2/subjects/"+sub.getSubjectId());
			deepClone.add(sub);
		}
		return deepClone;
	}
	
	public List<Author> getAuthorsBySubjectID(int aid, String base) {
		List<Author> deepClone = new ArrayList<Author>();
		List<Book> bookClone = findBooksBySubject(aid, base);		

		for (Book s : bookClone) {
			Author sub = getAuthor(s.getAuthorId());
			sub.setSelfLink(base+"v2/authors/"+sub.getAuthorId());
			deepClone.add(sub);
		}
		return deepClone;
	}
}
