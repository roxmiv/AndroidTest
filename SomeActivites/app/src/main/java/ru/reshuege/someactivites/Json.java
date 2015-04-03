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
import android.widget.Toast;

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

class Json extends AsyncTask<String, Integer, JSONObject> {

    Activity m_activity;
    String m_prefix;

    public Json (Activity activity, String prefix)
    {
        super();
        m_activity = activity;
        m_prefix = prefix;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
        return getJson("http://" + m_prefix + '.' + urls[0]);
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
    ProgressDialog m_progressDialog;

    public JsonWithNewIntent (Activity activity, String prefix, Intent intent)
    {
        super(activity, prefix);
        m_intent = intent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        m_progressDialog = ProgressDialog.show(m_activity, "Подождите", "Загрузка...");
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        m_progressDialog.dismiss();
        if (result != null) {
            m_intent.putExtra("EXTRA_DATA", result.toString());
            m_intent.putExtra("PREFIX", m_prefix);
            m_activity.startActivity(m_intent);
        }
        else
        {
            Toast.makeText(m_activity, "Ошибка. Проверьте соединение.", Toast.LENGTH_LONG).show();
        }
        super.onPostExecute(result);
    }
}

class JsonForTask extends Json {
    String[] m_taskNumbers;
    TextView[] m_webView;
    Spanned[] m_mas = new Spanned[1];

    public JsonForTask (Activity activity, String prefix, String[] taskNumbers, TextView[] webView)
    {
        super(activity, prefix);
        m_taskNumbers = taskNumbers;
        m_webView = webView;
    }

    @Override
    protected JSONObject doInBackground(String... urls) {
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
        int taskNumber = m_taskNumbers.length;
        for (int i = 0; i < taskNumber; i++) {
            JSONObject result = getJson("http://math.reshuege.ru/api?type=get_task&data=" + m_taskNumbers[i]);
            try {
                if (result != null)
                    m_mas[0] = Html.fromHtml("<html><body>" + result.getJSONObject("data").getString("body") + "</body></html>", imgGetter, null);
                else
                    m_mas[0] = Html.fromHtml("<html><body>Ошибка</body></html>");
            } catch (JSONException e) {
                m_mas[0] = Html.fromHtml("<html><body>Ошибка</body></html>");
            }
            publishProgress(i);
        }
        return new JSONObject();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Log.d("mylog", "Put text");
        m_webView[values[0]].setText(m_mas[0]);
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);
    }
}