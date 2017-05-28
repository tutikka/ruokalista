package com.tt.ruokalista;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.*;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tt.ruokalista.dto.CourseItem;
import com.tt.ruokalista.dto.Courses;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener, LocationDialog.LocationSelectionListener {

    private ListView listView;

    private CourseAdapter courseAdapter;

    private Location location;

    private SharedPreferences sharedPreferences;

    private Calendar calendar = Calendar.getInstance();

    private Handler handler = new Handler();

    private TextToSpeech textToSpeech;

    private String language = "fi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Location.load(this);

        sharedPreferences = getSharedPreferences("Ruokalista", 0);
        String locationCode = sharedPreferences.getString("location_code", null);
        if (locationCode == null) {
            location = Location.getLocations().get(0);
        } else {
            location = Location.getLocation(locationCode);
            if (location == null) {
                location = Location.getLocations().get(0);
            }
        }

        getActionBar().setSubtitle(location.toString());

        listView = (ListView) findViewById(R.id.courses);
        listView.setEmptyView(findViewById(R.id.empty));
        courseAdapter = new CourseAdapter(this);
        listView.setAdapter(courseAdapter);

        ImageButton previous = (ImageButton) findViewById(R.id.previous);
        previous.setTag("previous");
        previous.setOnClickListener(this);

        ImageButton next = (ImageButton) findViewById(R.id.next);
        next.setTag("next");
        next.setOnClickListener(this);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(new Locale("fi", "FI"));
                    if (result == TextToSpeech.SUCCESS) {
                        language = "fi";
                    } else {
                        textToSpeech.setLanguage(Locale.UK);
                        language = "en";
                    }
                } else {
                    Toast.makeText(MainActivity.this, "TTS-palvelu (tekstinluku) ei käytössä (" + i + ")", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        new UpdateAsyncTask().execute(location);
    }

    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return (true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.speak : {
                if (textToSpeech != null) {
                    Toast.makeText(this, "Toistetaan ruokalista (" + language + ")", Toast.LENGTH_SHORT).show();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textToSpeech.speak(location.getTitle() + ", " + location.getCity() + ", " + new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime()), TextToSpeech.QUEUE_ADD, null);
                            for (int i = 0; i < courseAdapter.getCount(); i++) {
                                CourseItem courseItem = courseAdapter.getItem(i);
                                if ("fi".equals(language) && courseItem.getTitle_fi() != null) {
                                    textToSpeech.speak(courseItem.getTitle_fi(), TextToSpeech.QUEUE_ADD, null);
                                }
                                if ("en".equals(language) && courseItem.getTitle_en() != null) {
                                    textToSpeech.speak(courseItem.getTitle_en(), TextToSpeech.QUEUE_ADD, null);
                                }
                            }
                        }
                    });
                } else {
                    // TODO
                }
                return (true);
            }
            case R.id.select_location : {
                Dialog dialog = new LocationDialog(this, this);
                dialog.show();
                return (true);
            }
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onClick(View v) {
        if ("previous".equals(v.getTag())) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            new UpdateAsyncTask().execute(location);
        }
        if ("next".equals(v.getTag())) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            new UpdateAsyncTask().execute(location);
        }
    }

    @Override
    public void onLocationSelected(Location location) {
        this.location = location;
        getActionBar().setSubtitle(this.location.toString());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("location_code", location.getCode());
        editor.commit();
        new UpdateAsyncTask().execute(location);
    }

    private class UpdateAsyncTask extends AsyncTask<Location, Void, List<CourseItem>> {

        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Päivitetään ruokalistaa...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected void onPostExecute(List<CourseItem> courses) {
            if (dialog != null) {
                dialog.dismiss();
            }
            courseAdapter.update(courses);
            TextView date = (TextView) findViewById(R.id.date);
            date.setText(new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime()));
        }

        @Override
        protected List<CourseItem> doInBackground(Location... locations) {
            List<CourseItem> result = new ArrayList<>();
            try {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                StringBuilder sb = new StringBuilder();
                sb.append("http://www.sodexo.fi/ruokalistat/output/daily_json/");
                sb.append(locations[0].getCode());
                sb.append("/");
                sb.append(year);
                sb.append("/");
                sb.append(month < 10 ? "0" + month : month);
                sb.append("/");
                sb.append(day < 10 ? "0" + day : day);
                sb.append("/fi");
                URL url = new URL(sb.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(15 * 1000);
                conn.setReadTimeout(15 * 1000);
                conn.setRequestMethod("GET");
                int status = conn.getResponseCode();
                if (status == 200) {
                    ObjectMapper mapper = new ObjectMapper();
                    Courses courses = mapper.readValue(conn.getInputStream(), Courses.class);
                    result = courses.getCourses();
                }
            } catch (Exception e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
            return (result);
        }

    }

}
