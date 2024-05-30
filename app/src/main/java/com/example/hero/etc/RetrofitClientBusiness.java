package com.example.hero.etc;
import android.content.Context;

import com.example.hero.R;

import org.checkerframework.checker.units.qual.C;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientBusiness {
    private Context context;
    private static Retrofit retrofit = null;
    public static Retrofit getClient(Context context) {
        String service_key = context.getString(R.string.business_auth_key);
        String BASE_URL = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + service_key;

        if (retrofit == null) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

//    public static ApiService getClient(String serviceKey) {
//        return new Retrofit.Builder()
//                .baseUrl(BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//                .create(ApiService.class);
//    }
}
