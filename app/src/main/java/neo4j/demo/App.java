package neo4j.demo;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import neo4j.Database;
import neo4j.demo.model.Artist;
import neo4j.demo.model.ArtistSearch;
import neo4j.demo.model.Release;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
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

    private void searchForArtist() throws IOException {
        Api api = createApi();
        Response<ArtistSearch> response = api.searchForArtist(searchQuery).execute();
        System.out.println("Please make a selection:");
        List<Artist> artists = response.body().artists;
        for (int i = 0; i < artists.size(); i++) {
            Artist artist = artists.get(i);
            System.out.println("[" + (i + 1) + "] " + artist.name + " (" + artist.disambiguation + ")");
        }
        Scanner scanner = new Scanner(System.in);
        int selection = scanner.nextInt();
        String artistId = artists.get(selection - 1).id;
        Response<Artist> artistResponse = api.getArtistWithReleaseGroups(artistId).execute();
        Artist artist = artistResponse.body();
        Database db = new Database();
        db.addArtistAndReleases(artist);
        if (artist.type.equals("Group")) {
            artist = api.getArtistWithRelatedArtists(artistId).execute().body();
            db.addMembersOfBand(artist);
        }
        scanner.close();
    }

    @Override
    public void run() {
        try {
            if (ARTIST.equals(entityType)) {
                searchForArtist();
            } else if (ALBUM.equals(entityType)) {

            } else {
                throw new RuntimeException("Unknown command " + entityType);
            }
        } catch (IOException ex) {
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
