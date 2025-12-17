package com.revx.api;


import com.revx.api.core.RevxSigningInterceptor;
import com.revx.api.exception.ApiError;
import com.revx.api.exception.ApiRevxException;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.concurrent.TimeUnit;

public class ApiCore {

    private static final OkHttpClient sharedClient;
    private static final Converter.Factory converterFactory = JacksonConverterFactory.create();

    private ApiCore() {
    }

    static {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequestsPerHost(1000);
        dispatcher.setMaxRequests(1000);
        sharedClient = new OkHttpClient.Builder()
                .dispatcher(dispatcher)
                .pingInterval(20, TimeUnit.SECONDS)
                .build();
    }

    public static <S> S create(Class<S> service, String url, String apiKey, String secret) {
        if (apiKey == null || secret == null) {
            throw new IllegalArgumentException("apiKey and secret cannot be null.");
        }
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(converterFactory);

        OkHttpClient client = sharedClient.newBuilder()
                .addInterceptor(new RevxSigningInterceptor(apiKey, secret))
                .build();

        retrofitBuilder.client(client);

        Retrofit retrofit = retrofitBuilder.build();
        return retrofit.create(service);
    }

    @SuppressWarnings("PMD")
    public static <T> T executeSync(Call<T> call) {
        try {
            Response<T> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            }

            throw parseApiError(response);

        } catch (IOException e) {
            //network error
            throw new RuntimeException(e.getMessage());
        }
    }

    private static RuntimeException parseApiError(Response<?> response) {
        ResponseBody errorBody = response.errorBody();
        if (errorBody == null) {
            return new RuntimeException("HTTP " + response.code() + " with empty error body");
        }

        try {
            ApiError apiError = errorConverter.convert(errorBody);

            return new ApiRevxException(apiError.getMessage(), apiError.getErrorId(), apiError.getTimestamp(),
                    response.code());

        } catch (Exception e) {
            return new ApiRevxException(e.getMessage(), null, System.currentTimeMillis(), e);
        }
    }


    @SuppressWarnings("unchecked")
    private static final Converter<ResponseBody, ApiError> errorConverter =
            (Converter<ResponseBody, ApiError>) converterFactory.responseBodyConverter(
                    ApiError.class, new Annotation[0], null);


}
