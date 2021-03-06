package io.github.mainstringargs.polygon.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.mainstringargs.alpaca.properties.AlpacaProperties;

/**
 * The Class PolygonRequest.
 */
public class PolygonRequest {

  /** The logger. */
  private static Logger LOGGER = LogManager.getLogger(PolygonRequest.class);

  /** The Constant USER_AGENT_KEY. */
  private static final String USER_AGENT_KEY = "user-agent";

  /** The Constant API_KEY_ID. */
  private static final String API_KEY_ID = "apiKey";

  /** The key id. */
  private String keyId;

  /**
   * Instantiates a new polygon request.
   *
   * @param keyId the key id
   */
  public PolygonRequest(String keyId) {
    this.keyId = keyId;
  }



  /**
   * Invoke get.
   *
   * @param builder the builder
   * @return the http response
   */
  public HttpResponse<JsonNode> invokeGet(PolygonRequestBuilder builder) {
    HttpResponse<JsonNode> response = null;
    try {

      builder.appendURLParameter(API_KEY_ID, keyId);

      LOGGER.debug("Get URL " + builder.getURL());

      response = Unirest.get(builder.getURL())
          .header(USER_AGENT_KEY, AlpacaProperties.USER_AGENT_VALUE).asJson();

      LOGGER.debug("GET status: " + response.getStatus() + "\n\t\t\t\t\tstatusText: "
          + response.getStatusText() + "\n\t\t\t\t\tBody: " + response.getBody());


    } catch (UnirestException e) {
      LOGGER.info("UnirestException", e);
    }

    return response;
  }

  /**
   * Invoke post.
   *
   * @param builder the builder
   * @return the http response
   */
  public HttpResponse<JsonNode> invokePost(PolygonRequestBuilder builder) {
    HttpResponse<JsonNode> response = null;
    try {


      builder.appendURLParameter(API_KEY_ID, keyId);

      LOGGER.debug("Post URL " + builder.getURL());
      LOGGER.debug("Post Body " + builder.getBodyAsJSON());

      response =
          Unirest.post(builder.getURL()).header(USER_AGENT_KEY, AlpacaProperties.USER_AGENT_VALUE)
              .body(builder.getBodyAsJSON()).asJson();

      LOGGER.debug("POST status: " + response.getStatus() + "\n\t\t\t\t\tstatusText: "
          + response.getStatusText() + "\n\t\t\t\t\tBody: " + response.getBody());


    } catch (UnirestException e) {
      LOGGER.info("UnirestException", e);
    }

    return response;
  }

  /**
   * Invoke delete.
   *
   * @param builder the builder
   * @return the http response
   */
  public HttpResponse<JsonNode> invokeDelete(PolygonRequestBuilder builder) {
    HttpResponse<JsonNode> response = null;
    try {


      builder.appendURLParameter(API_KEY_ID, keyId);

      LOGGER.debug("Delete URL " + builder.getURL());

      response = Unirest.delete(builder.getURL())
          .header(USER_AGENT_KEY, AlpacaProperties.USER_AGENT_VALUE).asJson();

      LOGGER.debug("DELETE status: " + response.getStatus() + "\n\t\t\t\t\tstatusText: "
          + response.getStatusText() + "\n\t\t\t\t\tBody: " + response.getBody());


    } catch (UnirestException e) {
      LOGGER.info("UnirestException", e);
    }

    return response;
  }


  /**
   * Gets the response object.
   *
   * @param <T> the generic type
   * @param httpResponse the http response
   * @param type the type
   * @return the response object
   */
  public <T> T getResponseObject(HttpResponse<JsonNode> httpResponse, Type type) {

    GsonBuilder gsonBuilder = new GsonBuilder();
    gsonBuilder.setLenient();
    Gson gson = gsonBuilder.create();

    T responseObjectFromJson = null;

    BufferedReader br = null;

    try {
      br = new BufferedReader(new InputStreamReader(httpResponse.getRawBody()));
      responseObjectFromJson = gson.fromJson(br, type);
    } catch (Exception e) {
      LOGGER.info("Exception", e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          LOGGER.info("IOException", e);
        }
      }
    }

    return responseObjectFromJson;

  }

}
