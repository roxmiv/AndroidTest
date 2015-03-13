package ru.reshuege.someactivites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class Json extends AsyncTask<String, Void, JSONObject> {

    ProgressDialog progressDialog;
    Activity mActivity;
    Intent mIntent;

    public Json (Activity activity, Intent intent)
    {
        super();
        mActivity = activity;
        mIntent = intent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(mActivity, "Wait", "Downloading...");
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        progressDialog.dismiss();
        if (result != null) {
            mIntent.putExtra("EXTRA_SUBJECT", result.toString());
            mActivity.startActivity(mIntent);
        }
        super.onPostExecute(result);
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        return getJson(urls[0]);
    }

    public static JSONObject getJson(String url){

        InputStream is = null;
        String result = "";
        JSONObject jsonObject = null;
        Log.d("mylog", "http_url=" + url);
        // HTTP
        try {
            HttpClient httpclient = new DefaultHttpClient(); // for port 80 requests!
            HttpGet httpget = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        } catch(Exception e) {
            Log.d("mylog", "Error: " + e.toString());
            return null;
        }
        Log.d("mylog", "// Read response to string");
        // Read response to string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"utf-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            result = sb.toString();
            Log.d("mylog", "result="+result);
            try
            {
                jsonObject = new JSONObject(result);
                jsonObject = jsonObject.getJSONObject("data");
                Log.d("mylog", "jsonObject="+jsonObject);
                return jsonObject;
            }
            catch (JSONException e) {
                Log.d("mylog", "Error getting JSON object");
                return null;
            }
        } catch(Exception e) {
            return null;
        }
    }
}