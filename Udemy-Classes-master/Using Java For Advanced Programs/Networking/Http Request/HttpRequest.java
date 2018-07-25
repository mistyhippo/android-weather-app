import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import org.json.*;

public class HttpRequest {
	
	private static String readInput(BufferedReader reader) throws IOException{
		StringBuilder builder = new StringBuilder();
		String buffer;
		while ((buffer = reader.readLine()) != null) {
			builder.append(buffer);
		}
		return builder.toString();
	}
	
	private static JSONObject downloadFile(String url) {
		try {
		InputStream inputStream = new URL(url).openStream();
		BufferedReader inputReader = new BufferedReader(new InputStreamReader(inputStream));
		String strJson = readInput(inputReader);
		return new JSONObject(strJson);
		} catch (IOException e) {
			System.out.println("IOException "+e);
		} catch (JSONException e) {
			System.out.println("JsonException "+e);
		}
		return null;
	}
	
	public static void main(String[] args) throws JSONException{
		System.out.println("Enter in the url of your JSON to be parsed");
		Scanner scanner = new Scanner(System.in);
		JSONObject json = downloadFile(scanner.nextLine());
		JSONArray names = json.names();
		for (int i = 0; i < json.names().length(); i++) {
			System.out.printf(names.get(i).toString()+"\t");
			System.out.println(json.get(names.get(i).toString()));
		}
	}

}
