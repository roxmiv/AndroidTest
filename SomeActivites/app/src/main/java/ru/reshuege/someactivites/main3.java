package ru.reshuege.someactivites;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class main3 extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main3);
        setTitle(R.string.math_button_text);
        try {
            JSONArray object = (new JSONObject(getIntent().getStringExtra("EXTRA_DATA"))).getJSONArray("data");
            int len = object.length();
            String[] values = new String[len];
            for (int i = 0; i < len; i++) {
                values[i] = object.getString(i);
            }
            TextView[] viewMas = new TextView[len];
            for (int i = 0; i < len; i++) {
                viewMas[i] = new TextView(main3.this);
                viewMas[i].setText("Загрузка...");
            }
            new JsonForTask(main3.this, values, viewMas).execute("");
            setListAdapter(new TasksAdapter(this, viewMas));
        }
        catch (JSONException e) {
        }
    }

    class TasksAdapter extends ArrayAdapter<TextView> {
        private final Context context;
        private final TextView[] values;

        public TasksAdapter(Context context, TextView[] values) {
            super(context, R.layout.activity_main3, values);
            this.context = context;
            this.values = values;
        }

        class ViewHolder {
            public TextView textView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            LinearLayout rowView = (LinearLayout) convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = (LinearLayout) inflater.inflate(R.layout.activity_main3, null, true);
                rowView.addView(values[position]);
                holder = new ViewHolder();
                holder.textView = values[position];//(TextView) rowView.findViewById(R.id.task);
                rowView.setTag(holder);
            }
            else {
                holder = (ViewHolder) rowView.getTag();
            }
            return rowView;
        }
    }
}
