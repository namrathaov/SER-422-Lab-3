package edu.asupoly.ser422.restexample.api.v2;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import dk.nykredit.jackson.dataformat.hal.HALLink;
import dk.nykredit.jackson.dataformat.hal.HALMapper;
import edu.asupoly.ser422.restexample.model.Author;
import edu.asupoly.ser422.restexample.model.Book;
import edu.asupoly.ser422.restexample.services.BooktownService;
import edu.asupoly.ser422.restexample.services.BooktownServiceFactory;

/*
 * This is an example of a simple custom serializer (converts a Java object to a JSON string).
 * Note all this one does is rename the property keys for Author, but what you do in the serialize
 * method is entirely up to you, so you may generalize to as rich a hypermedia format as you like.
 * 
 * Note that you would typically write a deserializer as well when you customize the JSON. A
 * deserializer goes in the opposite direction, converting JSON to a Java object. So, in this case
 */
public final class AuthorSerializationHelper {
	// some locally used constant naming our Author field names
	private static final String __AUTHORID = "id";
	private static final String __LASTNAME = "last_name";
	private static final String __FIRSTNAME = "first_name";
	private final static AuthorSerializationHelper __me = new AuthorSerializationHelper();
	private static BooktownService __bService = BooktownServiceFactory.getInstance();
	private ObjectMapper mapper = new ObjectMapper();
	private SimpleModule module = new SimpleModule();
	
	// Singleton
	private AuthorSerializationHelper() {
		module.addSerializer(Author.class, new AuthorJSON());
		module.addDeserializer(Author.class, new JSONAuthor());
		mapper.registerModule(module);
	}
	
	public static AuthorSerializationHelper getHelper() {
		return __me;
	}
	
	public String generateJSON(Author author, String self, String base) throws JsonProcessingException {
		// Since a custom serializer was added to the mapper via registerModule,
		// internally it will invoke the serialize method in the inner class below
		try {
			ObjectMapper om = new HALMapper();
			author.setSelf(new HALLink.Builder(self).build());
			author.setBookRel(new HALLink.Builder(base+"v2/books").build());
			author.setSubjectRel(new HALLink.Builder(base+"v2/subjects").build());
			author.setAuthoredBookRel(__bService.getBooksByAuthorID(author.getAuthorId(), base));
			author.setAuthoredSubjectRel(__bService.getSubjectsByAuthorID(author.getAuthorId(), base));
			return om.writeValueAsString(author);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Author consumeJSON(String json) throws IOException, JsonProcessingException {
		// A deserializer goes from JSON to the Object using the inverse process
		System.out.println("consumeJSON: " + json);
		return mapper.readValue(json, Author.class);
	}
	
	// Inner class for custom Author deserialization.
	// Loosely based on http://tutorials.jenkov.com/java-json/jackson-objectmapper.html
    final private class JSONAuthor extends JsonDeserializer<Author>  {
		@Override
		public Author deserialize(JsonParser parser, DeserializationContext context)
				throws IOException, JsonProcessingException {
			Author author = new Author();
			JsonToken token = parser.nextToken();
			while (!parser.isClosed()) {
				System.out.print("Deserializer processing token: " + token.asString());
				if (token != null && JsonToken.FIELD_NAME.equals(token)) {
					// we have a JSON Field, get it and see which one we have
					String fieldName = parser.getCurrentName();
					System.out.println(", field name: " + fieldName);
					// Check for which of our 3 fields comes next and set the next token in there
					token = parser.nextToken();
					if (fieldName.equals(__AUTHORID)) 
						author.setAuthorId(parser.getValueAsInt());
					else if (fieldName.equals(__LASTNAME))
						author.setLastName(parser.getValueAsString());
					else if (fieldName.equals(__FIRSTNAME))
						author.setFirstName(parser.getValueAsString());
				}
				token = parser.nextToken();
			}
			System.out.println("Deserializer returning Author: " + author);
			return author;
		}
    }
    
	// Inner class for custom Author serialization.
    final private class AuthorJSON extends JsonSerializer<Author>  {
       @Override
       public void serialize(Author author, JsonGenerator jgen, SerializerProvider provider)
               throws IOException, JsonProcessingException {
           jgen.writeStartObject();	
           jgen.writeNumberField(__AUTHORID, author.getAuthorId());
           jgen.writeStringField(__LASTNAME, author.getLastName());
           jgen.writeStringField(__FIRSTNAME, author.getFirstName());
           jgen.writeEndObject();
       }
   }
}