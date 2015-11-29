This is Kimberly Disher's Lab6. 

Output files - 
?
 
Input files -
Used when starting a Server:
automobile.txt (config for a auto Model). 
automobile2.txt (different config for an auto Model).

Added for Lab 6
Lab 6 adds servlets and a .jsp file for user interaction in a browser.
SelectModel presents a drop-down list of car models from the server.
ConfigAuto has a user select option choices via a drop-down for each option set.
carOptions.jsp displays selected options, prices, and a total cost.
autoassist.ConfigHelper adds the user's choices to a Model instance for carOptions.jsp.


Updates for Lab 6
CarModelOptionsIO - the "client" now has more atomized operations, such as getModels() and getModelOptions().
 These were previously done in handleSession() and debug methods. 
 ModelServer still runs independently on server-side. The servlets call on the client, CarModelOptionsIO.


The design diagram is Lab6Diagram.jpg

---------
Main changes from Lab 5 - adds client/server capabilities in new packages "client" and "server."
Main changes from Lab 4 - demonstrates capabilities for threading and scaling, data changing methods in Model are synchronized.
Main changes from Lab 3 - added a LinkedHashMap to handle a group of Model Object in ProxyAuto. Changed _optset in Model and 
		_options in OptionSet to ArrayLists from simple arrays.

Here are errors and codes added in Lab 2:

10404 file not found
10206 missing element in option line 
102061 missing element in option set line
102062 number not in correct format --used for both OptionSet and Option lines
102063 option set count not in correct format
102064 model name missing


