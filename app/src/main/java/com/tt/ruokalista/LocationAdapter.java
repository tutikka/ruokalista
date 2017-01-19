package com.tt.ruokalista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends ArrayAdapter<Location> {

    private List<Location> locations = Location.getLocations();

    public LocationAdapter(Context context) {
        super(context, 0);
    }

    @Override
    public int getCount() {
        return (locations.size());
    }

    @Override
    public Location getItem(int position) {
        return (locations.get(position));
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                locations = (List<Location>) results.values;
                notifyDataSetChanged();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                List<Location> filtered = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                for (Location location : Location.getLocations()) {
                    if (location.getTitle().toLowerCase().contains(constraint) || location.getCity().toLowerCase().contains(constraint)) {
                        filtered.add(location);
                    }
                }
                filterResults.count = filtered.size();
                filterResults.values = filtered;
                return (filterResults);
            }

        };
        return (filter);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Location location = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.location_item, null);
            viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.address = (TextView) view.findViewById(R.id.address);
            viewHolder.city = (TextView) view.findViewById(R.id.city);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.title.setText(location.getTitle());
        viewHolder.address.setText(location.getAddress() == null ? "-" : location.getAddress());
        viewHolder.city.setText(location.getPostalCode() == null ? "" : (location.getPostalCode() + " ") + location.getCity());

        return (view);
    }

    private static class ViewHolder {

        TextView title;

        TextView address;

        TextView city;

    }

}
