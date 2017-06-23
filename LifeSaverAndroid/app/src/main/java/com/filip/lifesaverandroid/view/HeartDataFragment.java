package com.filip.lifesaverandroid.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.filip.lifesaverandroid.R;
import com.filip.lifesaverandroid.model.PatientData;
import com.filip.lifesaverandroid.service.BackendURLs;
import com.filip.lifesaverandroid.service.RestTemplateConfig;

import org.springframework.web.client.RestTemplate;


public class HeartDataFragment extends Fragment {


    public static final String TAG = HeartDataFragment.class.getSimpleName();

    public static HeartDataFragment newInstance() {
        return new HeartDataFragment();
    }


    private TextView bloodPressure;
    private TextView cholesterol;
    private TextView heartRate;
    private TextView bloodSugar;

    /**
     * The data displayed in this fragment
     */
    private PatientData patientData;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_heart_data, container, false);
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bloodPressure = (TextView) view.findViewById(R.id.blood_pressure_value);
        cholesterol = (TextView) view.findViewById(R.id.cholesterol_value);
        heartRate = (TextView) view.findViewById(R.id.heart_rate_max_value);
        bloodSugar = (TextView) view.findViewById(R.id.blood_sugar_value);


        //requesting an update
        PatientDataUpdater patientDataUpdater = new PatientDataUpdater();
        patientDataUpdater.execute((Void) null);

        Toast.makeText(getActivity(), R.string.patient_data_update_in_progress, Toast.LENGTH_SHORT).show();


    }


    class PatientDataUpdater extends AsyncTask<Void, Void, PatientData> {

        @Override
        protected PatientData doInBackground(Void... params) {

            final String url = BackendURLs.PATIENT + "/" + PatientData.ID;
            RestTemplate restTemplate = RestTemplateConfig.buildRestTemplate();

            try {
                PatientData patientData = restTemplate.getForObject(url, PatientData.class);
                return patientData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(PatientData patientData) {
            super.onPostExecute(patientData);

            if (patientData != null) {
                //the views could be null if the underlying fragment was recycled
                if (bloodPressure != null) {
                    bloodPressure.setText(Integer.toString(patientData.getLastMeasuredTestBPS()));
                }
                if (cholesterol != null) {
                    cholesterol.setText(Integer.toString(patientData.getLastMeasuredCholesterol()));
                }
                if (heartRate != null) {
                    heartRate.setText(Integer.toString(patientData.getLastMeasuredThalach()));
                }
                if (bloodSugar != null) {
                    bloodSugar.setText(Boolean.toString(patientData.isLastMeasuredMuchFBS()));
                }
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Update successfully!", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (getActivity() != null) {
                    Toast.makeText(getActivity(), "Some troubles during connecting to backend!", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }


}
