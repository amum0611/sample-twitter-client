package demo.idea.pro;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;

public class TwitterClient {

    public String search (String hashTag) throws IOException {

        String searchUrl = "http://search.twitter.com/search.json?q={0}&rpp=1";

        URL url = new URL(MessageFormat.format(searchUrl, hashTag));
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();

        InputStreamReader reader = new InputStreamReader(inputStream);

        JsonParser jsonParser = new JsonParser();
        JsonObject asJsonObject = jsonParser.parse(reader).getAsJsonObject();

        JsonArray results = asJsonObject.get("results").getAsJsonArray();

        if (results.size() != 0) {
            return results.get(0).getAsJsonObject().get("text").toString();
        }
        return "No results found.";

    }
}
