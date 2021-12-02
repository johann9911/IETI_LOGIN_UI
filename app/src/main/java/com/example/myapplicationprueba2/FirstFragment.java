package com.example.myapplicationprueba2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplicationprueba2.databinding.FragmentFirstBinding;
import com.example.myapplicationprueba2.network.RetrofitGenerator;
import com.example.myapplicationprueba2.network.dto.LoginDto;
import com.example.myapplicationprueba2.network.dto.TokenDto;
import com.example.myapplicationprueba2.network.service.AuthService;
import com.example.myapplicationprueba2.network.storage.SharedPreferencesStorage;
import com.example.myapplicationprueba2.network.storage.Storage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Retrofit;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);
    Storage storage;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLoging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          //      NavHostFragment.findNavController(FirstFragment.this)
            //            .navigate(R.id.action_FirstFragment_to_SecondFragment);
                if(validLoginForm()){
                    sendAuthRequest();
                }

            }
        });
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("SHARED_preferences", Context.MODE_PRIVATE);
        storage = new SharedPreferencesStorage(sharedPreferences);
    }

    private boolean validLoginForm() {
        Editable usernNameText = binding.inputUsername.getText();
        Editable passwordNameText = binding.inputPassword.getText();
        if(usernNameText.length()==0 && !Patterns.EMAIL_ADDRESS.matcher(usernNameText).matches()){
            binding.inputUsername.setError(getString(R.string.invalid_email));
        }else if (passwordNameText.length()==0){
            binding.inputUsername.setError(null);
            binding.inputPassword.setError(getString(R.string.invalid_password));
        }else{
            binding.inputUsername.setError(null);
            binding.inputPassword.setError(null);
            return true;
        }
        return false;
    }

    private void sendAuthRequest() {
        Retrofit retrofit= RetrofitGenerator.getInstace(storage);
        AuthService authService= retrofit.create(AuthService.class);
        //LoginDto loginDto = new LoginDto("santiago@mail.com", "passw0rd");
        LoginDto loginDto = new LoginDto(binding.inputUsername.getText().toString(), binding.inputPassword.getText().toString());
        Action1<TokenDto> successAction = tokenDto ->  onSuccess(tokenDto.getAccessToken());
        Action1<Throwable> failedAction = throwable ->  Log.e("Developer", "Auth error:",throwable);
        authService.auth(loginDto)
                .observeOn(Schedulers.from(ContextCompat.getMainExecutor(requireContext())))
                .subscribe(successAction, failedAction);

    }

    private void onSuccess(String token){

        Log.d("Developer", "TOKERDTO:" + token);
        //getActivity().runOnUiThread(() -> binding.textviewFirst.setText(token));
        storage.setToken(token);
        NavHostFragment.findNavController(FirstFragment.this)
                .navigate(R.id.action_FirstFragment_to_SecondFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}