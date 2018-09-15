package com.example.albheryyyyuhckfjhdgf.finalproject.Adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.albheryyyyuhckfjhdgf.finalproject.Models.Reveiws;
import com.example.albheryyyyuhckfjhdgf.finalproject.R;

import java.util.List;

/**
 * Created by ALBHERYYYYUHCKFJHDGF on 21/09/2016.
 */
public class ReviewsAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final Reveiws reveiw = new Reveiws();
    private List<Reveiws> reveiws;

    public ReviewsAdapter(Context context, List<Reveiws> reveiws) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.reveiws = reveiws;
    }

    public Context getContext() {
        return context;
    }

    public void add(Reveiws object) {
        synchronized (reveiw) {
            reveiws.add(object);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        synchronized (reveiw) {
            reveiws.clear();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return reveiws.size();
    }

    @Override
    public Reveiws getItem(int position) {
        return reveiws.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        MyHolder holder;

        if (v == null) {
            v = inflater.inflate(R.layout.reviews_items, parent, false);
            holder = new MyHolder(v);
            v.setTag(holder);
        }
        String auther = reveiws.get(position).getAuther();
        String content = reveiws.get(position).getContent();
        holder = (MyHolder) v.getTag();

        holder.authorView.setText(auther);
        holder.contentView.setText(Html.fromHtml(content));

        return v;
    }

    public static class MyHolder {
        public final TextView authorView;
        public final TextView contentView;

        public MyHolder(View view) {
            authorView = (TextView) view.findViewById(R.id.auher_textview);
            contentView = (TextView) view.findViewById(R.id.content_textview);
        }
    }


}