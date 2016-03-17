package xyz.stavola.release_tracker;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.stavola.release_tracker.model.Release;

import java.util.Collections;
import java.util.List;

public class GithubAPI {
    private static final String ENDPOINT = "https://api.github.com/";
    private static GithubAPI INSTANCE;

    private final GithubService service;

    private GithubAPI() {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logger)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(GithubService.class);
    }

    private interface GithubService {
        @GET("repos/{org}/{repo}/releases")
        Call<List<Release>> getReleases(@Path("org") String org, @Path("repo") String repo);
    }

    //TODO: We need to handle a non 200 response
    public List<Release> getReleases(String org, String repo) {
        List<Release> releases = Collections.emptyList();

        try{
            Response<List<Release>> response =
                    service.getReleases(org, repo).execute();

            releases = response.body();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return releases;
    }

    public static GithubAPI get() {
        if(INSTANCE == null) {
            INSTANCE = new GithubAPI();
        }
        return INSTANCE;
    }
}
