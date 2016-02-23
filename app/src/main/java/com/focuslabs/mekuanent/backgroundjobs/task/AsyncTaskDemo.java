package com.focuslabs.mekuanent.backgroundjobs.task;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.focuslabs.mekuanent.backgroundjobs.parser.RSSParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by mekuanent on 2/18/16.
 */
public class AsyncTaskDemo extends AsyncTask<Void, Void, String> {


    private View view;
    private Context context;
    private TextView textView;

    public AsyncTaskDemo(View view, Context context, TextView textView){
        this.view = view;
        this.context = context;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(Void... params) {

        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 5000;


        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return "No Connection";
        }

        try {
            URL url = new URL("http://etb.fxexchangerate.com/rss.xml");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("Connection", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
//            String contentAsString = readIt(is, len);
            return RSSParser.parse(is);

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return "HeLLOOOO Don't forget about AsyncTask";
    }

    @Override
    protected void onPostExecute(String data) {
        textView.setText(data);
    }

    private String parseXMLData(String xmlData){



        return null;
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }


}
