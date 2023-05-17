package org.adaschool.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.bumptech.glide.Glide;
import org.adaschool.retrofit.databinding.ActivityMainBinding;
import org.adaschool.retrofit.dto.BreedsListDto;
import org.adaschool.retrofit.dto.DogDto;
import org.adaschool.retrofit.service.DogApiService;

import java.util.Arrays;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;
    private DogApiService dogApiService = RetrofitInstance.getRetrofitInstance().create(DogApiService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Call<BreedsListDto> call = dogApiService.getAllBreeds();

        loadDogInfo();

        call.enqueue(new Callback<BreedsListDto>() {
            @Override
            public void onResponse(Call<BreedsListDto> call, Response<BreedsListDto> response) {
                if (response.isSuccessful()) {
                    Map<String, String[]> breeds = response.body().getMessage();
                    for (Map.Entry<String, String[]> entry : breeds.entrySet()) {
                        Log.d(TAG, "Raza: " + entry.getKey());
                        for (String subRaza : entry.getValue()) {
                            Log.d(TAG, "Subraza: " + subRaza);
                        }
                    }
                } else {
                    Log.e(TAG, "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(Call<BreedsListDto> call, Throwable t) {
                Log.e(TAG, "Error al llamar a la API", t);
            }
        });
    }

    private void loadDogInfo() {
        String dogBreed = "hound";
        Call<DogDto> call = dogApiService.getRandomBreed(dogBreed);
        MainActivity self = this;
        call.enqueue(new Callback<DogDto>() {
            @Override
            public void onResponse(Call<DogDto> call, Response<DogDto> response) {
                if (response.isSuccessful()) {
                    String[] dogImageUrl = response.body().getMessage();
                    binding.textView.setText(dogBreed);
                    Glide.with(self)
                            .load(dogImageUrl[(int)(Math.random() * dogImageUrl.length - 1)])
                            .into(binding.imageView);
                } else {
                    Log.e(TAG, "Error en la respuesta de la API");
                }
            }

            @Override
            public void onFailure(Call<DogDto> call, Throwable t) {
                Log.e(TAG, "Error al llamar a la API", t);
            }
        });


    }


}
