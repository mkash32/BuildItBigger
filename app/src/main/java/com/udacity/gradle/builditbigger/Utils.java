package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;

import com.example.mkash32.jokeandroidlib.JokesActivity;
import com.example.mkash32.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by mkash32 on 9/7/16.
 */
public class Utils {
    public static class EndpointsAsyncTask extends AsyncTask<Void, Void, String> {
        private MyApi myApiService = null;

        @Override
        protected String doInBackground(Void... params) {
            if(myApiService == null) {
                // Replace "android-app-backend" with the project ID of your backend deployment
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://android-app-backend.appspot.com/_ah/api/");
                /*
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        })
                        .setApplicationName("BuildItBigger");
                */
                myApiService = builder.build();
            }

            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }
    }

    // Async Task for testing the EndpointsAsyncTask, onPostExecute overrridden to obtain the raw result
    public static class EndpointsTestAsyncTask extends EndpointsAsyncTask {
        private CountDownLatch signal;
        private String result;

        public EndpointsTestAsyncTask(CountDownLatch signal) {
            this.signal = signal;
        }

        @Override
        protected void onPostExecute(String result) {
            this.result = result;
            // Alerts that task is finished
            signal.countDown();
        }

        public String getResult() {
            return result;
        }
    }
}
