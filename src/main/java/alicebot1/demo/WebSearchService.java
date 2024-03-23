package Chatbot.Chatbot_demo;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class WebSearchService {
	public static String searchWeb(String query) {
		String apiKey = "AIzaSyBmiqdDSp6K8pW6C63jFeMSWZ1PwqSpkko";
		String cxNumber = "43b894565599241fc";
		String searchURL = "https://www.googleapis.com/customsearch/v1?" + "key=" + apiKey + "&" + "cx=" + cxNumber
				+ "&" + "q=" + query;

		try (CloseableHttpClient httpclient = HttpClients.createDefault();) {
			HttpGet request = new HttpGet(searchURL);
			HttpResponse response = httpclient.execute(request);
			String json = EntityUtils.toString(response.getEntity());
			JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
			JsonArray items = jsonObject.getAsJsonArray("items");

			StringBuilder result = new StringBuilder();
			for (int i = 0; i < items.size(); i++) {
				JsonObject item = items.get(i).getAsJsonObject();
				String title = item.get("title").getAsString();
				String link = item.get("link").getAsString();
				String snippet = item.get("snippet").getAsString();
				result.append(title).append("\n").append(snippet).append("\n").append(link).append("\n\n");
			}

			return result.length() > 0 ? result.toString() : "No results found.";

		} catch (Exception e) {
			e.printStackTrace();
			return "Error fetching search results.";

		}

		
	}

}
