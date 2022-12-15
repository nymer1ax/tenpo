package co.com.tempo.consumer.percentage;

import lombok.RequiredArgsConstructor;
import okhttp3.HttpUrl;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PercentageURL {
    @Value("${adapter.restconsumer.url}")
    private String base;

    public HttpUrl generateUrl(){
        return  new HttpUrl.Builder()
                .scheme(base.substring(0, 4))
                .host(base.substring(7, 16))
                .port(Integer.parseInt(base.substring(17,21)))
                .addPathSegment(base.substring(22))
                .build();
    }

    public Request generateRequest(HttpUrl httpUrl){
        return new Request.Builder()
                .addHeader("Content-Type", "application/json")
                .url(httpUrl)
                .build();
    }
}
