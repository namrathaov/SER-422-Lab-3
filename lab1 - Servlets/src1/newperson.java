import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
public class newperson extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String _filename=null;
	private static String path=null;
	private static String xmlFilePath=null; 
	public void init(ServletConfig config) throws ServletException {
			super.init(config);
			_filename=config.getInitParameter("xmlfile");
			path=System.getProperty("catalina.home");
			xmlFilePath=path+_filename;
	}
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		resp.getWriter().println("<html><body><p>Use " + "POST instead!</p></body></html>");
	}
	public void doPost(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException 
	{
		response.setContentType("text/html");
		String formFName=request.getParameter("fname");
		String formLName=request.getParameter("lname");
		Cookie firstName = new Cookie("lab1Cookie_first_name", formFName);
		Cookie lastName = new Cookie("lab1Cookie_last_name", formLName);
		Boolean flag=false;
		PrintWriter out = response.getWriter();
		// Set expiry date after 24 Hrs for both the cookies.
		firstName.setMaxAge(60*60*24);
		lastName.setMaxAge(60*60*24);

		// Add both the cookies in the response header.
		response.addCookie( firstName );
		response.addCookie( lastName );

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File tmpDir = new File(xmlFilePath);
		boolean existsFile = tmpDir.exists();
		Element root,child = null;
		Document document = null;
		StreamResult result = null;
		//create new XML file

		if(!existsFile) { 

			document = docBuilder.newDocument();
			root = document.createElement("form");
			document.appendChild(root);
			result = new StreamResult(new File(xmlFilePath));
		}
		//append to existing file
		else
		{

			try {
				document = docBuilder.parse(xmlFilePath);
				result = new StreamResult(new File(xmlFilePath));
			} catch (SAXException e) {

				e.printStackTrace();
			}
			root = document.getDocumentElement();
		}	

		//check if person exists
		NodeList nFName= document.getElementsByTagName("fName");
		NodeList nLName = document.getElementsByTagName("lname");
		NodeList nTotal= document.getElementsByTagName("newperson");
		int totalCount= nTotal.getLength();
		for (int temp1 = 0,temp2=0; (temp1 < nFName.getLength()) && (temp2 < nLName.getLength()); temp1++,temp2++) {

			Element eFElement = (Element)nFName.item(temp1);
			Element eLElement = (Element)nLName.item(temp2);

			Node eNextNode;

			flag=false;

			if((eFElement.getTextContent().matches(formFName)) && (eLElement.getTextContent().matches(formLName)))
			{
				while(eLElement.getNextSibling()!=null) {
					eNextNode=eLElement.getNextSibling();
					eLElement=(Element)eNextNode;
					if ("languages".equals(eNextNode.getNodeName())) {

						while (eNextNode.hasChildNodes()) {
							eNextNode.removeChild(eNextNode.getFirstChild());}
						String paramValues[] = request.getParameterValues("languages");
						if(paramValues.length>0) {
							for(int x=0;x<paramValues.length;x++)
							{
								Element language = document.createElement("language");
								eNextNode.appendChild(language);
								language.appendChild(document.createTextNode(paramValues[x]));
							}}
					}
					if("days".equals(eNextNode.getNodeName())) {

						while (eNextNode.hasChildNodes())
							eNextNode.removeChild(eNextNode.getFirstChild());
						String paramValues[] = request.getParameterValues("days");
						if(paramValues.length>0) {
							for(int x=0;x<paramValues.length;x++)
							{
								Element day = document.createElement("day");

								eNextNode.appendChild(day);
								day.appendChild(document.createTextNode(paramValues[x]));

							} }
					}
					if("movies".equals(eNextNode.getNodeName())) {

						while (eNextNode.hasChildNodes())
							eNextNode.removeChild(eNextNode.getFirstChild());
						String paramValues[] = request.getParameterValues("movies");
						if(paramValues.length>0) {
							for(int x=0;x<paramValues.length;x++)
							{
								Element movie = document.createElement("movie");

								eNextNode.appendChild(movie);
								movie.appendChild(document.createTextNode(paramValues[x]));

							} }
					}
				}

				flag=true;
				break;	        
			}
		}


		if(!flag)
		{
			child = document.createElement("newperson");
			root.appendChild(child);
			Element fName = document.createElement("fName");
			child.appendChild(fName);
			fName.appendChild(document.createTextNode(formFName));
			Element lName = document.createElement("lname");
			child.appendChild(lName);
			lName.appendChild(document.createTextNode(formLName));
			Element languages = document.createElement("languages");
			child.appendChild(languages);
			String paramLanguages[] = request.getParameterValues("languages");
			for(int x=0;x<paramLanguages.length;x++)
			{
				Element language = document.createElement("language");

				languages.appendChild(language);
				language.appendChild(document.createTextNode(paramLanguages[x]));
			}
			Element days = document.createElement("days");
			child.appendChild(days);
			String paramDays[] = request.getParameterValues("days");
			for(int x=0;x<paramDays.length;x++)
			{
				Element day = document.createElement("day");

				days.appendChild(day);
				day.appendChild(document.createTextNode(paramDays[x]));
			}
			Element movies = document.createElement("movies");
			child.appendChild(movies);
			String paramMovies[] = request.getParameterValues("movies");
			for(int x=0;x<paramMovies.length;x++)
			{
				Element movie = document.createElement("movie");

				movies.appendChild(movie);
				movie.appendChild(document.createTextNode(paramMovies[x]));
			}                  
		}
		TransformerFactory factory=TransformerFactory.newInstance();
		Transformer transformer=null;
		try {
			transformer = factory.newTransformer();
		} catch (TransformerConfigurationException ec) {

			out.println("Throwing Exception");
			ec.printStackTrace();
		}
		DOMSource source = new DOMSource(document);
		try {
			transformer.transform(source, result);
		} 
		catch (TransformerException e) {
			out.println("Error in Transformation");
			e.printStackTrace();
		}
		out.println("Transaction Successful!\n<br><br>");
		out.println("Total New Persons = " + (totalCount+1));
		out.println("\n<html><body><br><br><a href=\"" + request.getParameter("back") + "\">Back</a></body></html>");
	}

	public void destroy() {
		/* leaving empty for now this can be
		 * used when we want to do something at the end
		 * of Servlet life cycle
		 */
	}	


}
