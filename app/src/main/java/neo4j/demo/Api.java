package neo4j.demo;

import neo4j.demo.model.Artist;
import neo4j.demo.model.ArtistSearch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("artist?fmt=json&limit=5")
    Call<ArtistSearch> searchForArtist(@Query("query") String query);

    @GET("artist/{artistId}?fmt=json&inc=release-groups")
    Call<Artist> getArtistWithReleaseGroups(@Path("artistId") String artistId);

    @GET("artist/{artistId}?fmt=json&inc=artist-rels")
    Call<Artist> getArtistWithRelatedArtists(@Path("artistId") String artistId);
}
