package ru.reshuege.someactivites;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;


public class SubjectSelectActivity extends ActionBarActivity {

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_select);
        addListenerOnButton();
    }

    protected void goToMenu(String prefix)
    {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("PREFIX", prefix);
        SubjectSelectActivity.this.startActivity(intent);
    }

    public void addListenerOnButton() {
        button = (Button) findViewById(R.id.buttonMath);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.math_prefix));
                /*
                Intent intent = new Intent(context, ThemesActivity.class);
                new JsonWithNewIntent(SubjectSelectActivity.this, getString(R.string.math_prefix), intent).execute("reshuege.ru/api?type=get_themes");
                */
            }
        });
        button = (Button) findViewById(R.id.buttonMathb);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.mathb_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonInf);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.inf_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonRus);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.rus_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonEn);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.en_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonDe);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.de_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonFr);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.fr_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonSp);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.sp_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonPhys);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.phys_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonChem);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.chem_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonBio);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.bio_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonGeo);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.geo_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonSoc);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.soc_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonLit);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.lit_prefix));
            }
        });
        button = (Button) findViewById(R.id.buttonHist);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                goToMenu(getString(R.string.hist_prefix));
            }
        });
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
