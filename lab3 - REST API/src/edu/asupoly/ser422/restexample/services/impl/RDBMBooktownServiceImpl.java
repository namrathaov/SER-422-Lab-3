package edu.asupoly.ser422.restexample.services.impl;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.model.Subject;
import edu.asupoly.ser422.restexample.services.BooktownService;

import java.util.Properties;

import dk.nykredit.jackson.dataformat.hal.HALLink;

//A simple impl of interface BooktownService
public class RDBMBooktownServiceImpl implements BooktownService {
	private static Properties __dbProperties;
	private static String __jdbcUrl;
	private static String __jdbcUser;
	private static String __jdbcPasswd;
	private static String __jdbcDriver;

	private Connection getConnection() throws Exception {
		try {
			Class.forName(__jdbcDriver);
			return DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);
		} catch (Exception exc) {
			throw exc;
		}
	}
	
	// Only instantiated by factory within package scope
	public RDBMBooktownServiceImpl() {
	}

	public List<Author> getAuthors() {
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		List<Author> rval = new ArrayList<Author>();
		try {
			conn = getConnection();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(__dbProperties.getProperty("sql.getAuthors"));
			while (rs.next()) {
				rval.add(new Author(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}

	public int createAuthor(String lname, String fname) {
		if (lname == null || fname == null || lname.length() == 0 || fname.length() == 0) {
			return -1;
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.createAuthor"),
			 										Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, lname);
			stmt.setString(2, fname);
			// return stmt.executeUpdate();
			int updatedRows = stmt.executeUpdate();
			if(updatedRows > 0){
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				generatedKeys.next();
				return generatedKeys.getInt(1);
			}else{
				return -1;
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return -1;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	public boolean deleteAuthor(int authorId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.deleteAuthor"));
			stmt.setInt(1, authorId);
			stmt.executeUpdate();
			return true;
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return false;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	public Author getAuthor(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Author author = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getAuthor"));
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				author = new Author(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return author;
	}

	@Override
	public boolean updateAuthor(Author author) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.updateAuthor"));
			stmt.setString(1, author.getLastName());
			stmt.setString(2, author.getFirstName());
			stmt.setInt(3, author.getAuthorId());
			return (stmt.executeUpdate() > 0);
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return false;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	@Override
	public List<Book> getBooks() {
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		List<Book> rval = new ArrayList<Book>();
		try {
			conn = getConnection();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(__dbProperties.getProperty("sql.getBooks"));
			while (rs.next()) {
				rval.add(new Book(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getInt(4)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}

	@Override
	public Book getBook(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Book book = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getBook"));
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				book = new Book(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getInt(4));
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return book;
	}

	@Override
	public int createBook(String title, int aid, int sid) {
		if (title == null || aid == 0 || sid==0 || title.length() == 0) {
			return -1;
		}
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.createBook"));
			stmt.setString(1, title);
			stmt.setInt(2, aid);
			stmt.setInt(3, sid);
			return stmt.executeUpdate();
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return -1;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	public Author findAuthorOfBook(int bookId) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Author author = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.findAuthorOfBook"));
			stmt.setInt(1, bookId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				author = new Author(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return author;
	}

	@Override
	public List<Subject> getSubjects() {
		Connection conn = null; 
		Statement stmt = null;
		ResultSet rs = null;
		List<Subject> rval = new ArrayList<Subject>();
		try {
			conn = getConnection();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(__dbProperties.getProperty("sql.getSubjects"));
			while (rs.next()) {
				rval.add(new Subject(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}

	@Override
	public Subject getSubject(int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Subject subject = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getSubject"));
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				subject = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return subject;
	}

	@Override
	public List<Book> findBooksBySubject(int subjectId) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Book> rval = new ArrayList<Book>();
		try {
			conn = getConnection();

			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getBooksBySubject"));
			stmt.setInt(1, subjectId);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				rval.add(new Book(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getInt(4)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}

	// This class is going to look for a file named rdbm.properties in the classpath
	// to get its initial settings
	static {
		try {
			__dbProperties = new Properties();
			__dbProperties.load(RDBMBooktownServiceImpl.class.getClassLoader().getResourceAsStream("rdbm.properties"));
			__jdbcUrl    = __dbProperties.getProperty("jdbcUrl");
			__jdbcUser   = __dbProperties.getProperty("jdbcUser");
			__jdbcPasswd = __dbProperties.getProperty("jdbcPasswd");
			__jdbcDriver = __dbProperties.getProperty("jdbcDriver");
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
		}
	}

	@Override
	public boolean deleteBook(int bid) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.deleteBook"));
			stmt.setInt(1, bid);
			stmt.executeUpdate();
			return true;
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return false;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	@Override
	public boolean updateLocation(String location, int id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.updateLocation"));
			stmt.setString(1, location);
			stmt.setInt(2, id);
			return (stmt.executeUpdate() > 0);
		} catch (Exception sqe) {
			sqe.printStackTrace();
			return false;
		} finally {  // why nest all of these try/finally blocks?
			try {
					if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
	}

	@Override
	public List<Author> getAuthorList(String location) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Author> rval = new ArrayList<Author>();
		try {
			conn = getConnection();

			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getAuthorBySubjectLocation"));
			location = '%'+location+'%';
			stmt.setString(1, location);
			rs = stmt.executeQuery();
			while (rs.next()) {
				rval.add(new Author(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}

	@Override
	public List<Book> getBooksByAuthorID(int aid, String base) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Book> book = new ArrayList<Book>();;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.findBooksByAuthorID"));
			stmt.setInt(1, aid);
			rs = stmt.executeQuery();
			if (rs.next()) {
				book.get(rs.getInt(1)).setSelf(new HALLink.Builder(base+"v2/books/"+book.get(rs.getInt(1)).getBookId()).build());
				book.add(new Book(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getInt(4),book.get(rs.getInt(1)).getSelf()));
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return book;
	}
	public List<Subject> getSubjectsByAuthorID(int authorId, String base){
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Subject> subject = new ArrayList<Subject>();;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.findSubjectsByAuthorID"));
			stmt.setInt(1, authorId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				subject.get(rs.getInt(1)).setSelfLink(base+"v2/subjects/"+rs.getInt(1));
				subject.add(new Subject(rs.getInt(1), rs.getString(2),rs.getString(3)));
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return subject;
	}

	@Override
	public Subject getSubject(int bookId, String base) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Subject subject = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getSubject"));
			stmt.setInt(1, bookId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				subject = new Subject(rs.getInt(1), rs.getString(2), rs.getString(3));
				subject.setSelfLink(base+"v2/subjects/"+subject.getSubjectId());
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return subject;
	}

	@Override
	public Author findAuthorOfBook(int bookId, String base) {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Author author = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.findAuthorOfBook"));
			stmt.setInt(1, bookId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				author = new Author(rs.getInt(1), rs.getString(2), rs.getString(3));
				author.setSelfLink(base+"v2/authors/"+author.getAuthorId());
			}
		} catch (Exception sqe) {
			sqe.printStackTrace();
		} finally {  // why nest all of these try/finally blocks?
			try {
				rs.close();
				if (stmt != null) { stmt.close(); }
			} catch (Exception e2) { e2.printStackTrace(); }
			finally {
				try {
					if (conn != null) { conn.close(); }
				} catch (Exception e3) { e3.printStackTrace(); }
			}
		}
		return author;
	}

	@Override
	public List<Book> findBooksBySubject(int subjectId, String base) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Book> rval = new ArrayList<Book>();
		try {
			conn = getConnection();

			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getBooksBySubject"));
			stmt.setInt(1, subjectId);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				rval.get(rs.getInt(1)).setSelf(new HALLink.Builder(base+"v2/books/"+rval.get(rs.getInt(1))).build());
				rval.add(new Book(rs.getInt(1), rs.getString(2), rs.getInt(3),rs.getInt(4)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}

	@Override
	public List<Author> getAuthorsBySubjectID(int subjectId, String base) {
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Author> rval = new ArrayList<Author>();
		try {
			conn = getConnection();

			stmt = conn.prepareStatement(__dbProperties.getProperty("sql.getAuthorsBySubject"));
			stmt.setInt(1, subjectId);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				rval.add(new Author(rs.getInt(1), rs.getString(2), rs.getString(3)));
			}
		}
		catch (Exception se) {
			se.printStackTrace();
			return null;
		}
		finally {  // why nest all of these try/finally blocks?
			try {
				if (rs != null) { rs.close(); }
			} catch (Exception e1) { e1.printStackTrace(); }
			finally {
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Exception e2) { e2.printStackTrace(); }
				finally {
					try {
						if (conn != null) { conn.close(); }
					} catch (Exception e3) { e3.printStackTrace(); }
				}
			}
		}

		return rval;
	}
}	
