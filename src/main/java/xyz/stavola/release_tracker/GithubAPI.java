package xyz.stavola.release_tracker;

import com.squareup.moshi.Moshi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import xyz.stavola.release_tracker.model.ReleaseAdapter;
import xyz.stavola.release_tracker.model.transformed.Release;

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

        Moshi moshi = new Moshi.Builder()
                .add(new ReleaseAdapter())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();

        this.service = retrofit.create(GithubService.class);
    }

    private interface GithubService {
        @GET("repos/{org}/{repo}/releases")
        Call<Release[]> getReleases(@Path("org") String org, @Path("repo") String repo);
    }

    //TODO: We need to handle a non 200 response
    public Release[] getReleases(String org, String repo) {
        Release[] releases = new Release[0];

        try{
            Response<Release[]> response =
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
