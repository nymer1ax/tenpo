package co.com.tempo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RestConsumer // implements Gateway from domain
{

    @Value("${adapter.restconsumer.url}")
    private String url;
    private final OkHttpClient client;
    private final ObjectMapper mapper;


    // these methods are an example that illustrates the implementation of OKHTTP Client.
    // You should use the methods that you implement from the Gateway from the domain.

    public ObjectResponse testGet() throws IOException {

        Request request = new Request.Builder()
            .url(url)
            .get()
            .addHeader("Content-Type","application/json")
            .build();

        return mapper.readValue(client.newCall(request).execute().body().string(), ObjectResponse.class);
    }

    public ObjectResponse testPost() throws IOException {
        String json = mapper.writeValueAsString(ObjectRequest.builder()
            .val1("exampleval1")
            .val2("exampleval1")
            .build()
        );

        RequestBody requestBody = RequestBody
            .create(json, MediaType.parse("application/json; charset=utf-8"));

        Request request = new Request.Builder()
            .url(url)
            .post(requestBody)
            .addHeader("Content-Type","application/json")
            .build();

        return mapper.readValue(client.newCall(request).execute().body().string(), ObjectResponse.class);

    }

}
