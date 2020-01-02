package com.tt.ruokalista;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;

import java.util.Locale;


public class LocationDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;

    private LocationAdapter locationAdapter;

    private LocationSelectionListener locationSelectionListener;

    public LocationDialog(Context context, LocationSelectionListener locationSelectionListener) {
        super(context);
        this.locationSelectionListener = locationSelectionListener;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.location_dialog);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        listView = (ListView) findViewById(R.id.locations);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        locationAdapter = new LocationAdapter(getContext());
        listView.setAdapter(locationAdapter);

        TextView filter = (TextView) findViewById(R.id.filter);
        filter.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                locationAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        });

        Button close = (Button) findViewById(R.id.cancel);
        close.setTag("cancel");
        close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ("cancel".equals(v.getTag())) {
            dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Location location = locationAdapter.getItem(position);
        locationSelectionListener.onLocationSelected(location);
        dismiss();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Location location = locationAdapter.getItem(position);
        if (location.getLatitude() == 0.0 || location.getLongitude() == 0.0) {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(String.format(Locale.ENGLISH, "geo:0,0?q=%s,%s", location.getAddress(), location.getCity())));
            intent.setPackage("com.google.android.apps.maps");
            getContext().startActivity(intent);
        } else {
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(String.format(Locale.ENGLISH,"geo:%f,%f", location.getLatitude(), location.getLongitude())));
            intent.setPackage("com.google.android.apps.maps");
            getContext().startActivity(intent);
        }
        return (true);
    }

    public interface LocationSelectionListener {

        void onLocationSelected(Location location);

    }

}
