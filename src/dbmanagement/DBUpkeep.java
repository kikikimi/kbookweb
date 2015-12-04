/* Kimberly Disher
 * CIS 35B
 * Lab 6, database operations
 * Note this database was created on mySQL, and syntax has not been checked for compatibility with all databases.
 */
package dbmanagement;

import java.sql.*;
import java.util.*;
import adapter.*;
import automobile.*;


public class DBUpkeep extends DBBaseOpts{
	ModelBuilder modBuilder;
	Model model;
	public DBUpkeep (ModelBuilder builder) {
		super();
		modBuilder = builder;
	}
	
	public Model loadModelsFromDb (String modelName) {
		this.sqlString = "Select  Make, ModelName, BasePrice from Model";
		String whereStr;
		if (!modelName.isEmpty()) {
			whereStr = " where ModelName ='" + modelName + "';";
		}
		else whereStr = ";";
		ResultSet optionResults;
		Statement optionStatement;
		String currSet = "";
		String oldSet = "";
		try {
			this.results = this.statement.executeQuery(this.sqlString + whereStr);
			optionStatement = conn.createStatement();

			while (this.results.next()) {
				model = new Model();
				model.setMakerName(this.results.getString("Make"));
				model.setModelName(this.results.getString("ModelName"));
				model.setModelPrice(this.results.getDouble("BasePrice"));
				
				//"Order by" below will recreate the Options/Sets in a Model in the order they were inserted into the database 
				this.sqlString = "select OptionName,  OptionValue, OptionPrice from  Opts, OptionSet, Model "
						+ "where opts.OptionSetID = OptionSet.OptionSetID "
						+ "and OptionSet.ModelID = Model.ModelID "
						+ "and ModelName = \"" + this.results.getString("ModelName") + "\" order by OptionSet.OptionSetID, optionID";
				optionResults = optionStatement.executeQuery(this.sqlString);
				while(optionResults.next()) {
					currSet = optionResults.getString("OptionName");
					if (currSet.compareTo(oldSet) != 0) {
						model.addOptionSet(currSet, 2); // if it's listed there will at least be a yes and no result
						oldSet = currSet;
					}
	
					model.addOptionToLastSet(optionResults.getString("OptionValue"), 
							optionResults.getDouble("OptionPrice"));
				}
				model.setDefaultOptionChoices();
				optionResults.close();
				
				if (modelName.isEmpty())
					modBuilder.addAuto(model);
			}
		}
		catch (SQLException e) {
			System.err.println (e.getMessage());
		}
		if (!modelName.isEmpty()) 
			return model;
		else return null;
	}

