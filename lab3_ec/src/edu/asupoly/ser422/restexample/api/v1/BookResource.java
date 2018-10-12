package edu.asupoly.ser422.restexample.api.v1;

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

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.services.BooktownService;
import edu.asupoly.ser422.restexample.services.BooktownServiceFactory;

@Path("/v1/books")
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
     * 
     * @apiDefine InternalServerError
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator!
     * */
    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!!
     * */

    /**
     * @api {get} v1/books Get list of Books
     * @apiName getBooks
     * @apiGroup Books
     *
     * @apiVersion 1.0.0
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * 
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	[
     *   {"bookId":1111,"title":"Ariel","Author ID":"1","Subject ID":"1"},
     *   {"bookId":1212,"title":"John","Author ID":"2","Subject ID":"1"}
     *  ]
     * 
     * */	
	
	@GET
	public List<Book> getBooks() {
		return __bService.getBooks();
	}
	/**
	 * @api {get} v1/books/{book_id} Retrieve Book Details
	 * @apiName getBook
	 * @apiGroup Books
	 *
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiVersion 1.0.0
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *  {"bookId":1111,"title":"Gammy","Author ID":"1","Subject ID":"1"}
	 *  ]
	 * 
	 * */	
	@GET
	@Path("/{bookId}")
	 public Response getBook(@PathParam("bookId") int id) {
		Book book= __bService.getBook(id);
		try {
			String aString = BookSerializationHelper.getHelper().generateJSON(book,_uriInfo.getAbsolutePath().toString());
			//LAB 3 TASK 6:
			if(aString == null) {
				return Response.status(404).entity("{ \" INVALID BOOK ID! \"}").build();
			}
			return Response.status(Response.Status.OK).entity(aString).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(500).entity("{ \" Exception in getting book details! \"}").build();
		}
	 }
	
	/**
	 * @api {get} v1/books/bookauthor/{bookId} Retrieve Book Author
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
	@Path("bookauthor/{bookId}")
	public Author findAuthorOfBook(@PathParam("bookId") int bookId) {
		return __bService.findAuthorOfBook(bookId);		
	}
	/**
	 * @api {post} v1/books/ Create Book
	 * @apiName createBook
	 * @apiVersion 1.0.0
	 * @apiGroup Books
	 *
	 * @apiDescription createBook returns the Book details of book created.
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiParam title Book Title in payload
	 * @apiParam author_id Query Param: Author ID
	 * @apiParam subject_id Query Param: Subject ID
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 201 Created
	 * 	[
	 *  {"bookId":1111,"title":"Gammy","Author ID":"1","Subject ID":"1"}
	 *  ]
	 * 
	 * */	
	@POST
	@Consumes("text/plain")
	 public Response createBook(String title,@QueryParam("aid") int aid,@QueryParam("sid") int sid) {
		
		int success = __bService.createBook(title, aid, sid);
		if (success == -1) {
			return Response.status(500).entity("{ \" EXCEPTION INSERTING INTO DATABASE! \"}").build();
		} else if (success == 0) {
			return Response.status(500).entity("{ \" ERROR INSERTING INTO DATABASE! \"}").build();
		}
		return Response.status(201)
				.header("Location", String.format("%s/%s",_uriInfo.getAbsolutePath().toString(), success))
				.entity("{ \"Book ID\" : \"\" + success +\"Book title\" : \"" + title + "\", \"Author ID : \""
				+ aid +"\", \"Subject ID : \""	+ sid +"\"}").build();
		}
	
	/**
	 * @api {patch} v1/books/ Correct Book Details
	 * @apiName patchBook
	 * @apiGroup Books
	 * @apiVersion 1.0.0
	 *
	 * @apiDescription patchBook Patch Book is not supported in this version.
	 *  
	 * */
	//LAB 3 TASK 6: Correct book details not yet supported
	@PATCH
	public Response patchBook(@QueryParam("id") int bid) {
		return Response.status(405).entity("{ \"message \" : \"PATCH not supported\"}").build();
    }
	
	/**
	 * @apiVersion 1.0.0
	 * @api {delete} v1/books/ Delete Book
	 * @apiName deleteBook
	 * @apiGroup Books
	 *
	 * @apiDescription deleteBook deletes the Book with Book ID 'id'.
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiParam book_id Query Parameter: Book ID
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 204 No Content
	 * 	[
	 *  {"bookId":1111,"title":"Ariel","Author ID":"1","Subject ID":"1"}
	 *  ]
	 * 
	 * */	
	
	@DELETE
    public Response deleteBook(@QueryParam("id") int bid) {
		if (__bService.deleteBook(bid)) {
			return Response.status(204).build();
		} else {
			return Response.status(404).entity("{ \"message \" : \"Invalid Book ID" + bid + "\"}").build();
		}
    }
    
}
