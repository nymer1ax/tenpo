package co.com.tempo.consumer.percentage;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;


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

        Response response = null;

        try {
             response = client.newCall(request).execute();
        }catch (ConnectException ex){
            return null;
        }

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
            int tryCount = 1;
            while (!responseOK && tryCount < 4) {
                try {
                    log.info("Intentando request.. " );
                    response = chain.proceed(request);
                    responseOK = response.isSuccessful();
                }catch (Exception e){
                    log.info("Request is not successful:  # " + tryCount);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                }finally{
                    tryCount++;
                }
            }

            if(response == null){
                throw new ConnectException("La conexión ha fallado");
            }
            log.info("Se ha ejecutado el request con exíto.. " );
            return response;


        }
    }

}
