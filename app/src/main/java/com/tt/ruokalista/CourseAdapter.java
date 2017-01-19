package com.tt.ruokalista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.tt.ruokalista.dto.CourseItem;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends ArrayAdapter<CourseItem> {

    private List<CourseItem> courses = new ArrayList<>();

    public CourseAdapter(Context context) {
        super(context, 0);
    }

    public void update(List<CourseItem> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (courses.size());
    }

    @Override
    public CourseItem getItem(int position) {
        return (courses.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseItem courseItem = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.course_item, null);
            viewHolder = new ViewHolder();
            viewHolder.category = (TextView) view.findViewById(R.id.category);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.properties = (TextView) view.findViewById(R.id.properties);
            viewHolder.price = (TextView) view.findViewById(R.id.price);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.category.setText(courseItem.getCategory() == null ? "-" : courseItem.getCategory());
        viewHolder.title.setText(courseItem.getTitle_fi() == null ? "-" : courseItem.getTitle_fi());
        viewHolder.properties.setText(courseItem.getProperties() == null ? "-" : courseItem.getProperties());
        viewHolder.price.setText(courseItem.getPrice() == null ? "-" : courseItem.getPrice() + " EUR");
        return (view);
    }

    private static class ViewHolder {

        TextView category;

        TextView title;

        TextView properties;

        TextView price;

    }

}
