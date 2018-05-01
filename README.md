# CityInformation_Android_SQLite
### Main Activity
The main activity is responsible for initialing setting up the database the first time the app runs, allowing the user to reset the database manually, and allowing the user to initiate either of the other two activities.
### Lookup Cities
As you can see from the screenshot, the search activity contains a set of controls allowing the user to set their search criteria and a ListView showing the results of their search.
![lookupcities](https://user-images.githubusercontent.com/20994167/39495179-1ea1ddaa-4d4e-11e8-951d-dc7a263a2862.png)

If a field is left blank, that means the user does not care about that particular criteria. The “Name” and “Continent” fields allow for partial matching. Searching for “San” lists any city containing “San”. Searching for Continent “America” will find both South and North American cities. If the user leaves all three EditText fields empty, display all city data in the database.
Name and Continent search criteria is not case-sensitive which is the standard for SQL databases. For simplicity i didn't make any consideration for if the user types in any special SQL search characters something like “_o%” into one of the search fields.
### Add City
Adding cities should be fairly straightforward. For simplicity some assumptions are made such as the user enters in meaningful data in all three fields, do not worry about duplicates (i.e., the user adding the same city twice) nor worry about whether or not the user enters the correct name of one of the seven continents.
