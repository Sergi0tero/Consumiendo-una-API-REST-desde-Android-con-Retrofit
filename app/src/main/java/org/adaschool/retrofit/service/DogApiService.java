package org.adaschool.retrofit.service;
import org.adaschool.retrofit.dto.BreedsListDto;
import org.adaschool.retrofit.dto.DogDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DogApiService {
    @GET("api/breeds/list/all")
    Call<BreedsListDto> getAllBreeds();

    @GET("api/breed/{breed}/images")
    Call<DogDto> getRandomBreed(@Path("breed") String breed);
}

