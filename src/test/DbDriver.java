/* Kimberly Disher
 * CIS 35B
 * Lab6, database interaction
 */
package test;
import java.util.ArrayList;
import dbmanagement.*;
import adapter.*;

public class DbDriver {

	public static void main(String[] args) {
		DBCreation dbCreator = new DBCreation();
		ModelBuilder modBuilder = new ModelBuilder();
		DBUpkeep dbstuff= new DBUpkeep (modBuilder);
		ArrayList<String> modelList;
		if (dbCreator.dataExists()) {
			dbstuff.loadModelsFromDb("");
		}
		else {
			dbstuff.addModelToDB(modBuilder.buildAuto("automobile.txt", "text"));
			dbstuff.addModelToDB(modBuilder.buildAuto("automobile2.txt", "text"));
			dbstuff.addModelToDB(modBuilder.buildAuto("automobile3.txt", "text"));
			dbstuff.addModelToDB(modBuilder.buildAuto("automobile4.txt", "text"));
		}
		
		modelList = modBuilder.getModelNameList();
		for (String modStr : modelList) {
			modBuilder.printAuto(modStr);
			System.out.println();
		}
		modBuilder.removeAuto(modelList.get(modelList.size()-1));
		dbstuff.deleteModelFromDB(modelList.get(modelList.size()-1));
		
		System.out.println("Removed from DB and LHM: " + modelList.get(modelList.size()-1));
		modelList.remove(modelList.size()-1);
		
		if (modBuilder.countAutos() > 0) {
			
			modBuilder.updateOptionPrice(modelList.get(modelList.size()-1), "Transmission", "Automatic", 1F);
			dbstuff.updateModelInDB(modelList.get(modelList.size()-1));
		
			System.out.println ("Changed the price of Automatic Transmission in " + modelList.get(modelList.size()-1));
			System.out.println();
			
			for (String modStr : modelList) {
				modBuilder.printAuto(modStr);
				System.out.println();
			}
		}
		dbstuff.close();
	}

}
