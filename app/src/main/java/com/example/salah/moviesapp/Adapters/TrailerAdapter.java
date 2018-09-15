package com.example.albheryyyyuhckfjhdgf.finalproject.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Trailer;
import com.example.albheryyyyuhckfjhdgf.finalproject.R;

import java.util.List;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 19/09/2016.
 */
public class TrailerAdapter extends BaseAdapter {
    private final List<Trailer> trailers;
    private final LayoutInflater mInflater;
    private final Context context;
    Trailer trailer = new Trailer();

    public TrailerAdapter(Context context,List<Trailer> trailers) {
        this.context=context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.trailers = trailers;

    }

    public Context getContext() {
        return context;
    }

    public void add(Trailer object) {
        synchronized (trailer) {
            trailers.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (trailer) {
            trailers.clear();
        }
        notifyDataSetChanged();
    }





    @Override
    public int getCount() {
        return trailers.size();
    }

    @Override
    public Object getItem(int position) {
        return trailers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder;
        View v = convertView;

        if (v == null) {
            v = mInflater.inflate(R.layout.trailer_items,parent, false);
            holder = new MyHolder(v);
            v.setTag(holder);
        }
        String name=trailers.get(position).getName();
        holder = (MyHolder) v.getTag();
        holder.textView.setText(name);

        return v;

    }

    private static class MyHolder {
        TextView textView;
        ImageView imageView;
        public MyHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.trailer_imageview);
            textView = (TextView) view.findViewById(R.id.trailer_textview);
        }
    }



}

