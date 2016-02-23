package com.focuslabs.mekuanent.backgroundjobs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.focuslabs.mekuanent.backgroundjobs.parser.RSSParser;
import com.focuslabs.mekuanent.backgroundjobs.task.AsyncTaskDemo;
import com.focuslabs.mekuanent.backgroundjobs.task.ContactablesLoaderCallbacks;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView tvData = (TextView) findViewById(R.id.tv_rss_data);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                AsyncTaskDemo asyncTaskDemo = new AsyncTaskDemo(toolbar,MainActivity.this,tvData);
//                asyncTaskDemo.execute();

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                String url ="http://etb.fxexchangerate.com/rss.xml";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    FileOutputStream outputStream = MainActivity.this.openFileOutput("rss",MainActivity.MODE_PRIVATE);
                                    outputStream.write(response.getBytes());
                                    outputStream.close();

                                    FileInputStream inputStream = MainActivity.this.openFileInput("rss");

                                    tvData.setText("USING VOLLEY \n" + RSSParser.parse(inputStream));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                    tvData.setText(":-(");
                                    tvData.setTextSize(42);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    tvData.setText(":-(");
                                    tvData.setTextSize(42);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tvData.setText(error.toString());
                    }
                });
                queue.add(stringRequest);

            }
        });

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactablesLoaderCallbacks contactablesLoaderCallbacks = new ContactablesLoaderCallbacks(MainActivity.this, tvData);

                getLoaderManager().restartLoader(1, null, contactablesLoaderCallbacks);
            }
        });

        AsyncTaskDemo asyncTaskDemo = new AsyncTaskDemo(toolbar,this,tvData);
        asyncTaskDemo.execute();

        Intent intent = new Intent(this, IntentServiceDemo.class);
        Bundle bundle = new Bundle();
        bundle.putString(IntentServiceDemo.PARAM, "HELLOO");
        intent.putExtra(IntentServiceDemo.PARAM, bundle);
        startService(intent);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
