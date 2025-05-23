package com.example.sensorapp.ui.humidity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.example.sensorapp.R;

import java.util.ArrayList;
import java.util.Date;

public class HumidityFragment extends Fragment implements SensorEventListener {
    private LineChart chart;
    private SensorManager mSensorManager;
    private Sensor mHumidSensor;
    static ArrayList<Entry> entries = new ArrayList<>();

    public HumidityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
        mHumidSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        if(mHumidSensor == null){
            Toast.makeText(getContext(), R.string.message_neg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_humidity, container, false);
        chart = (LineChart) root.findViewById(R.id.chart);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



    private void addEntry(SensorEvent event) {
        float humidityValue = event.values[0];

        // Ajout de la nouvelle entrée à la liste
        entries.add(new Entry(entries.size(), humidityValue));

        // Création du dataset à partir des entrées
        LineDataSet dataSet = new LineDataSet(entries, "Humidity - Time series");
        LineData data = new LineData(dataSet);

        // Mise à jour du graphique
        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mHumidSensor, SensorManager.SENSOR_DELAY_NORMAL);
        entries.clear();
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        entries.clear();
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void onSensorChanged(SensorEvent event) {
        addEntry(event);
    }
}
