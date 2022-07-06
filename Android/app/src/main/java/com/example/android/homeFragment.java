package com.example.android;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.Database.CalendarRepository;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {

    CalendarView calendarView;
    EditText breakfast;
    EditText lunch;
    EditText dinner;
    CalendarRepository calendarRepository;
    MaterialButton save;
    String currentDate = "not set";
    int saved;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        calendarRepository = new CalendarRepository(this.getActivity().getApplication());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        breakfast = view.findViewById(R.id.breakfast);
        lunch = view.findViewById(R.id.lunch);
        dinner = view.findViewById(R.id.dinner);
        calendarView =(CalendarView) view.findViewById(R.id.calendar);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        currentDate = formatter.format(date);
        LiveData<Calendar> event = calendarRepository.getCalendarEvent(((GlobalClass)getActivity().getApplication()).getUserId(), currentDate);
        event.observe(getViewLifecycleOwner(), new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                if(calendar != null){
                    breakfast.setText(calendar.breakfast);
                    lunch.setText(calendar.lunch);
                    dinner.setText(calendar.dinner);
                } else {
                    breakfast.setText("Breakfast");
                    lunch.setText("Lunch");
                    dinner.setText("Dinner");
                }
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                month=month+1;
                String monthModified = "" + month;
                String dayOfMonthModified = ""+dayOfMonth;
                if(month<10) {
                    monthModified = "0" + month;
                }
                if(dayOfMonth<10){
                    dayOfMonthModified = "0"+dayOfMonth;
                }
                String date =  year + "/" + monthModified + "/" + dayOfMonthModified;
                currentDate = date;
                LiveData<Calendar> event = calendarRepository.getCalendarEvent(((GlobalClass)getActivity().getApplication()).getUserId(), date);
                event.observe(getViewLifecycleOwner(), new Observer<Calendar>() {
                    @Override
                    public void onChanged(Calendar calendar) {
                        if(calendar != null){
                            breakfast.setText(calendar.breakfast);
                            lunch.setText(calendar.lunch);
                            dinner.setText(calendar.dinner);
                        } else {
                            breakfast.setText("Breakfast");
                            lunch.setText("Lunch");
                            dinner.setText("Dinner");
                        }
                    }
                });
            }
        });

        save = view.findViewById(R.id.button_save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saved = 0;
                LiveData<Calendar> event = calendarRepository.getCalendarEvent(((GlobalClass)getActivity().getApplication()).getUserId(), currentDate);
                event.observe(getViewLifecycleOwner(), new Observer<Calendar>() {
                    @Override
                    public void onChanged(Calendar calendar) {
                        if(saved == 0) {
                            if (calendar == null) {
                                saved = 1;
                                Calendar calendar1 = new Calendar();
                                calendar1.breakfast = breakfast.getText().toString();
                                calendar1.lunch = lunch.getText().toString();
                                calendar1.dinner = dinner.getText().toString();
                                calendar1.date = currentDate;
                                calendar1.user = ((GlobalClass) getActivity().getApplication()).getUserId();
                                calendarRepository.insertCalendarEvent(calendar1);
                                Toast.makeText(getActivity(), "You have insert data correctly", Toast.LENGTH_SHORT).show();
                            } else {
                                saved = 1;
                                calendarRepository.updateCalendarEvent(currentDate, breakfast.getText().toString(), lunch.getText().toString(), dinner.getText().toString(), ((GlobalClass) getActivity().getApplication()).getUserId());
                                Toast.makeText(getActivity(), "You have insert data correctly", Toast.LENGTH_SHORT).show();
                            }
                            breakfast.setText(breakfast.getText().toString());
                            lunch.setText(lunch.getText().toString());
                            dinner.setText(dinner.getText().toString());
                        }
                    }
                });
            }
        });
        return view;
    }
}