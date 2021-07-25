package com.example.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HistoryItemAdapter  extends ArrayAdapter<HistoryItem> implements View.OnClickListener{


    private ArrayList<HistoryItem> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtDate;
        TextView txtName1;
        TextView txtName2;
        TextView txtWinner;
    }

    public HistoryItemAdapter(HistoryHolder data, Context context) {
       super(context, R.layout.history_item_row,data.items);
        this.dataSet = data.items;
        this.mContext=context;
    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        HistoryItem dataModel=(HistoryItem)object;

        switch (v.getId())
        {
            case R.id.winner_id:
                Snackbar.make(v, "Release date " +dataModel.winner, Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        HistoryItem dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.history_item_row, parent, false);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.date_id);
            viewHolder.txtName1 = (TextView) convertView.findViewById(R.id.name1_id);
            viewHolder.txtName2 = (TextView) convertView.findViewById(R.id.name2_id);
            viewHolder.txtWinner = (TextView) convertView.findViewById(R.id.winner_id);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);*/
        lastPosition = position;

        viewHolder.txtDate.setText(dataModel.match.toString());
        viewHolder.txtName1.setText(dataModel.player1);
        viewHolder.txtName2.setText(dataModel.player2);
        viewHolder.txtName1.setTextColor(dataModel.winner==1? getContext().getColor(R.color.xColor): getContext().getColor(R.color.black));
        viewHolder.txtName2.setTextColor(dataModel.winner==2? getContext().getColor(R.color.xColor): getContext().getColor(R.color.black));
        // Return the completed view to render on screen
        return convertView;
    }
}
