package com.example.mukhe.dynamicautocomplete;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mukhe on 06-Feb-18.
 */

public class CustomAdapter extends ArrayAdapter implements Filterable {
    public List<Train> trainName;
    Context context;
    public CustomAdapter(Context context,int resource){
        super(context,resource);
        this.context=context;
        trainName=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return trainName.size();
    }

    @Nullable
    @Override
    public Train getItem(int position) {
        return trainName.get(position);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults=new FilterResults();
                if(charSequence!=null){
                    String partialName=charSequence.toString();
                    String url="https://api.railwayapi.com/v2/suggest-train/train/"+partialName+"/apikey/xnlo6zq3yj/";
                    HTTPConnector httpConnector=new HTTPConnector(url,context);
                    JSONObject response=httpConnector.getJsonResponse();
                    try {
                        JSONArray trains=response.getJSONArray("trains");
                        for(int i=0;i<trains.length();i++){
                            trainName.add(new Train(trains.getJSONObject(i).getString("number")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    filterResults.values=trainName;
                    filterResults.count=trainName.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults!=null && filterResults.count>0){
                    notifyDataSetChanged();
                }
                else notifyDataSetInvalidated();
            }
        };
        return filter;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(getContext());
        if(convertView==null){
            convertView=inflater.inflate(R.layout.cutom_layout,parent,false);
            Train train=trainName.get(position);
            TextView textView=convertView.findViewById(R.id.trainName);
            textView.setText(train.getTrainNumber());
        }
        return convertView;
    }
}
