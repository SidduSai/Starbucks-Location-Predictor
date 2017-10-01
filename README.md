# Starbucks-Location-Predictor

This will predict if a Starbucks store will be successful in a particular location or not.

1) This code uses the data from opendata.socrata.com to get the store number, latitude and longitude.

2) Using this lat and long values, the FetchJSONObject.java code retrieves the attributes around each 
starbuck location. This is done using google places API and is stored in starbucks.xlsx

3) This .xlsx file is converted into .arff to give it to the Weka API

4) Using Weka API, classification is performed to classify data into two sets, one is the store which is
in a big city and other is the store which is in rural area.

5) Naive Bayes is used to predict what is the probability of a store being successful in a particular
location
