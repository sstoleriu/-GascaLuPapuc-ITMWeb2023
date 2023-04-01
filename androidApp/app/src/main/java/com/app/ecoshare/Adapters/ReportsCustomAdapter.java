package com.app.ecoshare.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.app.ecoshare.Models.Report;
import com.app.ecoshare.R;
import java.util.ArrayList;



public class ReportsCustomAdapter extends ArrayAdapter<Report> {

    ArrayList<Report> reportsList;

    public ReportsCustomAdapter(Activity context, ArrayList<Report> reportsList ){
        super(context, 0,reportsList);
        this.reportsList = reportsList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.reports_list, parent, false);
        }


        return listItemView;
    }
}
