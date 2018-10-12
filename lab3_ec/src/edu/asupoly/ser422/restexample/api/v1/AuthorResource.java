package edu.asupoly.ser422.restexample.api.v1;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.services.BooktownService;
import edu.asupoly.ser422.restexample.services.BooktownServiceFactory;

@Path("/v1/authors")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})

public class AuthorResource {
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
	 * @api {get} v1/authors Get list of Authors
	 * @apiName getAuthors
	 * @apiGroup Authors
	 *
	 * @apiVersion 1.0.0
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"},
	 *   {"authorId":1212,"firstName":"John","lastName":"Worsley"}
	 *  ]
	 * 
	 * */	

	@GET
	public List<Author> getAuthors() {
		return __bService.getAuthors();
	}
	/**
	 * @api {get} v1/authors/{author_id} Retrieve Author Details
	 * @apiName getAuthor
	 * @apiGroup Authors
	 *
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiVersion 1.0.0
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"}
	 *  ]
	 * 
	 * */	
	@GET
	@Path("/{authorId}")
	public Response getAuthor(@PathParam("authorId") int aid) {
		Author author = __bService.getAuthor(aid);
		try {
			String aString = AuthorSerializationHelper.getHelper().generateJSON(author,_uriInfo.getAbsolutePath().toString());
			//LAB 3 TASK 6:
			if(aString == null) {
				return Response.status(404).entity("{ \" AUTHOR ID DOES NOT EXIST! \"}").build();
			}
			return Response.status(Response.Status.OK).entity(aString).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(500).entity("{ \" ERROR IN FETCHING THE AUTHOR DETAILS! \"}").build();
		}
	}

	/**
	 * @api {post} v1/authors Create Author
	 * @apiName createAuthor
	 * @apiDescription createAuthor accepts FirstName and LastName in the payload.
	 * @apiGroup Authors
	 *
	 * @apiParam {String} FirstName Author
	 * @apiParam {String} LastName Author
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiVersion 1.0.0
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 204 Created
	 * 	[
	 *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"}
	 *  ]
	 * 
	 * */	
	@POST
	@Consumes("text/plain")
	public Response createAuthor(String name) {		
		String[] names = name.split(" ");
		int aid = __bService.createAuthor(names[0], names[1]);
		if (aid == -1) {
			return Response.status(500).entity("{ \" EXCEPTION IN INSERTING INTO DATABASE! \"}").build();
		} else if (aid == 0) {
			return Response.status(500).entity("{ \" ERROR INSERTING AUTHOR DETAILS INTO DATABASE! \"}").build();
		}
		return Response.status(201)
				.header("Location", String.format("%s/%s",_uriInfo.getAbsolutePath().toString(), aid))
				.entity("{ \"Author\" : \"" + aid + "\", \"Last Name : \""+ names[0] +"\", \"First Name : \""
						+ names[1] +"\"}").build();
	}

	/**
	 * @api {put} v1/authors Update Author
	 * @apiName updateAuthor
	 * @apiGroup Authors
	 * @apiDescription updateAuthor accepts Author object in JSON format in the payload.
	 * @apiParam {String} FirstName Author's First Name
	 * @apiParam {String} LastName Author's Last Name
	 *
	 * @apiVersion 1.0.0
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 201 OK
	 * 	[
	 *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"}
	 *  ]
	 * 
	 * */
	@PUT
	@Consumes("application/json")
	public Response updateAuthor(String json) {
		try {
			Author a = AuthorSerializationHelper.getHelper().consumeJSON(json);
			if (__bService.updateAuthor(a)) {
				// In the response payload it would still use Jackson's default serializer,
				// so we directly invoke our serializer so the PUT payload reflects what it should.
				String aString = AuthorSerializationHelper.getHelper().generateJSON(a,_uriInfo.getAbsolutePath().toString());
				return Response.status(201).entity(aString).build();
			} else {
				return Response.status(404, "{ \"message \" : \"No such Author " + a.getAuthorId() + "\"}").build();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(500, "{ \"message \" : \"Internal server error deserializing Author JSON\"}").build();
		}
	}
	/**
	 * @api {delete} v1/authors Delete Author
	 * @apiName deleteAuthor
	 * @apiGroup Authors
	 *
	 * @apiDescription deleteAuthor accepts an Author ID as query parameter.
	 * @apiParam {int} id Author ID
	 *
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiVersion 1.0.0
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 204 No Content 
	 * */
	@DELETE
	public Response deleteAuthor(@QueryParam("id") int aid) {
		if (__bService.deleteAuthor(aid)) {
			return Response.status(204).build();
		} else {
			return Response.status(404).entity("{ \"message \" : \"No such Author " + aid + "\"}").build();
		}
	}

	/**
	 * @api {patch} v1/authors/ Correct Author Details
	 * @apiName patchAuthor
	 * @apiGroup Authors
	 * @apiVersion 1.0.0
	 * @apiDescription patchAuthor Patch Author is not supported in this version.
	 *  
	 * */	
	@PATCH
	public Response patchAuthor(@QueryParam("id") int aid) {
		return Response.status(405).entity("{ \"message \" : \"PATCH not supported\"}").build();
    }
}
