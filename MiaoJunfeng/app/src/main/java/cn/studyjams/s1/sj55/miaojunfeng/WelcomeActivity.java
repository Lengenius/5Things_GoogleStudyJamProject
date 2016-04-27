package cn.studyjams.s1.sj55.miaojunfeng;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

public class WelcomeActivity extends AppCompatActivity {
    private EditText edWeight, edHeight, edName;
    private float weight, height;
    private String name;
    private RadioGroup salaryRadioGroup, dreamRadioGroup, relationshipRadioGroup;
    private Button submit;

    private StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        edWeight = (EditText) findViewById(R.id.user_info_weight);
        edHeight = (EditText) findViewById(R.id.user_info_height);
        edName = (EditText) findViewById(R.id.user_info_name);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

    }

    /**
     * Calculate user Body Mess Index to give an advice.
     * @param weight represents user's weight in kilogram.
     * @param height represents user's height in metric.
     * @return
     */
    private float calBMI(float weight, float height) {
        return weight / (height * height);
    }

    /**
     * Add suitable advice to the result to the BMI index.
     * @param bmi
     */

    private void dealBMI(float bmi) {
        if (bmi < 18.5) {
            stringBuffer.append(getResources().getString(R.string.answer_thin));
        } else if (bmi <= 25) {
            stringBuffer.append(getResources().getString(R.string.answer_fit));
        } else {
            stringBuffer.append(getResources().getString(R.string.answer_fat));
        }
    }

    /**
     * Add advice in the result of user's chosen.
     * deal with user event "on salary condition".
     */
    public void addSalaryListener() {
        salaryRadioGroup = (RadioGroup) findViewById(R.id.salary_radio_group);
        int selectedSalary = salaryRadioGroup.getCheckedRadioButtonId();
        if (selectedSalary == R.id.salary_enough) {
            stringBuffer.append((getResources().getString(R.string.answer_salary_fit)));
        } else if (selectedSalary == R.id.salary_less) {
            stringBuffer.append(getResources().getString(R.string.answer_salary_low));
        } else {
            stringBuffer.append(getResources().getString(R.string.answer_salary_high));
        }
    }

    /**
     * Add advice in the result of user's chosen.
     * deal with user event "on relationship condition".
     */
    public void addRelationshipListener() {
        relationshipRadioGroup = (RadioGroup) findViewById(R.id.relationship_radio_group);
        int selectedRelationship = relationshipRadioGroup.getCheckedRadioButtonId();
        if (selectedRelationship == R.id.relationship_no) {
            stringBuffer.append(getResources().getString(R.string.answer_relationship_no));
        } else {
            stringBuffer.append(getResources().getString(R.string.answer_relationship_in));
        }
    }
    /**
     * A little test for healthy knowledge.
     * Give advice on user's answer.
     */
    public void addHealthyFood() {
        CheckBox checkBoxchicken, checkBoxfish, checkBoxnuts, checkBoxcheeseburger, checkBoxmilk, checkBoxfriedchicken;
        checkBoxchicken = (CheckBox) findViewById(R.id.food_chicken);
        checkBoxfish = (CheckBox) findViewById(R.id.food_fish);
        checkBoxnuts = (CheckBox) findViewById(R.id.food_nuts);
        checkBoxmilk = (CheckBox) findViewById(R.id.food_milk);
        checkBoxfriedchicken = (CheckBox) findViewById(R.id.food_friedchicken);
        checkBoxcheeseburger = (CheckBox) findViewById(R.id.food_cheeseburger);
        Boolean notHealthy = checkBoxcheeseburger.isChecked()
                || checkBoxfriedchicken.isChecked()
                || !checkBoxchicken.isChecked()
                || !checkBoxfish.isChecked()
                || !checkBoxnuts.isChecked()
                || !checkBoxmilk.isChecked();


        if (notHealthy) {
            stringBuffer.append(getResources().getString(R.string.answer_healthy_food_wrong));
        } else {
            stringBuffer.append(getResources().getString(R.string.answer_healthy_food_right));
        }


    }

    /**
     * Deal with user's dream chosen.
     */
    public void addDream() {
        dreamRadioGroup = (RadioGroup) findViewById(R.id.dream_radio_group);
        int dreamOrNot = dreamRadioGroup.getCheckedRadioButtonId();
        if (dreamOrNot == R.id.life_goal_yes) {
            stringBuffer.append(getResources().getString(R.string.answer_life_goal_yes));
        } else {
            stringBuffer.append(getResources().getString(R.string.answer_life_goal_no));
        }

    }

    public void addBMI() {
        weight = Float.parseFloat(edWeight.getText().toString());
        height = Float.parseFloat(edHeight.getText().toString());
        dealBMI(calBMI(weight, height));
    }

    /**
     * Submit all answer and make a email for the user to remember all the advices.
     */
    public void submit() {
        name = edName.getText().toString();
        addBMI();
        addSalaryListener();
        addRelationshipListener();
        addHealthyFood();
        addDream();
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String content = stringBuffer.toString();
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mail_subject) + name);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
