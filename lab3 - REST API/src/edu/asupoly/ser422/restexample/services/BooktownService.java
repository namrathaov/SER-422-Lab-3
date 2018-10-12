package edu.asupoly.ser422.restexample.services;

import java.util.List;

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.model.Subject;

// we'll build on this later
public interface BooktownService {
	// Author methods
    public List<Author> getAuthors();
    public Author getAuthor(int id);
    public boolean deleteAuthor(int authorId);
    public int createAuthor(String lname, String fname);
    public boolean updateAuthor(Author author);
    
    // Book methods
    public List<Book> getBooks();
    public Book getBook(int id);
    public int createBook(String title, int aid, int sid);
    public Author findAuthorOfBook(int bookId);
    public boolean deleteBook(int bid);
    
    // Subject methods
    public List<Subject> getSubjects();
    public Subject getSubject(int id);
    public List<Book> findBooksBySubject(int subjectId);
    public boolean updateLocation(String location, int id);
	public List<Author> getAuthorList(String location);
	public List<Book> getBooksByAuthorID(int authorId, String base);
	public List<Subject> getSubjectsByAuthorID(int authorId, String base);
	public Subject getSubject(int bookId, String base);
	public Author findAuthorOfBook(int bookId, String base);
	public List<Book> findBooksBySubject(int subjectId, String base);
	public List<Author> getAuthorsBySubjectID(int subjectId, String base);
}