	//delete model a model row and supporting optionset and option (opts table) rows in 3 queries.
	public boolean deleteModelFromDB(String modelName) {
		boolean success = false;
		String delOpts = "delete from opts where OptionSetID in "
					+"(select OptionSetId from optionset, model "
					+"where optionset.ModelID = model.modelid and model.ModelName = " 
					+ "\"" + modelName +"\");";
		String delOptSets = "delete from optionset where modelId in "
					+ "(select modelId from model where ModelName = "+ "\""+ modelName+ "\");";
		String delModel = "delete from model where ModelName = \"" + modelName + "\";";
		try {
			this.statement.executeUpdate(delOpts);
			this.statement.executeUpdate(delOptSets);
			this.statement.executeUpdate(delModel);
			success = true;
		}
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return success;
	}
	public void updateModelInDB (String modelName){
		String optSetSql = "update optionSet set ";
		String optionSql = "update opts set ";
		String modelSql = "update model set ";
		model = loadModelsFromDb(modelName);
		Model lhmModel = modBuilder.getAuto(modelName);
		String modelChange;
		String optsetChange;
		String optChange;
		try {
			if (model.getModelPrice() !=lhmModel.getModelPrice()){
				modelChange = "ModelPrice = " + lhmModel.getModelPrice() + " where ModelName = '"
						+ modelName + "';";
				statement.executeUpdate(modelSql + modelChange);
			}
			for(int i = 0; i< model.getOptionSetSize(); i++){
				for (int j = 0; j < model.getOptionCount(i); j++) {
					optChange = "";
					if (model.getOptionValue(i, j).compareTo(lhmModel.getOptionValue(i,j)) != 0) {
						optChange = "OptionValue = '"  + lhmModel.getOptionValue(i,j) + "'";
					}
					if (model.getOptionPrice(i,  j).compareTo(lhmModel.getOptionPrice(i, j)) != 0) {
						if (!optChange.isEmpty())
							optChange = optChange + ", ";
						optChange = optChange + "OptionPrice = " + lhmModel.getOptionPrice(i, j);
					}
					if (!optChange.isEmpty()) {
						optChange = optChange + " where OptionValue = '" + model.getOptionValue(i,  j)
								+"' and OptionSetID in (select OptionSetID from OptionSet, Model where" 
								+ " optionset.modelid = model.modelID and model.make = '" + model.getMakerName() + "'"
								+ " and model.modelName = '" + model.getModelName() + "');";
						statement.executeUpdate(optionSql + optChange);
					}
				}
				if (model.getOptionSetName(i).compareTo(lhmModel.getOptionSetName(i)) != 0) {
					optsetChange = "OptionName = '" + lhmModel.getOptionSetName(i) 
							+ "' where ModelId in (select ModelId from model where modelName = '"
							+ model.getModelName() + "' and Make = '" + model.getMakerName() + "');";
					statement.executeUpdate(optSetSql + optsetChange); 
				}
			}
		}
		catch (SQLException e) {System.err.println(e.getMessage());}
	}
	//add a model in a row to the model table. Calls addOptSetToDB and addOptionToDB
	public void addModelToDB (Model model) {
		String modIDSql = "select ModelID from Model where ModelName = '" 
					+ model.getModelName() + "' and Make = '" + model.getMakerName() + "';";
		int modID;
		HashMap <String, Integer> optsetIds;
		StringBuilder modSB = new StringBuilder("insert into Model (Make, ModelName, BasePrice) values ('");
		modSB.append(model.getMakerName());
		modSB.append("', '");
		modSB.append(model.getModelName());
		modSB.append("', ");
		modSB.append(model.getModelPrice());
		modSB.append(");");
		
		try {
			if (!this.statement.isClosed()) {
				this.statement.executeUpdate(modSB.toString());
				this.results = this.statement.executeQuery(modIDSql); 
				this.results.next();
				modID = this.results.getInt(1);
				optsetIds = addOptSetToDB(model, modID);
				addOptionToDB(model, optsetIds);
			}
		}
		catch (SQLException e) { System.err.println (e.getMessage()); }
	}
	private HashMap <String, Integer> addOptSetToDB (Model model, int modelId) throws SQLException{
		StringBuilder optsetSB  = new StringBuilder ("insert into OptionSet (OptionName, ModelID) values");
		String optsetIDSql = "select OptionName, OptionsetID from optionset where modelID = " + modelId + ";";
		HashMap <String, Integer> optsetIds = new HashMap <String, Integer> ();
		for (int i = 0; i < model.getOptionSetSize(); i++) {
			optsetSB.append(" ('");
			optsetSB.append(model.getOptionSetName(i));
			optsetSB.append("', ");
			optsetSB.append(modelId);
			optsetSB.append("),");
		}
		optsetSB.replace(optsetSB.length() -1,optsetSB.length(), ";");
		
		this.statement.executeUpdate(optsetSB.toString());
		this.results = this.statement.executeQuery(optsetIDSql);
		while (this.results.next()) {
			optsetIds.put(this.results.getString(1), this.results.getInt(2));
		}
		return optsetIds;
	}
	private void addOptionToDB (Model model, HashMap <String, Integer> optsetIds) throws SQLException{
		StringBuilder optSB = new StringBuilder ("insert into Opts (OptionSetID, OptionValue, OptionPrice) values");
		int id = 0;
		for (int i = 0; i < optsetIds.size(); i++) {
			id  =  optsetIds.get(model.getOptionSetName(i));
			for (int j = 0; j < model.getOptionCount(i); j++) {
				optSB.append (" (");
				optSB.append (id);
				optSB.append (", '");
				optSB.append (model.getOptionValue(i, j));
				optSB.append("', ");
				optSB.append(model.getOptionPrice(i, j));
				optSB.append("),");
			}	
		}
		optSB.replace(optSB.length() -1,optSB.length(), ";");
		statement.executeUpdate(optSB.toString());
		
	}
	public void printResults (ResultSet res){
		int colcount = 0;
		try {
				ResultSetMetaData rsmd = res.getMetaData();
				if (res.next()){
					colcount = rsmd.getColumnCount();
					for (int i = 1; i < colcount + 1; i++){
						System.out.print(rsmd.getColumnLabel(i));
					}
					System.out.println(); 
				}
			while (res.next()){
				for (int i = 1; i < colcount + 1; i++){
					System.out.print(res.getString(i));
				}
				System.out.println();
			}
			res.beforeFirst();
		}
		catch (SQLException e){
			System.err.println(e.getMessage());
		}
	}

}
