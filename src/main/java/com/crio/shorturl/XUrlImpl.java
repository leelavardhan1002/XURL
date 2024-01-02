package com.crio.shorturl;

import java.util.HashMap;
import java.util.Map;

public class XUrlImpl implements XUrl {

  private Map<String, String> longToShortUrlMap;
  private Map<String, String> shortToLongUrlMap;
  private Map<String, Integer> longUrlHitCountMap;

  public XUrlImpl() {
    longToShortUrlMap = new HashMap<>();
    longUrlHitCountMap = new HashMap<>();
    shortToLongUrlMap = new HashMap<>();
  }

  @Override
  public String registerNewUrl(String longUrl) {
    // If longUrl already has a corresponding shortUrl, return that shortUrl
    if (longToShortUrlMap.containsKey(longUrl)) {
        longUrlHitCountMap.put(longUrl, getHitCount(longUrl)+1);
        return longToShortUrlMap.get(longUrl);
    }

    // If longUrl is new, create a new shortUrl for the longUrl and return it
    String shortUrl = generateShortUrl();
    longToShortUrlMap.put(longUrl, shortUrl);
    shortToLongUrlMap.put(shortUrl, longUrl);
    longUrlHitCountMap.put(longUrl, getHitCount(longUrl)+1);
    return shortUrl;
  }

  @Override
  public String registerNewUrl(String longUrl, String shortUrl) {
    // If shortUrl is already present, return null
    if (longToShortUrlMap.containsValue(shortUrl)) {
        return null;
    }

    // Else, register the specified shortUrl for the given longUrl
    longToShortUrlMap.put(longUrl, shortUrl);
    shortToLongUrlMap.put(shortUrl, longUrl);
    longUrlHitCountMap.put(longUrl, getHitCount(longUrl)+1);
    return shortUrl;
  }

  @Override
  public String getUrl(String shortUrl) {
    // If shortUrl doesn't have a corresponding longUrl, return null
    if (shortToLongUrlMap.containsKey(shortUrl)) {
      return (shortToLongUrlMap.get(shortUrl));
    }
    return null;
  }

  @Override
  public Integer getHitCount(String longUrl) {
    // Return the number of times the longUrl has been looked up using getUrl()

    return longUrlHitCountMap.getOrDefault(longUrl, 0);
  }

  @Override
  public String delete(String longUrl) {
    // Delete the mapping between this longUrl and its corresponding shortUrl
    shortToLongUrlMap.remove(longToShortUrlMap.get(longUrl));
    longToShortUrlMap.remove(longUrl);
    return (longToShortUrlMap.get(longUrl));

    // Do not zero the Hit Count for this longUrl
    // longUrlHitCountMap.remove(longUrl);

    // return shortUrl;
  }

  // Helper method to generate a random alphanumeric shortUrl
  private String generateShortUrl() {
    // Generate a random alphanumeric string (you may use a more sophisticated logic)
    String alphanumericString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    StringBuilder shortUrl = new StringBuilder("http://short.url/");
    for (int i = 0; i < 9; i++) {
      int index = (int) (Math.random() * alphanumericString.length());
      shortUrl.append(alphanumericString.charAt(index));
    }
    return shortUrl.toString();
  }
}
