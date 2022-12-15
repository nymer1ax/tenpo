package co.com.tempo.consumer.percentage;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.IOException;




@Service
@RequiredArgsConstructor
@Slf4j
public class RestPercentageConsumer {

    private final RestPercentageMapper restPercentageMapper;


    private final OkHttpClient client = new OkHttpClient.Builder().retryOnConnectionFailure(true)
            .addInterceptor(new ConnectionErrorInterceptor())
            .build();

    private final PercentageURL percentageURL;

    public PercentageResponse getPercentage() throws IOException {

        HttpUrl httpUrl = percentageURL.generateUrl().newBuilder().addPathSegment("percentage")
                .build();

        Request request = percentageURL.generateRequest(httpUrl).newBuilder().get().build();

        Response response = client.newCall(request).execute();

        String jsonData = response.body().string();

        JSONObject jsonObject = new JSONObject(jsonData);

        return restPercentageMapper.jsonObjectToPercetangeResponse(jsonObject);

    }

    private static class ConnectionErrorInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            Response response = null;
            boolean responseOK = false;
            int tryCount = 0;

            while (!responseOK && tryCount < 3) {
                try {
                    response = chain.proceed(request);
                    responseOK = response.isSuccessful();
                }catch (Exception e){
                    log.info("Request is not successful - " + tryCount);
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }finally{
                    tryCount++;
                }
            }

            // otherwise just pass the original response on
            return response == null ? chain.proceed(request) : response;


        }
    }

}
