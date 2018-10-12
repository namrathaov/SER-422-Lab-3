package edu.asupoly.ser422.restexample.api.v1;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
//import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.model.Subject;
import edu.asupoly.ser422.restexample.services.BooktownService;
import edu.asupoly.ser422.restexample.services.BooktownServiceFactory;

@Path("/v1/subjects")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class SubjectResource {
	private static BooktownService __bService = BooktownServiceFactory.getInstance();
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
	 * @api {get} v1/subjects Get list of Subjects
	 * @apiName getSubjects
	 * @apiGroup Subjects
	 *
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiVersion 1.0.0
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"},
	 *   {"authorId":1212,"firstName":"John","lastName":"Worsley"}
	 *  ]
	 * 
	 * */	

	@GET
	 public List<Subject> getSubjects() {
		return __bService.getSubjects();
		
	}
	
	/**
	 * @api {get} v1/subjects/location/{locationSubstr} Retrieve Author Details by Subject Location
	 * @apiName getAuthorBySubject
	 * @apiGroup Subjects
	 *
	 * @apiDescription getAuthorBySubject retrieves author details by Subject Location Substring.
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiVersion 1.0.0
	 * @apiParam locationSubstr Substring of Location
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"},
	 *   {"authorId":1212,"firstName":"John","lastName":"Worsley"}
	 *  ]
	 * 
	 * */	
	@GET
	@Path("/location/{locationSubstr}")
	 public List<Author> getAuthorBySubject(@PathParam("locationSubstr") String location) {
		   return __bService.getAuthorList(location);
	}
	
	/**
     * @api {get} v1/books/findbooks/{subjectId} Retrieve Books by Subject
     * @apiName findBooksBySubject
     * @apiGroup Subjects
     *
     * @apiDescription findBooksBySubject retrieves individual book details by Subject ID.
     * @apiUse BadRequestError
     * @apiUse InternalServerError
     * @apiVersion 1.0.0
     * @apiParam subject_id Subject ID
     * @apiSuccessExample Success-Response:
     * 	HTTP/1.1 200 OK
     * 	[
     *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"},
     *   {"authorId":1212,"firstName":"John","lastName":"Worsley"}
     *  ]
     * 
     * */	
	@GET
	@Path("/findbooks/{subjectId}")
	 public List<Book> findBooksBySubject(@PathParam("subjectId") int subjectId) {
		 return __bService.findBooksBySubject(subjectId);
 }
	/**
	 * @api {get} v1/subjects/{subjectId} Retrieve Subject Details
	 * @apiName getSubject
	 * @apiGroup Subjects
	 * @apiVersion 1.0.0
	 * @apiDescription getSubject retrieves individual subject details by Subject ID.
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * 
	 * @apiParam subject_id Subject ID
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 200 OK
	 * 	[
	 *   {"authorId":1111,"firstName":"Ariel","lastName":"Denham"},
	 *   {"authorId":1212,"firstName":"John","lastName":"Worsley"}
	 *  ]
	 * 
	 * */	

	@GET
	@Path("/{subjectId}")
	 public Response getSubject(@PathParam("subjectId") int id) {
		   Subject subject = __bService.getSubject(id);
		   try {
				String aString = SubjectSerializationHelper.getHelper().generateJSON(subject);
				return Response.status(Response.Status.OK).entity(aString).build();
			} catch (Exception exc) {
				exc.printStackTrace();
				return null;
			}
	}

	/**
	 * @api {post} v1/subjects/ Create Subject 
	 * @apiName createSubject
	 * @apiGroup Subjects
	 *@apiVersion 1.0.0
	 * @apiDescription createSubject Create Subject is not supported in this version.
	 *  
	 * */	
	//LAB 3 TASK 6: Create Subject not supported
	@POST
	public Response createSubject(@QueryParam("id") int sid) {
		return Response.status(405).entity("{ \"message \" : \"Create not supported\"}").build();
    }
	
	/**
	 * @api {put} v1/subjects/ Update Subject Location
	 * @apiName updateLocation
	 * @apiGroup Subjects
	 *
	 * @apiDescription updateLocation updates subject location by new location in payload and 'id' in query parameter.
	 * 
	 * @apiParam location New Location in Payload
	 * @apiParam subject_id Subject ID
	 * @apiUse BadRequestError
	 * @apiUse InternalServerError
	 * @apiVersion 1.0.0
	 * @apiSuccessExample Success-Response:
	 * 	HTTP/1.1 204 No Content
	 * 
	 * */	
	@PUT
    public Response updateLocation(String location, @QueryParam("id") int id) {
		if (__bService.updateLocation(location,id)) {
			return Response.status(201).entity("{ \"Location \" : \"" + location + "\"}").build();
		} else {
			return Response.status(404).entity("{ \"message \" : \"No such Subject " + id + "\"}").build();
		}
    }
}