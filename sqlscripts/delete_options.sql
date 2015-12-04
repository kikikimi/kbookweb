delete from opts where OptionSetID in 
(select OptionSetId from optionset, model 
where optionset.ModelID = model.modelid and model.ModelName = "");