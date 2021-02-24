package neo4j.demo;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Consumer;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.ConfigurationSource;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import neo4j.demo.model.Artist;
import neo4j.demo.model.ArtistSearch;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Command(name = "Neo4j Demo", version = "Neo4j Demo 1.0", mixinStandardHelpOptions = true)
public class App implements Runnable {

    private static final String ARTIST = "artist";
    private static final String ALBUM = "album";

    @Parameters(paramLabel = "<entity>", description = "Entity type for which to search. One of 'artist' or 'album'")
    private String entityType = "";

    @Parameters(paramLabel = "<query>", description = "Search query")
    private String searchQuery = "";

    @Override
    public void run() {
        try {
            Api api = createApi();
            if (ARTIST.equals(entityType)) {
                Response<ArtistSearch> response = api.searchForArtist(searchQuery).execute();
                System.out.println("Please make a selection:");
                for (int i = 0; i < response.body().artists.size(); i++) {
                    System.out.println("[" + (i + 1) + "] " + response.body().artists.get(i).name);
                }
                Scanner scanner = new Scanner(System.in);
                int selection = scanner.nextInt();
                System.out.println(selection);
                scanner.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }

    private Api createApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(Level.NONE);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(logging);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("User-Agent", "ics611-neo4j-demo");
                return chain.proceed(builder.build());
            }
        });

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://musicbrainz.org/ws/2/")
                .addConverterFactory(GsonConverterFactory.create()).client(client.build()).build();
        return retrofit.create(Api.class);
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
