package autoweb;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.io.PrintWriter;
import client.*;

/**
 * Servlet implementation class SelectModel
 */
@WebServlet("/SelectModel")
public class SelectModel extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<String> modelNames;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectModel() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		CarModelOptionsIO carClient = new CarModelOptionsIO(); //not sure if here or init() is a better place
		carClient.openConnection();
		modelNames = carClient.getModels();
		carClient.closeSession();
		writePage(response, modelNames);
	}
	protected void writePage (HttpServletResponse response, ArrayList<String> modelNames) {
		try {
			PrintWriter writer = response.getWriter();
			writer.println("<!DOCTYPE HTML>");
			writer.println("<HTML><HEAD>");
			writer.println("<TITLE>Choose a vehicle model</TITLE>");
			writer.println("</HEAD>");
			writer.println("<BODY>");
			writer.println("<H3> Select car model to customize. </H3>");
			writer.println("<FORM ACTION='ConfigAuto'>");
			writer.println("<TABLE BORDER='1px'>");
			writer.println("<TR> <TD> Model </TD>");
			writer.println("<TD><SELECT NAME='modelnm'>");
			for (int i = 0; i < modelNames.size(); i++){
				writer.println("<OPTION VALUE='" + modelNames.get(i) + "'>" +
						modelNames.get(i) + "</OPTION>");
			}
			writer.println("</SELECT></TD></TR>");
			writer.println("<TR><TD COLSPAN=2> <INPUT TYPE='SUBMIT' VALUE='Next' STYLE='float:right;' />");
			writer.println("</TD></TR></TABLE>");
			writer.println("</FORM>");
			writer.println("</BODY></HTML>");
			writer.close();
			
		}
		catch (IOException e) {}
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
