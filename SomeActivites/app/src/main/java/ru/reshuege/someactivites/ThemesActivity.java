package ru.reshuege.someactivites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ThemesActivity extends ActionBarActivity {

    String m_prefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_prefix = getIntent().getStringExtra("PREFIX");
        Context context = this;
        setTitle(context.getResources().getIdentifier(m_prefix + "_button_text", "string", context.getPackageName()));
        setContentView(R.layout.activity_themes);
        try {
            final JSONArray themes = (new JSONObject(getIntent().getStringExtra("EXTRA_DATA"))).getJSONArray("data");

            final List<String> listDataHeader = new ArrayList<String>();
            final HashMap<String, List<List<String>>> listDataChild = new HashMap<String, List<List<String>>>();
            if (themes != null) {
                int len = themes.length();
                for (int i=0;i<len;i++){
                    JSONObject currentTheme = themes.getJSONObject(i);
                    String subject = currentTheme.getString("name");
                    listDataHeader.add(subject);
                    JSONArray childArray = currentTheme.getJSONArray("childs");
                    if (childArray != null){
                        int childLen = childArray.length();
                        ArrayList<List<String>> childrens = new ArrayList<List<String>>();
                        for (int j=0; j< childLen; j++) {
                            List<String> element = new ArrayList<String>();
                            element.add(childArray.getJSONObject(j).getString("name"));
                            element.add(childArray.getJSONObject(j).getString("id"));
                            childrens.add(element);
                        }
                        listDataChild.put(listDataHeader.get(i), childrens);
                    }
                }
            }
            ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
            ExpandableListView themeList = (ExpandableListView)findViewById(R.id.lvExp);
            themeList.setAdapter(listAdapter);

            themeList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v,
                                            int groupPosition, int childPosition, long id) {
                    try {
                        String themeId = listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition).get(1);

                        Intent intent = new Intent(getApplicationContext(), VariantActivity.class);
                        new JsonWithNewIntent(ThemesActivity.this, m_prefix, intent).execute("reshuege.ru/api?type=get_theme_tasks&data=" + themeId);
                    }
                    catch (Exception e) {
                        Toast.makeText(
                                getApplicationContext(),
                                "Ошибка", Toast.LENGTH_SHORT)
                                .show();
                    }
                    return false;
                }
            });
        }
        catch (JSONException e) {
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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
