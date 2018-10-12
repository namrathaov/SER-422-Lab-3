package edu.asupoly.ser422.restexample.api.v2;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
//import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.asupoly.ser422.restexample.api.v2.BookSerializationHelper;
import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.services.BooktownService;
import edu.asupoly.ser422.restexample.services.BooktownServiceFactory;

@Path("/v2/books")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class BookResource {
	private static BooktownService __bService = BooktownServiceFactory.getInstance();
	@Context
	private UriInfo _uriInfo;
	
	 /**
     * @apiDefine BadRequestError
     * @apiError (Error 4xx) {400} BadRequest Bad Request Encountered
     * */
    /** @apiDefine ActivityNotFoundError
     * @apiError (Error 4xx) {404} NotFound Activity cannot be found
     * */
    /**
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */
    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */

    /**
     * @api {get} v2/books Get list of Books
     * @apiName getBooks
     * @apiGroup Books
     *
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiVersion 2.0.0
     * @apiDescription Returns Book with associated hyperlinks
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	[
     *   {"bookId":1111,"title":"Ariel","Author ID":"1","Subject ID":"1"},
     *   {"bookId":1212,"title":"John","Author ID":"2","Subject ID":"1"}
     *  ]
     * 
     * */	
	
	@GET
	public Response getBooks() {
		List<Book> bookList = new ArrayList<Book>(__bService.getBooks());
		String aString = "";
		int jsonKey = 0;
		try {
			for(Book book : bookList) {
				aString += "\""+String.valueOf(jsonKey)+"\":"+
					BookSerializationHelper.getHelper().generateJSON(book,_uriInfo.getAbsolutePath().toString(),_uriInfo.getBaseUri().toString()) + ',';
				jsonKey++;
			}
			System.out.println("{" + aString.substring(0, aString.length() - 1) + "}");
			return Response.status(Response.Status.OK).entity("{" + aString.substring(0, aString.length() - 1) + "}").build();
		}catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	}
	/**
	 * @api {get} v2/books/{book_id} Retrieve Book Details
	 * @apiName getBook
	 * @apiGroup Books
	 *
	 * @apiParam bookId BookID
	 * @apiVersion 2.0.0
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiDescription Returns Book with associated hyperlinks
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *  {"bookId":1111,"title":"Ariel","Author ID":"1","Subject ID":"1"}
	 *  ]
	 * 
	 * */	
	@GET
	@Path("/{bookId}")
	 public Response getBook(@PathParam("bookId") int id) {
		Book book= __bService.getBook(id);
		try {
			String aString = BookSerializationHelper.getHelper().generateJSON(book,_uriInfo.getAbsolutePath().toString(),_uriInfo.getBaseUri().toString());
			return Response.status(Response.Status.OK).entity(aString).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return null;
		}
	 }
	
	/**
	 * @api {get} v2/books/{book_id}/authors Retrieve Book Author
	 * @apiName findAuthorOfBook
	 * @apiGroup Books
	 *
	 * @apiDescription findAuthorOfBook returns the Author details of book_id
	 * @apiUse BadRequestError
	 * @apiVersion 2.0.0
	 * @apiUse InternalServerError
	 * 
	 * @apiParam bookId BookID
	 * @apiSuccessExample Success Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *  { "authorId": 1 ,
	 *	"firstName": "Clinton",
	 *	"lastName": "Hillary" }
	 *  ]
	 * 
	 * */	
	@GET
	@Path("/{bookId}/authors")
	public Author findAuthorOfBook(@PathParam("bookId") int bookId) {
		return __bService.findAuthorOfBook(bookId);		
	}
	/**
	 * @api {post} v2/books/ Create Book
	 * @apiName createBook
	 * @apiVersion 2.0.0
	 * @apiGroup Books
	 *
	 * @apiDescription createBook returns the Book details of book created along with location in Header.
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiParam title Book Title in payload
	 * @apiParam author_id Query Parameter: Author ID
	 * @apiParam subject_id Query Parameter: Subject ID
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 201 Created
	 * 	[
	 *  {"bookId":1111,"title":"Ariel","Author ID":"1","Subject ID":"1"}
	 *  ]
	 * 
	 * */	
	@POST
	@Consumes("text/plain")
	 public Response createBook(String title,@QueryParam("author_id") int aid,@QueryParam("subject_id") int sid) {
		
		int success = __bService.createBook(title, aid, sid);
		if (success == -1) {
			return Response.status(500).entity("{ \" EXCEPTION INSERTING INTO DATABASE! \"}").build();
		} else if (success == 0) {
			return Response.status(500).entity("{ \" ERROR INSERTING INTO DATABASE! \"}").build();
		}
		String rep = "{\"Book title\" : \"" + title + "\", \"Author ID\" : \""
				+ aid +"\", \"Subject ID\" : \"" + sid +"\"}";
		return Response.status(201)
				.header("Location", String.format("%s%s",_uriInfo.getAbsolutePath().toString(), success))
				.entity(rep).build();
		}
	
	/**
	 * @api {patch} v2/books/ Correct Book Details
	 * @apiName patchBook
	 * @apiGroup Books
	 *
	 * @apiDescription patchBook Patch Book is not supported in current version.
	 *  @apiVersion 2.0.0
	 * */	
	//LAB 3 TASK 6: Correct book details not supported
	@PATCH
	public Response patchBook(@QueryParam("id") int bid) {
		return Response.status(405).entity("{ \"message \" : \"PATCH not supported\"}").build();
    }
    
	/**
	 * @api {delete} v2/books/ Delete Book
	 * @apiVersion 2.0.0
	 * @apiName deleteBook
	 * @apiGroup Books
	 *
	 * @apiDescription deleteBook deletes Book with Book ID 'id'.
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiVersion 2.0.0
	 * @apiParam book_id Query Param: Book ID
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 204 No Content
	 * 	[
	 *  {"bookId":1111,"title":"Ariel","Author ID":"1","Subject ID":"1"}
	 *  ]
	 * 
	 * */	
	
	@DELETE
    public Response deleteBook(@QueryParam("book_id") int bid) {
		if (__bService.deleteBook(bid)) {
			return Response.status(204).build();
		} else {
			return Response.status(404).entity("{ \"message \" : \"No such Book " + bid + "\"}").build();
		}
    }
    
}
