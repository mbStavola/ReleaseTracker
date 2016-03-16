package xyz.stavola.release_tracker;

import com.squareup.moshi.Moshi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

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

        Moshi moshi = new Moshi.Builder()
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
        List<ReleaseModel> getReleases(@Path("org") String org, @Path("repo") String repo);
    }

    public List<ReleaseModel> getReleases(String org, String repo) {
        return service.getReleases(org, repo);
    }

    public static GithubAPI get() {
        if(INSTANCE == null) {
            INSTANCE = new GithubAPI();
        }
        return INSTANCE;
    }
}
