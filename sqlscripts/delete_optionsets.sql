delete from optionset where modelId in 
(select modelId from model where ModelName = "");