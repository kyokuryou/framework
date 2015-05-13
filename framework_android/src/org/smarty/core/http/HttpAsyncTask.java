package org.smarty.core.http;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.client.HttpResponseException;
import org.springframework.http.ContentCodingType;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

/**
 * http异步任务
 */
public class HttpAsyncTask extends AsyncTask<Void, Void, HttpMessage> {
    protected static final String TAG = HttpAsyncTask.class.getSimpleName();
    private Map<String, Object> formData;
    private URI url;
    private HttpResponseListener httpResponseListener;

    public HttpAsyncTask(URI url) {
        this(url, null);
    }

    public HttpAsyncTask(URI url, Map<String, Object> formData) {
        Assert.notNull(url, "'url' must not be null");
        this.url = url;
        this.formData = formData;
    }

    public void setHttpResponseListener(HttpResponseListener httpResponseListener) {
        this.httpResponseListener = httpResponseListener;
    }

    @Override
    protected HttpMessage doInBackground(Void... voids) {
        try {
            return requestHttp();
        } catch (HttpResponseException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return null;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(HttpMessage httpMessage) {
        if (httpResponseListener != null) {
            httpResponseListener.refresh(httpMessage);
        }
    }

    private HttpMessage requestHttp() throws HttpResponseException {
        RestTemplate rt = new RestTemplate();

        HttpEntity<?> requestEntity = createRequestEntity();

        ResponseEntity<String> response = rt.exchange(url, HttpMethod.POST, requestEntity, String.class);

        HttpStatus hs = response.getStatusCode();
        if (HttpStatus.OK.compareTo(hs) != 0) {
            throw new HttpResponseException(hs.value(), response.toString());
        }

        HttpMessage t = new HttpMessage();
        t.setData(response.getBody());
        t.setStatus(response.getStatusCode());
        return t;
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        if (formData != null && !formData.isEmpty()) {
            requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        }
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.setAcceptEncoding(ContentCodingType.GZIP);
        return requestHeaders;
    }

    private HttpEntity<?> createRequestEntity() {
        if (formData == null || formData.isEmpty()) {
            new HttpEntity<Object>(createHttpHeaders());
        }
        return new HttpEntity<Map<String, Object>>(formData, createHttpHeaders());
    }
}
