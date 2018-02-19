package com.jets.medicalreminder.details;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jets.medicalreminder.R;
import com.jets.medicalreminder.beans.Medicine;
import com.jets.medicalreminder.database.MedicineAdapter;
import com.jets.medicalreminder.util.Constants;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MedicineDetailsFragment extends Fragment {

    private static final int REQUEST_CODE_MEDICINE_EDIT = 0;

    private ImageView imageView_medicinePhoto;
    private TextView textView_medicineName;
    private TextView textView_medicineType;
    private TextView textView_medicineDose;
    private TextView textView_startDate;
    private TextView textView_endDate;
    private TextView textView_startTime,textView_midTime,textView_endTime;
   // private TextView textView_interval;
    private TextView textView_notes;
    private Medicine medicine;

    TextView mealTime1,mealTime2,mealTime3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        Intent intent = getActivity().getIntent();
        int id = intent.getIntExtra(Constants.EXTRA_MEDICINE_ID, 0);
        if (id != 0) {
            MedicineAdapter medicineAdapter = MedicineAdapter.getInstance(getActivity());
            medicine = medicineAdapter.getMedicine(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_medicine_details, container, false);

        initViews(rootView);
        setFields();

        return rootView;
    }


    // Initialize Views
    private void initViews(View view) {
        imageView_medicinePhoto = (ImageView) view.findViewById(R.id.imageView_medicinePhoto);
        textView_medicineName = (TextView) view.findViewById(R.id.textView_medicineName);
      //  textView_medicineType = (TextView) view.findViewById(R.id.textView_medicineType);
      //  textView_medicineDose = (TextView) view.findViewById(R.id.textView_medicineDose);
        textView_startDate = (TextView) view.findViewById(R.id.textView_startDate);
        textView_endDate = (TextView) view.findViewById(R.id.textView_endDate);
        textView_startTime = (TextView) view.findViewById(R.id.textView_startTime);
      //  textView_startTime = (TextView) view.findViewById(R.id.textView_startTime);
        textView_midTime=(TextView)view.findViewById(R.id.textView_midTime);
        textView_endTime=(TextView)view.findViewById(R.id.textView_endTime) ;
       // textView_interval = (TextView) view.findViewById(R.id.textView_interval);
        textView_notes = (TextView) view.findViewById(R.id.textView_notes);
        mealTime1=(TextView)view.findViewById(R.id.mealtime1);
        mealTime2=(TextView)view.findViewById(R.id.mealtime2);
        mealTime3=(TextView)view.findViewById(R.id.mealtime3);
    }


    // Get Medicine
    public Medicine getMedicine() {
        return medicine;
    }


    // Set Fields
    private void setFields() {
        textView_medicineName.setText(medicine.getName());
       // textView_medicineType.setText(medicine.getType().toString());
      //  textView_medicineDose.setText(String.valueOf(medicine.getDose()));
        textView_notes.setText(medicine.getNotes());

        // Set Interval
      /*  long milliseconds = medicine.getInterval();
        for (Util.Interval interval : Util.Interval.values()) {
            boolean larger = milliseconds / interval.milliSeconds != 0;
            boolean divisible = milliseconds % interval.milliSeconds == 0;
            if (larger && divisible) {
                String intervalText = milliseconds / interval.milliSeconds + " " + interval.toString();
                //textView_interval.setText(intervalText);
                break;
            }
        }
*/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.US);

        // Set Start Date
        textView_startDate.setText(simpleDateFormat.format(medicine.getStartDateTime()));

        // Set End Date
        long endDateTime = medicine.getEndDateTime();
        String dateString = endDateTime == 0 ? "Not defined" : simpleDateFormat.format(endDateTime);
        textView_endDate.setText(dateString);

        // Set Start Time
        simpleDateFormat.applyPattern(Constants.TIME_FORMAT);
        textView_startTime.setText(simpleDateFormat.format(medicine.getStartTime()));
        textView_midTime.setText(simpleDateFormat.format(medicine.getMidTime()));
        textView_endTime.setText(simpleDateFormat.format(medicine.getEndTime()));
        mealTime1.setText(medicine.getMorningIntakeHabit());
        mealTime2.setText(medicine.getNoonIntakeHabit());
        mealTime3.setText(medicine.getEveningIntakeHabit());
        setImage();
    }


    // Set Image
    private void setImage() {
        Bitmap image = medicine.getImage();
        if (image == null) {
            image = BitmapFactory.decodeResource(getResources(), R.drawable.default_medicine);
        }
        imageView_medicinePhoto.setImageBitmap(image);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_MEDICINE_EDIT) {
                int id = data.getIntExtra(Constants.EXTRA_MEDICINE_ID, 0);
                if (id != 0) {
                    MedicineAdapter medicineAdapter = MedicineAdapter.getInstance(getActivity());
                    medicine = medicineAdapter.getMedicine(id);
                    setFields();
                }
            }
        }
    }
}
