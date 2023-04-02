package com.app.ecoshare.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.ecoshare.Models.Report;
import com.app.ecoshare.Models.ReportResponse;
import com.app.ecoshare.R;
import java.util.ArrayList;
import java.util.List;


public class ReportsCustomAdapter extends ArrayAdapter<ReportResponse> {

    List<ReportResponse> reportsList;

    public ReportsCustomAdapter(Activity context, List<ReportResponse> reportsList ){
        super(context, 0,reportsList);
        this.reportsList = reportsList;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ReportResponse reportResponse = getItem(position);
        View listItemView = convertView;
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.reports_list, parent, false);
        }

        TextView description = listItemView.findViewById(R.id.description);
        TextView state = listItemView.findViewById(R.id.state);

        description.setText("Description: " + reportResponse.getDescription());
        state.setText("Resolved Status: " + reportResponse.getResolve());

        return listItemView;
    }
}
