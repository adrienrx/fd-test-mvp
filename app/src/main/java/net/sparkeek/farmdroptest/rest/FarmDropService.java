package net.sparkeek.farmdroptest.rest;

import net.sparkeek.farmdroptest.rest.dto.DTOProducersResponseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FarmDropService {
    @GET("/{fullOrNot}/producers")
    Call<DTOProducersResponseItem> listProducers(@Path("fullOrNot") final String psFullOrNot,@Query("page") final String psPage);
}
