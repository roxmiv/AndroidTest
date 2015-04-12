package ru.reshuege.someactivites;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class VariantActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.variant_activity);
        setTitle(R.string.math_button_text);
        try {
            JSONArray object = (new JSONObject(getIntent().getStringExtra("EXTRA_DATA"))).getJSONArray("data");
            int len = object.length();
            String[] values = new String[len];
            for (int i = 0; i < len; i++) {
                values[i] = object.getString(i);
            }
            View[] taskMas = new View[len];
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (int i = 0; i < len; i++) {
                taskMas[i] = inflater.inflate(R.layout.variant_task_activity, null);
                ((TextView)taskMas[i].findViewById(R.id.TaskItem)).setText("Загрузка...");
            }
            new JsonForTask(VariantActivity.this, "math", values, taskMas).execute("");
            setListAdapter(new TasksAdapter(this, taskMas));
        }
        catch (JSONException e) {
        }
    }

    class TasksAdapter extends ArrayAdapter<View> {
        private final Context context;
        private final View[] values;

        public TasksAdapter(Context context, View[] values) {
            super(context, R.layout.variant_activity, values);
            this.context = context;
            this.values = values;
        }

        class ViewHolder {
            public View view;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            LinearLayout rowView = (LinearLayout) convertView;
            if (rowView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                rowView = (LinearLayout) inflater.inflate(R.layout.variant_activity, null, true);
                rowView.addView(values[position]);
                holder = new ViewHolder();
                holder.view = values[position];//(TextView) rowView.findViewById(R.id.task);
                rowView.setTag(holder);
            }
            else {
                holder = (ViewHolder) rowView.getTag();
            }
            return rowView;
        }
    }
}
