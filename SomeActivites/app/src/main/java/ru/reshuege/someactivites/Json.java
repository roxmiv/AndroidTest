package ru.reshuege.someactivites;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

class Json extends AsyncTask<String, Void, JSONObject> {

    ProgressDialog m_progressDialog;
    Activity m_activity;

    public Json (Activity activity)
    {
        super();
        m_activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //m_progressDialog = ProgressDialog.show(m_activity, "Подождите", "Загрузка...");
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        //m_progressDialog.dismiss();
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

class JsonWithNewIntent extends Json {
    Intent m_intent;

    public JsonWithNewIntent (Activity activity, Intent intent)
    {
        super(activity);
        m_intent = intent;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        if (result != null) {
            m_intent.putExtra("EXTRA_DATA", result.toString());
            m_activity.startActivity(m_intent);
        }
        super.onPostExecute(result);
    }
}

class JsonForTask extends Json {
    TextView m_webView;
    Spanned[] m_mas = new Spanned[1];

    public JsonForTask (Activity activity, TextView webView)
    {
        super(activity);
        m_webView = webView;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        JSONObject result = getJson(urls[0]);
        Html.ImageGetter imgGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                Drawable drawable = null ;
                Log.d( "Image Path" , source);
                URL url;
                try {
                    url = new URL(source);
                    drawable = Drawable.createFromStream(url.openStream(), "" );
                }
                catch (Exception e) {
                    return null ;
                }
                drawable.setBounds( 0 , 0 , drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        try {
            m_mas[0] = Html.fromHtml("<html><body>" + result.getJSONObject("data").getString("body") + "</body></html>", imgGetter, null);
        }
        catch (JSONException e) {
            return null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result) {

        if (result != null) {
            m_webView.setText(m_mas[0]);
        }
        else {
            m_webView.setText("Ошибка");
        }
        super.onPostExecute(result);
    }
}