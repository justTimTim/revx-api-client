package com.revx.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revx.api.ApiCore;
import com.revx.api.core.RevxSigningInterceptor;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApiCoreTest {

    @Mock
    private RevxSigningInterceptor mockSigningInterceptor;

    @BeforeEach
    void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        when(mockSigningInterceptor.intercept(any(Interceptor.Chain.class)))
                .thenAnswer(invocation -> {
                    Interceptor.Chain chain = invocation.getArgument(0);
                    Request original = chain.request();

                    Request signedRequest = original.newBuilder()
                            .header("Authorization", "Bearer test-key")
                            .header("X-Timestamp", String.valueOf(System.currentTimeMillis()))
                            .build();

                    return chain.proceed(signedRequest);
                });

        testService = createTestService();
    }

    private TestService createTestService() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(mockSigningInterceptor)
                .build();

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(mockWebServer.url("/").toString());
        builder.client(client);
        builder.addConverterFactory(JacksonConverterFactory.create());
        Retrofit retrofit = builder
                .build();

        return retrofit.create(TestService.class);
    }


    @Test
    void executeSync_SuccessfulResponse_ShouldReturnResponseBody1() throws IOException {
        TestResponse expectedResponse = new TestResponse("123", "Test");
        String jsonResponse = new ObjectMapper().writeValueAsString(expectedResponse);

        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(jsonResponse)
                .addHeader("Content-Type", "application/json"));

        Call<TestResponse> call = testService.getTest("123");

        // When
        TestResponse actualResponse = ApiCore.executeSync(call);

        assertNotNull(actualResponse);
        assertEquals("123", actualResponse.getId());

        verify(mockSigningInterceptor, times(1)).intercept(any(Interceptor.Chain.class));
    }


    private MockWebServer mockWebServer;
    private TestService testService;

    interface TestService {
        @GET("/test/{id}")
        Call<TestResponse> getTest(@Path("id") String id);

    }

    @Setter
    @Getter
    static class TestResponse {
        private String id;
        private String name;

        public TestResponse() {}

        public TestResponse(String id, String name) {
            this.id = id;
            this.name = name;
        }

    }

}