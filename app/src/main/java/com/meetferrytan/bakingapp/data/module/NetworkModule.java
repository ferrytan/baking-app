package com.meetferrytan.bakingapp.data.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.meetferrytan.bakingapp.BuildConfig;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ferrytan on 7/4/17.
 */

@Module
public class NetworkModule {

    private String mBaseUrl;
    public NetworkModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Context context) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson(CollectionAdapter collectionAdapter) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeHierarchyAdapter(Collection.class, collectionAdapter);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache, HttpLoggingInterceptor interceptor,
                                     @Named("NETWORK_TIMEOUT") int timeOUt) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(timeOUt, TimeUnit.SECONDS)
                .readTimeout(timeOUt, TimeUnit.SECONDS)
                .writeTimeout(timeOUt, TimeUnit.SECONDS);

        if(BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(interceptor);
        }
        return clientBuilder.build();
    }

    @Provides
    @Singleton
    @Named("NETWORK_TIMEOUT")
    int provideNetworkTimeOut(){
        return 20;
    }


    @Provides
    @Singleton
    HttpLoggingInterceptor provideLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    CollectionAdapter provideCollectionAdapter(){
        return new CollectionAdapter();
    }

    public class CollectionAdapter implements JsonSerializer<Collection<?>> {
        @Override
        public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null || src.isEmpty()) // exclusion is made here
                return null;

            JsonArray array = new JsonArray();

            for (Object child : src) {
                JsonElement element = context.serialize(child);
                array.add(element);
            }

            return array;
        }
    }
}