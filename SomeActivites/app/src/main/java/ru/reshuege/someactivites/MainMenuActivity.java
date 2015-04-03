package ru.reshuege.someactivites;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;


public class MainMenuActivity extends ActionBarActivity {

    String m_prefix;
    Button m_taskButton;
    Button m_variantButton;
    Button m_subjectButton;
    boolean m_taskHidden;
    boolean m_varianHidden;
    ObjectAnimator m_taskAnimator;
    ObjectAnimator m_variantAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_prefix = getIntent().getStringExtra("PREFIX");
        Context context = this;
        setTitle(context.getResources().getIdentifier(m_prefix + "_button_text", "string", context.getPackageName()));
        setContentView(R.layout.activity_main_menu);
        m_varianHidden = false;
        m_taskHidden = false;

        m_subjectButton = (Button) findViewById(R.id.menu_themes_button);
        m_taskButton = (Button) findViewById(R.id.menu_task_button);
        m_variantButton = (Button) findViewById(R.id.menu_variant_button);

        m_taskAnimator = ObjectAnimator.ofInt(new ButtonAnimatorHelper(m_taskButton), "marginLeft", 0, 600);
        m_taskAnimator.setDuration(500);
        //horizontalAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //horizontalAnimator.setRepeatMode(ValueAnimator.REVERSE);
        m_taskAnimator.setInterpolator(new LinearInterpolator());
        m_taskAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!m_taskHidden)
                {
                    m_taskButton.setText(R.string.menu_task_button);
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });


        m_variantAnimator = ObjectAnimator.ofInt(new ButtonAnimatorHelper(m_variantButton), "marginLeft", 0, 600);
        m_variantAnimator.setDuration(500);
        //horizontalAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //horizontalAnimator.setRepeatMode(ValueAnimator.REVERSE);
        m_variantAnimator.setInterpolator(new LinearInterpolator());
        m_variantAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}
            @Override
            public void onAnimationEnd(Animator animation) {
                if (!m_varianHidden)
                {
                    m_variantButton.setText(R.string.menu_variant_button);
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {}
            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;
        m_subjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, ThemesActivity.class);
                new JsonWithNewIntent(MainMenuActivity.this, m_prefix, intent).execute("reshuege.ru/api?type=get_themes");
            }
        });

        m_taskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                m_taskButton.setText("");

                m_taskAnimator.start();
                m_taskHidden = true;
                if (m_varianHidden)
                {
                    m_varianHidden = false;
                    m_variantAnimator.reverse();
                }
            }
        });

        m_variantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                m_variantButton.setText("");

                m_variantAnimator.start();
                m_varianHidden = true;
                if (m_taskHidden)
                {
                    m_taskHidden = false;
                    m_taskAnimator.reverse();
                }
            }
        });
    }

    private static class ButtonAnimatorHelper {

        final Button m_button;
        public ButtonAnimatorHelper(final Button button) {
            m_button = button;
        }

        public void setMarginLeft(final int margin) {
            final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) m_button.getLayoutParams();

            params.rightMargin = margin;

            m_button.setLayoutParams(params);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
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
