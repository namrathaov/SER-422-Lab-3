import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class getperson
 */
@WebServlet("/getperson")
public class getperson extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String _filename=null;
	private static String path=null;
	private static String xmlFilePath=null; 
	public void init(ServletConfig config) throws ServletException {
			super.init(config);
			_filename = config.getInitParameter("xmlfile");
			path = System.getProperty("catalina.home");
			xmlFilePath = path + _filename;
	}
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getperson() {
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();	
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document document = null;
		try {
			document = docBuilder.parse(xmlFilePath);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String browser=request.getHeader("User-Agent");
		if (browser.contains("Chrome"))
			out.println("<html><body bgcolor=cyan><table border='1'>");
		else
			out.println("<html><body><table border='1'>");
		String URL=request.getQueryString();
		if(URL.isEmpty()) {
			NodeList allData=(document.getElementsByTagName("newperson"));
			out.print("<tr><td>First Name</td><td>Last Name</td><td>Languages</td><td>Days</td><td>Movies</td></tr>");	
			for(int d=0;d<allData.getLength();d++) {
				Node nNode = allData.item(d);
				out.print("<tr>");
				if(nNode.hasChildNodes()) {
					NodeList allChild=nNode.getChildNodes();
					for(int child=0;child<allChild.getLength();child++)
					{
						out.print("<td>");
						out.print(allChild.item(child).getTextContent()+" , ");
						out.print("</td>");
					}
				}
				else {
					out.print("<td>"+nNode.getTextContent()+"</td>");
				}
				out.print("</tr>");
			}
			out.print("</table></body></html>");
		}
		else {			
			String fname=request.getParameter("fName");
			String lname=request.getParameter("lname");
			String[] languages=request.getParameterValues("languages");
			String[] days=request.getParameterValues("days");
			String movies=request.getParameter("movies");
			NodeList nList=document.getElementsByTagName("newperson");
			for(int i=0;i<nList.getLength();i++)
			{
				Node n=nList.item(i);
				Boolean isExist=false;
				if((n.getNodeType()==Node.ELEMENT_NODE) && n!=null)
				{
					Element element=(Element) n;
					if(!element.getElementsByTagName("fName").item(0).getTextContent().contains(fname))
						continue;
					if(!element.getElementsByTagName("lname").item(0).getTextContent().contains(lname))
						continue;
					NodeList langList=element.getElementsByTagName("languages").item(0).getChildNodes();
					List<String> llist=new ArrayList<String>();
					for(int j=0;j<langList.getLength();j++) 
					{
						llist.add(langList.item(j).getTextContent());
					}
					for(int j = 0;j<languages.length; j++)
					{
						if(llist.contains(languages[j]))
						{
							isExist=true;
							break;
						}
					}
					if(!isExist)
						continue;
				NodeList daysList=element.getElementsByTagName("days").item(0).getChildNodes();
				List<String> dlist=new ArrayList<String>();
				for(int j=0;j<daysList.getLength();j++)
				{
					dlist.add(daysList.item(j).getTextContent());
				}
				isExist = false;
				if(days!=null)
				{
					for(int j=0;j<days.length; j++)
					{
						if (dlist.contains(days[j])) 
						{
							isExist = true;
							break;
						}
					}
					if (!isExist) continue;
				}
				if (!element.getElementsByTagName("movies").item(0).getTextContent().contains(movies))
					continue;
				out.println(element.getElementsByTagName("fName").item(0).getTextContent());
				out.println(element.getElementsByTagName("lname").item(0).getTextContent());
				for(int x=0;x<llist.size();x++)
				{
					out.println(llist.get(x));
				}
				for(int y=0;y<dlist.size();y++)
				{
					out.println(dlist.get(y));
				}
				out.println(element.getElementsByTagName("movies").item(0).getTextContent());
				}
			}
		}
	}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
	response.getWriter().println("<html><body><p>Use " + "GET instead!</p></body></html>");

}
}