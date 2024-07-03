package org.unibl.etf.Util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.unibl.etf.MainApplication;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpUtil {
    public static void sendHttpPostRequest(String url, String json) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    System.out.println("Server response: " + responseEntity.getContent());
                }
            } catch (IOException e) {
                Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, e.getMessage());
            }
        } catch (IOException e) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, e.getMessage());
        }
    }

    public static String sendHttpGetRequest(String url) {
        String responseString = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    responseString = EntityUtils.toString(responseEntity);
                }
            } catch (IOException e) {
                Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, e.getMessage());
            }
        } catch (IOException e) {
            Logger.getLogger(MainApplication.class.getName()).log(Level.SEVERE, e.getMessage());
        }

        return responseString;
    }

}
