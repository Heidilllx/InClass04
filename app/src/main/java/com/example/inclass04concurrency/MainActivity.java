package com.example.inclass04concurrency;

import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * main activity of the application
 */
public class MainActivity extends AppCompatActivity {
    /**
     * number of numbers need to be created
     */
    int complexity;
    /**
     *seekbar_1
     */
    SeekBar seekBar;
    /**
     *button_gene
     */
    Button buttonGene;
    /**
     *textView1
     */
    TextView times;
    /**
     *editText_max
     */
    TextView editTextMax;
    /**
     *editText_ave
     */
    TextView editTextAve;
    /**
     *editText_min
     */
    TextView editTextMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        times = findViewById(R.id.textView1);
        seekBar = findViewById(R.id.seekBar_1);
        buttonGene = findViewById(R.id.button_gene);
        editTextAve = findViewById(R.id.editText_ave);
        editTextMax = findViewById(R.id.editText_max);
        editTextMin = findViewById(R.id.editText_min);

        findViewById(R.id.button_gene).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute(1000);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                complexity = i;
                System.out.println(complexity);
                times.setText(String.valueOf(complexity));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        buttonGene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute(complexity);
            }
        });

    }

    /**
     * async task for calculating work
     */
    private class MyTask extends AsyncTask<Integer, Integer, ArrayList<Double>> {

        /**
         * progress bar for showing computing progress
         */
        ProgressBar pb;

        protected void onPreExecute() {
            pb = findViewById(R.id.progressBar);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<Double> arrayList) {
            super.onPostExecute(arrayList);

            pb.setVisibility(View.INVISIBLE);
            editTextMin.setText(arrayList.get(0).toString());
            editTextAve.setText(arrayList.get(2).toString());
            editTextMax.setText(arrayList.get(1).toString());
        }

        @Override
        protected ArrayList<Double> doInBackground(Integer... integers) {
            complexity = integers[0];

            ArrayList<Double> result = HeavyWork.getArrayNumbers(complexity);

            // calculate the average
            double min = result.get(0);
            double max = result.get(0);

            double sum = 0.0;
            for (int i = 0; i < result.size(); i++) {
                sum += result.get(i);
                min = Math.min(min, result.get(i));
                max = Math.max(max, result.get(i));
            }
            double average = sum / result.size();

            ArrayList<Double> ans = new ArrayList<>();
            ans.add(min);
            ans.add(max);
            ans.add(average);

            return ans;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


}
