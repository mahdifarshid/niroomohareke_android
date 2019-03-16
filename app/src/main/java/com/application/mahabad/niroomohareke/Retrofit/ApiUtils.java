package com.application.mahabad.niroomohareke.Retrofit;

/**
 * Created by farshid on 12/24/17.
 */

public class ApiUtils {

    private ApiUtils() {
    }

//    public static final String BASE_URL = "http://192.168.1.101/";

//            public static final String BASE_URL =  "http://192.168.43.106:8000/";
//    public static final String BASE_URL = "http://p30lord.ir/";
    public static final String BASE_URL = "http://admin.niroomohareke.ir/";
    //public static final String BASE_URL =  "http://botteach.ir/";
    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

}
