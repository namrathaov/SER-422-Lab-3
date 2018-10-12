package edu.asupoly.ser422.restexample.api.v2;

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

import edu.asupoly.ser422.restexample.api.v2.AuthorSerializationHelper;
import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.services.BooktownService;
import edu.asupoly.ser422.restexample.services.BooktownServiceFactory;

@Path("/v2/authors")
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
     * @apiError (Error 5xx) {500} InternalServerError Something went wrong at server, Please contact the administrator.
     * */
    /**
     * @apiDefine NotImplementedError
     * @apiError (Error 5xx) {501} NotImplemented The resource has not been implemented. Please keep patience, our developers are working hard on it!
     * */

    /**
     * @api {get} v2/authors Get list of Authors
     * @apiName getAuthors
     * @apiGroup Authors
     *
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * 
     * @apiVersion 2.0.0
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
	public Response getAuthors() {
		List<Author> authorList = new ArrayList<Author>(__bService.getAuthors());
		String aString = "";
		int jsonKey = 0;
		try {
			for(Author author : authorList) {
				aString += "\""+String.valueOf(jsonKey)+"\":"+
					AuthorSerializationHelper.getHelper().generateJSON(author,_uriInfo.getAbsolutePath().toString(),_uriInfo.getBaseUri().toString()) + ',';
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
	 * @api {get} v2/authors/{author_id} Retrieve Author Details
	 * @apiName getAuthor
	 * @apiGroup Authors
	 * @apiDescription getAuthor retrieves  author details by ID.
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiVersion 2.0.0
	 * @apiParam authorId Author ID
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
			String aString = AuthorSerializationHelper.getHelper().generateJSON(author,_uriInfo.getAbsolutePath().toString(), _uriInfo.getBaseUri().toString());
			//LAB 3 TASK 6:
			if(aString == null) {
				return Response.status(404).entity("{ \" INVALID AUTHOR ID! \"}").build();
			}
			return Response.status(Response.Status.OK).entity(aString).build();
		} catch (Exception exc) {
			exc.printStackTrace();
			}
		return null;
	}
	
	/**
	 * @api {post} v2/authors Create Author
	 * @apiName createAuthor
	 * @apiDescription createAuthor accepts space separated FirstName and LastName in the payload.
	 * @apiGroup Authors
	 *
	 * @apiParam {String} firstName Author's First Name in payload
	 * @apiParam {String} lastName Author's Last Name in payload
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiVersion 2.0.0
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 201 Created
	 * 	[
	 *    {"authorId":1111,"firstName":"Gammy","lastName":"Denham"}
	 *  ]
	 * 
	 * */	
	@POST
	@Consumes("text/plain")
    public Response createAuthor(String name) {		
		String[] names = name.split(" ");
		int aid = __bService.createAuthor(names[0], names[1]);
		//LAB 3 TASK 6: If client enters first name, middle and last name, the author has ambiguous values 
		if(names[2]!=null) {
			String rep = "{ \"message \" : \"Bad Request: Middle name not allowed \"}";
			System.out.println(rep);
			return Response.status(400).entity(rep).build();
		}
		if (aid == -1) {
			return Response.status(500).entity("{ \" EXCEPTION INSERTING INTO DATABASE! \"}").build();
		} else if (aid == 0) {
			return Response.status(500).entity("{ \" ERROR INSERTING INTO DATABASE! \"}").build();
		}
		String rep = "{ \"authorId\" : \"" + String.valueOf(aid) + "\", \"lastName\" : \""+ names[0] +"\", \"firstName\" : \""
				+ names[1] +"\"}";
		return Response.status(201)
				.header("Location", String.format("%s%s",_uriInfo.getAbsolutePath().toString(), aid))
				.entity(rep).build();
    }
	
	/**
	 * @api {put} v2/authors Update Author
	 * @apiName updateAuthor
	 * @apiGroup Authors
	 * @apiDescription updateAuthor accepts Author object in JSON format in the payload.
	 * @apiParam {String} FirstName Author's First Name in payload
	 * @apiParam {String} LastName Author's Last Name in payload
	 *
	 * @apiVersion 2.0.0
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 201 Created
	 * 	[
	 *   {"authorId":1111,"firstName":"Gammy","lastName":"Denham"}
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
				String aString = AuthorSerializationHelper.getHelper().generateJSON(a,_uriInfo.getAbsolutePath().toString(), _uriInfo.getBaseUri().toString());
				return Response.status(201).entity(aString).build();
			} else {
				return Response.status(404, "{ \"message \" : \"Author does not exist " + a.getAuthorId() + "\"}").build();
			}
		} catch (Exception exc) {
			exc.printStackTrace();
			return Response.status(500, "{ \"message \" : \"Internal server error deserializing Author JSON\"}").build();
		}
    }
	/**
	 * @api {delete} v2/authors Delete Author
	 * @apiName deleteAuthor
	 * @apiGroup Authors
	 *
	 * @apiDescription deleteAuthor accepts an Author ID as a query parameter.
	 * @apiParam {int} id Query Parameter: Author ID
	 *
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiVersion 2.0.0
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
	 * @api {patch} v2/authors/ Correct Author Details
	 * @apiName patchAuthor
	 * @apiGroup Authors
	 *
	 * @apiVersion 2.0.0
	 * @apiDescription patchAuthor Patch Author is not supported in this version.
	 *  
	 * */	
	@PATCH
	public Response patchAuthor(@QueryParam("id") int aid) {
		return Response.status(405).entity("{ \"message \" : \"PATCH not supported\"}").build();
    }
}
