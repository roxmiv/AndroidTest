package ru.reshuege.someactivites;

import android.app.ListActivity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Spanned;
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
            setListAdapter(new TasksAdapter(this, values));
        }
        catch (JSONException e) {
        }
    }

    class TasksAdapter extends ArrayAdapter<String> {
        private final Context context;
        private final String[] values;

        public TasksAdapter(Context context, String[] values) {
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

            View rowView = convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = inflater.inflate(R.layout.activity_main3, null, true);
                holder = new ViewHolder();
                holder.textView = (TextView) rowView.findViewById(R.id.task);
                rowView.setTag(holder);
            }
            else {
                holder = (ViewHolder) rowView.getTag();
            }

            new JsonForTask(main3.this, holder.textView).execute("http://math.reshuege.ru/api?type=get_task&data=" + values[position]);
            return rowView;
        }
    }
}
