package autoassist;

import javax.servlet.http.*;
import automobile.Model;

/**
 * Session Bean implementation class ConfigHelper
 */

public class ConfigHelper {

    /**
     * Default constructor. 
     */
    public ConfigHelper() {
        
    }
    
    public  Model processAttributes(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	Model auto= (Model)session.getAttribute("car");
    	String optSetName ="";
    	for (int i = 0; i < auto.getOptionSetSize(); i++) {
    		optSetName = auto.getOptionSetName(i);
    		auto.setOptionChoice(optSetName, request.getParameter(optSetName));	
    	}
    	return auto;
    }
}
