package net.sparkeek.farmdroptest.rest;

import net.sparkeek.farmdroptest.rest.dto.DTOProducersResponseItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FarmDropService {
    @GET("/{page}/producers")
    Call<List<DTOProducersResponseItem>> listProducers(@Path("page") final String psPage);
}
