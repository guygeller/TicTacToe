package com.example.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class HistoryItemAdapter  extends ArrayAdapter<HistoryItem>{

    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtDate;
        TextView txtName1;
        TextView txtName2;
    }

    public HistoryItemAdapter(HistoryHolder data, Context context) {
       super(context, R.layout.history_item_row,data.items);
        this.mContext=context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        HistoryItem dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.history_item_row, parent, false);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.date_id);
            viewHolder.txtName1 = (TextView) convertView.findViewById(R.id.name1_id);
            viewHolder.txtName2 = (TextView) convertView.findViewById(R.id.name2_id);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String text = sdf.format(dataModel.match);
        viewHolder.txtDate.setText(text);
        viewHolder.txtName1.setText(dataModel.player1);
        viewHolder.txtName2.setText(dataModel.player2);
        viewHolder.txtName1.setTextColor(dataModel.winner==1? getContext().getColor(R.color.winner): getContext().getColor(R.color.black));
        viewHolder.txtName2.setTextColor(dataModel.winner==2? getContext().getColor(R.color.winner): getContext().getColor(R.color.black));
        // Return the completed view to render on screen
        return convertView;
    }
}
