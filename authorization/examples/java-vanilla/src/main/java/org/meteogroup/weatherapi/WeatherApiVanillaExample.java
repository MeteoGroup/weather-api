package org.meteogroup.weatherapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class WeatherApiVanillaExample {

  private static final String CLIENT_ID = "test-key"; // please use your own
  private static final String CLIENT_SECRET = "NSbpWeyfCrQR6K9kbpuuTBwsgLrOHtLm";  // SECRET! find a secure place to store, do NOT share
  private static final Charset UTF8 = Charset.forName("UTF-8");

  public static void main(String[] args) {
    WeatherApiVanillaExample weatherApiVanillaExample = new WeatherApiVanillaExample();
    try {
      weatherApiVanillaExample.fetch_observation_for_Berlin();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void fetch_observation_for_Berlin() throws IOException {
    URL url = new URL("https://point-observation.weather.mg/search?"
        + "locatedAt=13.42,52.52"
        + "&observedPeriod=PT0S"
        + "&fields=airTemperatureInCelsius"
    );
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    con.setRequestProperty("Authorization", "Bearer " + getAccessToken());
    try (InputStream is = con.getInputStream()) {
      String response = readAsTextAndCloseStream(is);
      System.out.println(response);
    }
  }

  /**
   * This is a simple==dirty hack to fetch the access token.
   * Please, only use for occasionally requests.
   *
   * @deprecated please use proper OAuth libraries, like e.g. https://github.com/zalando/tokens
   */
  private String getAccessToken() throws IOException {
    URL url = new URL("https://auth.weather.mg/oauth/token");
    String urlParameters = "grant_type=client_credentials";
    byte[] postData = urlParameters.getBytes(UTF8);
    int postDataLength = postData.length;
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");
    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    String secret = new String(java.util.Base64.getEncoder().encode((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
    con.setRequestProperty("Authorization", "Basic " + secret);
    con.setRequestProperty("Content-Length", Integer.toString(postDataLength));
    con.setDoInput(true);
    con.setDoOutput(true);
    try (OutputStream os = con.getOutputStream()) {
      os.write(postData);
    }
    try (InputStream is = con.getInputStream()) {
      String response = readAsTextAndCloseStream(is);
      return extractAccessToken(response);
    }
  }

  private String readAsTextAndCloseStream(InputStream is) throws IOException {
    byte[] responseBuf = new byte[4096];
    int responseSize = is.read(responseBuf);
    return new String(responseBuf, 0, responseSize, "UTF-8");
  }

  private String extractAccessToken(String response) {
    Pattern pattern = Pattern.compile("access_token\":\"([^\"]+)");
    Matcher matcher = pattern.matcher(response);
    if (!matcher.find()) {
      throw new IllegalStateException("No access token found in response: " + response);
    }
    return matcher.group(1);
  }

}