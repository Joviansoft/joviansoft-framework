package com.joviansoft.mpolice.rest;

import com.joviansoft.gps.domain.Message;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created by bigbao on 14-2-27.
 */
public class ClientTest {

    enum Color {
        RED(255, 0, 0), GREEN(0, 255, 0), BLUE(0, 0, 255);

        private Color(int r, int g, int b) {
            this.r = r;
            this.g = g;
            this.b = b;
        }

        private int r;
        private int g;
        private int b;

        @Override
        public String toString() {
            return super.toString() + r + g + b;
        }
    }

    private RestTemplate restTemplate = new RestTemplate();

    private String url = "http://localhost:8080/test";
    private String secret = "111111";
    private String appKey = "10001";
    private String sessionId;

    private String Logon() {

        String username = "test";
        String password = "123";
        Message bean = restTemplate.getForObject(url + "?username=1&password=" + password, Message.class);

        return "";
    }

    @Test
    public void testGet() {

        MultiValueMap<String, String> vars = new LinkedMultiValueMap<String, String>();
        vars.add("message", "123456");
        vars.add("id", "1");

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(vars, headers);


        ResponseEntity<Message> test = restTemplate.exchange("http://localhost:8080/test", HttpMethod.GET, entity, Message.class);
    }

    @Test
    public void testGetWithMap() {


        MultiValueMap<String, String> mapParams = new LinkedMultiValueMap<String, String>();



        mapParams.add("id", "221");
        // mapParams.put("id", "1");
        mapParams.add("message", "你好");

        try {
            // restTemplate.getForObject()
            //   restTemplate.postForObject()
            String result = restTemplate.postForObject(url, mapParams, String.class);
            System.out.println(result);
        } catch (HttpClientErrorException e) {

        }
    }

    @Test
    public void testGetWithUrl() throws UnsupportedEncodingException {

        Color color = Color.BLUE;
        System.out.println(color);
        switch (color){
            case RED:
                break;
            case GREEN:
                break;
            case BLUE:
                break;
            default:
                break;
        }
        Color c2 = Color.GREEN;
        Color.BLUE.ordinal();
        Color.values();
        if(color.compareTo(Color.BLUE) == 0){

        }

        String msg = "你好";
        msg = URLEncoder.encode(msg, "UTF-8");
        JovianResponseErrorHandler errorHandler = new JovianResponseErrorHandler();

        restTemplate.setErrorHandler(errorHandler);
        String strRsp = restTemplate.getForObject(url + "?id=100&message=" + msg, String.class);

        // Message bean = restTemplate.getForObject(url + "?id=100&message=" + msg, Message.class);

        if (errorHandler.getStatusCode() == HttpStatus.BAD_REQUEST) {

            System.out.println("400错误");

        } else {
            System.out.println(strRsp);
        }


        switch (errorHandler.getStatusCode()) {
            case BAD_REQUEST:
                break;
            case OK:
                break;
            case NOT_FOUND:
                System.out.println("404 错误，地址没找到");
                break;
        }

        //  DefaultResponseErrorHandler handler;

        //  restTemplate.getErrorHandler().

        // System.out.println(strRsp);
        //  System.out.println(bean.getId() + "\r\n" + bean.getMessage());


    }

    @Test
    public void testGetWithUrl2() throws UnsupportedEncodingException {

//        String msg = "你好";
//        msg = URLEncoder.encode(msg, "UTF-8");
//        GpsResponse gpsResponse = restTemplate.getForObject(url + "?id=1&message=" + msg, GpsResponse.class);
//
//        System.out.println(gpsResponse.getGpsBean().getId());
//        //  System.out.println(gpsResponse.getId() + "\r\n" + gpsResponse.getMessage());

        DefaultResponseErrorHandler handler;
    }

    class JovianResponseErrorHandler implements ResponseErrorHandler {

        private HttpStatus httpStatus;

        /**
         * Indicates whether the given response has any errors.
         * Implementations will typically inspect the {@link org.springframework.http.client.ClientHttpResponse#getStatusCode() HttpStatus}
         * of the response.
         *
         * @param response the response to inspect
         * @return {@code true} if the response has an error; {@code false} otherwise
         * @throws java.io.IOException in case of I/O errors
         */
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            httpStatus = response.getStatusCode();
            return httpStatus == HttpStatus.OK;

        }

        /**
         * Handles the error in the given response.
         * This method is only called when {@link #hasError(org.springframework.http.client.ClientHttpResponse)} has returned {@code true}.
         *
         * @param response the response with the error
         * @throws java.io.IOException in case of I/O errors
         */
        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            String errorRsp = response.getBody().toString();
            System.out.println(errorRsp);
        }

        public HttpStatus getStatusCode() {
            return httpStatus;
        }
    }

}
