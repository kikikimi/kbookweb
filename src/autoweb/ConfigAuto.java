package autoweb;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.Locale;

import client.CarModelOptionsIO;
import automobile.Model;
/**
 * Servlet implementation class ConfigAuto
 */
@WebServlet("/ConfigAuto")
public class ConfigAuto extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	ArrayList<String> modelNames;
	Model configModel;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfigAuto() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		session = request.getSession();
		String modelnm = request.getParameter("modelnm");
		response.setContentType("text/html;charset=UTF-8");
		getOptions(modelnm);
		writePage(response, modelnm);
		
	}
	private void getOptions (String modelnm) {
		CarModelOptionsIO carClient = new CarModelOptionsIO();
		carClient.openConnection();
		modelNames = carClient.getModels();
		configModel = carClient.getModelOptions(modelnm);
		carClient.sendCmd("QUIT");
		carClient.closeSession();
		session.setAttribute("car", configModel);
	}
	private void writePage(HttpServletResponse response, String modelnm) {
		try{
			PrintWriter writer = response.getWriter();
			String selected = "";
			writer.println("<!DOCTYPE HTML>");
			writer.println("<HTML><HEAD>");
			writer.println("<TITLE>"+ modelnm + " Configuration</TITLE>");
			writer.println("</HEAD>");
			writer.println("<BODY>");
			writer.println("Select options for your car");
			writer.println("<FORM NAME='formA' ACTION='ConfigAuto'>");
			writer.println("<INPUT TYPE='hidden' NAME='modelnm' value=''>");
			writer.println("</INPUT></FORM>");
			writer.println("<FORM NAME='formB' ACTION='carOptions.jsp'>");
			writer.println("<TABLE BORDER='1px'>");
			writer.println("<TR> <TD> Model: </TD>");
			writer.println("<TD><SELECT NAME='modeldrpdwn' "
					+ "ONCHANGE=\" document.formA.modelnm.value = document.formB.modeldrpdwn.value;"
					+ "document.formA.submit()\">");
			if (modelNames != null) {
				for (int i = 0; i < modelNames.size(); i++){
					if (modelNames.get(i).equals(modelnm))
						selected = "SELECTED";
					else selected = "";
					writer.println("<OPTION VALUE='" + modelNames.get(i) + "' " + selected +">" +
							modelNames.get(i) + "</OPTION>");
				}
			}
			writer.println("</SELECT></TD></TR>");
			for (int i = 0; i < configModel.getOptionSetSize(); i++) {
				writer.println("<TR><TD>" +configModel.getOptionSetName(i) + "</TD>");
				writer.println("<TD><SELECT NAME ='" + configModel.getOptionSetName(i) +"'>");
				for (int j = 0; j < configModel.getOptionCount(i); j++) {
					writer.print("<OPTION VALUE='" + configModel.getOptionValue(i, j) + "'>");
					writer.print(configModel.getOptionValue(i, j) + " - " 
							+ NumberFormat.getCurrencyInstance(new Locale("en", "US"))
                            .format(configModel.getOptionPrice(i, j)));
					writer.println("</OPTION>");
				}
				writer.println("</TD></TR>");	
			}
			writer.println("<TR><TD COLSPAN=2> <INPUT TYPE='SUBMIT' VALUE='Next' STYLE='float:right;' />");
			writer.println("</TABLE>");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
