## Starbucks-Location-Predictor

#This will predict if a Starbucks store will be successful in a particular location or not.

- This code uses the data from opendata.socrata.com to get the store number, latitude and longitude.

- Using this lat and long values, the FetchJSONObject.java code retrieves the attributes around each 
starbuck location. This is done using google places API.

- The google places api url sample is https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=43.031873,-71.073203&radius=500&key=

- This .xlsx file is converted into .arff to give it to the Weka API

- Using Weka API, classification is performed to classify data into two sets, one is the store which is
in a big city and other is the store which is in rural area.

- Naive Bayes is used to predict what is the probability of a store being successful in a particular
location
