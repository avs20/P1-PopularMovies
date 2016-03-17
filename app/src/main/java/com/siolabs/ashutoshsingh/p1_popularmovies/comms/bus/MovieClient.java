package com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus;

import android.content.Context;
import android.util.Log;

import com.siolabs.ashutoshsingh.p1_popularmovies.models.Movie;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by ashutoshsingh on 16-03-2016.
 */
public class MovieClient {

    private static Retrofit mRetrofit;
    private static OkHttpClient.Builder mHttpClient;
    private static Context mContext;

    private static MovieClient movieClient;


    public static MovieClient getClient(Context context){

        if(movieClient == null){
            movieClient = new MovieClient();
            mContext = context;
        }

        return movieClient;

    }


    private MovieClient(){
        //mHttpClient = new OkHttpClient.Builder();



        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public MovieService getMovieService()
    {



        return mRetrofit.create(MovieService.class);
    }

    public static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            String requestLog = String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers());
            //YLog.d(String.format("Sending request %s on %s%n%s",
            //        request.url(), chain.connection(), request.headers()));
            if(request.method().compareToIgnoreCase("post")==0){
                requestLog ="\n"+requestLog+"\n"+bodyToString(request);
            }
            Log.d("TAG","request"+"\n"+requestLog);

            Response response = chain.proceed(request);
            long t2 = System.nanoTime();

            String responseLog = String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers());

            String bodyString = response.body().string();

            Log.d("TAG","response"+"\n"+responseLog+"\n"+bodyString);

            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
            //return response;
        }
    }

    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }




}
