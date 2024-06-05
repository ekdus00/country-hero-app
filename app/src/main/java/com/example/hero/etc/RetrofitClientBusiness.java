package com.example.hero.etc;
import android.content.Context;

import com.example.hero.R;

import org.checkerframework.checker.units.qual.C;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientBusiness {
    // 사업자 번호 등록 인증을 위한 RetrofitClient
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "https://api.odcloud.kr/";
    public static Retrofit getClient(Context context) {
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
}
