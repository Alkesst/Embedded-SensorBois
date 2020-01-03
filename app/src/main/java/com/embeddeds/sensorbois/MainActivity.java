package com.embeddeds.sensorbois;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.hardware.SensorManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TextView xaxis;
    private TextView yaxis;
    private TextView zaxis;
    private Spinner spinner;

    private SensorManager sensorManager;
    private List<Sensor> sensors;
    private Sensor selectedSensor;

    private SensorListener currentListener;

    private class SensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            xaxis.setText(String.format("%s", event.values[0]));
            yaxis.setText(String.format("%s", event.values[1]));
            zaxis.setText(String.format("%s", event.values[2]));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager != null) {
            sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        } else {
            System.out.println("There's no fucking weebs allowed");
        }
        xaxis = findViewById(R.id.xaxis);
        zaxis = findViewById(R.id.yaxis);
        yaxis = findViewById(R.id.zaxis);
        spinner = findViewById(R.id.spinner);
        List<String> temp = new ArrayList<>();
        for(Sensor sens : sensors) {
            temp.add(sens.getName());
        }
        ArrayAdapter<String> adaptedSensors = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, temp);
        adaptedSensors.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptedSensors);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (currentListener != null) sensorManager.unregisterListener(currentListener);
                currentListener = new SensorListener();
                sensorManager.registerListener(
                        currentListener, sensors.get(position), SensorManager.SENSOR_DELAY_FASTEST);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    /*private void setSensor() {

        sensorManager.registerListener(
                new SensorListener(), accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }*/
}
