package com.joviansoft.mpolice.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by bigbao on 14-3-20.
 */
public class JsonTest {
    public static class GpsResult {
        private int total;
       @JsonProperty("gpsDataList111")
        private List<Gps> gpsDataList;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Gps> getGpsDataList() {
            return gpsDataList;
        }

        public void setGpsDataList(List<Gps> gpsDataList) {
            this.gpsDataList = gpsDataList;
        }
    }

    public static class Gps{
        private float x;
        private float y;

        private float z;

        public float getZ() {
            return z;
        }

        public void setZ(float z) {
            this.z = z;
        }



        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }
    }

    @Test
    public void testDeSerialize() {

        String rsp = "{\"total\":2,\"gpsDataList111\":[{\"x\":120.11,\"y\":30.12, \"id\":222}]}";


        GpsResult result = convertResponse2Object(rsp, GpsResult.class);

        Assert.assertEquals(2, result.getTotal());

        Assert.assertEquals(1, result.getGpsDataList().size());

        Assert.assertEquals(result.getGpsDataList().get(0).getX(), 120.11f,0);

        System.out.println(result.getGpsDataList().get(0).getX());

    }


    @Test
    public void  testSimpleDes(){
        String rsp = "{\"x\":120.11,\"y\":30.12}";

        //    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        try {

            rsp = "{\"x\":120.11,\"y\":30.12}";

            Gps object = convertResponse2Object(rsp, Gps.class);

            Assert.assertEquals(120.11f, object.getX(), 0);

            //  return objectMapper.readValue(rsp, clazz);
        }catch (Exception e){
            e.printStackTrace();

        }

    }
    private <T> T convertResponse2Object(String rsp, Class<T> clazz) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        //objectMapper.configure(org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        try {

          //  rsp = "{\"total\":2,\"gpsDataList\":[{\"x\":120.11,\"y\":30.12}]}";

            T object = objectMapper.readValue(rsp,clazz);
            return object;

            //  return objectMapper.readValue(rsp, clazz);
        }catch (Exception e){
            e.printStackTrace();

        }
        return null;
    }
}
