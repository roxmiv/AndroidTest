package ru.reshuege.someactivites;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class main2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.math_button_text);
        setContentView(R.layout.activity_main2);
        try {
            final JSONObject object = (new JSONObject(getIntent().getStringExtra("EXTRA_DATA"))).getJSONObject("data");

            JSONArray jsonArray = object.names();
            final List<String> listDataHeader = new ArrayList<String>();
            final HashMap<String, List<String>> listDataChild = new HashMap<String, List<String>>();
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    String subject = jsonArray.get(i).toString();
                    listDataHeader.add(subject);
                    JSONArray childArray = object.getJSONObject(subject).names();
                    if (childArray != null){
                        int childLen = childArray.length();
                        ArrayList<String> childrens = new ArrayList<String>();
                        for (int j=0; j< childLen; j++) {
                            childrens.add(childArray.get(j).toString());
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
                        String themeId = object.getJSONObject(listDataHeader.get(groupPosition))
                                .getString(listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition));

                        Intent intent = new Intent(getApplicationContext(), main3.class);
                        new JsonWithNewIntent(main2.this, intent).execute("http://math.reshuege.ru/api?type=get_theme_tasks&data=" + themeId);
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
