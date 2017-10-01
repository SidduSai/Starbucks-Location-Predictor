package ua.edu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

public class FetchJSONObject {

	static String[] longitudeArray;
	static String[] latitudeArray;
	static String[] storeArray;
	static Set<String> attributeSet;
	static String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?radius=500&sensor=false&";
	static String key1 = "key=AIzaSyAPeFn1fRCwyFOWmBo17nWj1YkqerskPH0&";
	static String key2 = "key=AIzaSyDsfDnzIiQjdRlCF5xK4PLCUOdQ98OkQHY&";
	static String key3 = "key=AIzaSyDo4LJLgiDro4adOalhBW04A0JckF235wA&";
	static String key4 = "key=AIzaSyDWfJNlZUBKhmY3tQekzpMoJLZnSQGZg9Y&";

	public static void main(String[] args) {

		try {
			readLocationFile();
			if (longitudeArray != null && latitudeArray != null) {

				String location = "D:\\Akron\\Academics\\Data Mining\\Final_Project\\JSON\\DD\\";
				// for SB 4333 done
				int keyCount = 0;
				int keyNumber = 1;
				for (int i = 334; i < 1634; i++) {
					boolean starBucksLoc = false;
					int counter = 0;
					keyCount++;
					JSONObject jsonObject0 = null;
					JSONObject jsonObject1 = null;
					JSONObject jsonObject2 = null;

					if (keyCount == 334) {
						keyNumber++;
						keyCount = 1;
					}
					String finalURL = constructURL(i, keyNumber);
					JSONObject jsonObj = readJsonFromUrl(finalURL);
					if (!jsonObj.toString().contains("Starbucks")) {
						jsonObject0 = jsonObj;
					} else {
						starBucksLoc = true;
					}

					while (jsonObj.toString().contains("next_page_token")) {

						if (!jsonObj.toString().contains("Starbucks")) {
							counter++;
							String pageTokenURL = finalURL
									.concat("&pagetoken=").concat(
											(String) jsonObj
													.get("next_page_token"));

							jsonObj = readJsonFromUrl(pageTokenURL);

							if (counter == 1) {
								jsonObject1 = jsonObj;
							} else {
								jsonObject2 = jsonObj;
							}

							if (counter == 2) {
								break;
							}
						} else {
							starBucksLoc = true;
							break;
						}
					}

					//Persistence code
					if (!starBucksLoc) {

						String outputFileName0 = "DD-" + storeArray[i] + "-"
								+ 0 + ".json";
						String outputFileName1 = "DD-" + storeArray[i] + "-"
								+ 1 + ".json";
						String outputFileName2 = "DD-" + storeArray[i] + "-"
								+ 2 + ".json";

						if (jsonObject0 != null) {
							FileWriter fw = new FileWriter(location
									+ outputFileName0);
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(jsonObject0.toString());

							bw.close();
						}

						if (jsonObject1 != null) {
							FileWriter fw1 = new FileWriter(location
									+ outputFileName1);
							BufferedWriter bw1 = new BufferedWriter(fw1);
							bw1.write(jsonObject1.toString());

							bw1.close();
						}

						if (jsonObject2 != null) {
							FileWriter fw2 = new FileWriter(location
									+ outputFileName2);
							BufferedWriter bw2 = new BufferedWriter(fw2);
							bw2.write(jsonObject2.toString());

							bw2.close();
						}
					}else{
						System.out.println( "starbuck is present at "+storeArray[i] + "location");
					}

				}

			}

			System.out.println("Done!");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String constructURL(int i, int keyNumber) {
		StringBuilder urlSB = new StringBuilder();
		if (keyNumber == 1) {
			urlSB.append(url).append(key1).append("location=")
					.append(longitudeArray[i]).append(",")
					.append(latitudeArray[i]);
		} else if (keyNumber == 2) {
			urlSB.append(url).append(key2).append("location=")
					.append(longitudeArray[i]).append(",")
					.append(latitudeArray[i]);

		} else if (keyNumber == 3) {
			urlSB.append(url).append(key3).append("location=")
					.append(longitudeArray[i]).append(",")
					.append(latitudeArray[i]);
		} else {
			urlSB.append(url).append(key4).append("location=")
					.append(longitudeArray[i]).append(",")
					.append(latitudeArray[i]);
		}

		return urlSB.toString();

	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String jsonText = readData(rd);
			JSONObject obj = new JSONObject(jsonText.trim());
			return obj;
		} finally {
			is.close();
		}
	}

	public static void readLocationFile() {
		String fileLocation = "D:\\Akron\\Academics\\Data Mining\\Final_Project\\DDLocation\\DDLoc.csv";

		try {
			BufferedReader br = new BufferedReader(new FileReader(fileLocation));
			String line = "";
			StringBuilder longitudeSB = new StringBuilder();
			StringBuilder latitudeSB = new StringBuilder();
			StringBuilder storeNumberSB = new StringBuilder();

			while ((line = br.readLine()) != null) {
				String[] lineArray = line.split(",");
				longitudeSB.append(lineArray[1]).append(",");
				latitudeSB.append(lineArray[2]).append(",");
				storeNumberSB.append(lineArray[0]).append(",");
			}

			longitudeArray = longitudeSB.toString().split(",");
			latitudeArray = latitudeSB.toString().split(",");
			storeArray = storeNumberSB.toString().split(",");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String readData(BufferedReader rd) {

		StringBuffer sb = new StringBuffer();
		try {
			String line = "";
			// sb.append(rd.readLine());
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb.toString();

	}
}
