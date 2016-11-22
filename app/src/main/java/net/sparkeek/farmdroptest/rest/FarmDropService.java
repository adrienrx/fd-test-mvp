package net.sparkeek.farmdroptest.rest;

import net.sparkeek.farmdroptest.rest.dto.DTOProducers;
import net.sparkeek.farmdroptest.rest.dto.DTORepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FarmDropService {
    @GET("/{page}/producers")
    Call<List<DTOProducers>> listProducers(@Path("page") final String psPage);
}
