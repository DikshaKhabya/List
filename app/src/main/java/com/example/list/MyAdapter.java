
package com.example.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Integer> numbers;
    private int minimum, maximum;

    public MyAdapter(Context context) {
        numbers = new ArrayList<Integer>();
    }

    public void setMinimum(int minimum) {

        this.minimum = minimum;
    }

    public void setMaximum(int maximum) {

        this.maximum = maximum;
    }

    public ArrayList<Integer> getNumbers() {
        return numbers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.list_layout, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
        int text = numbers.get(i);
        myViewHolder.values.setText(Integer.toString(text));

        if (minimum == text) {
            myViewHolder.text_max_min.setText(R.string.min);
        } else if (maximum == text) {
            myViewHolder.text_max_min.setText(R.string.max);
        } else
            myViewHolder.text_max_min.setText("");

    }

    @Override
    public int getItemCount() {
        return numbers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView values;
        TextView text_max_min;

        public MyViewHolder(View v) {
            super(v);

            values = (TextView) v.findViewById(R.id.values);
            text_max_min = (TextView) v.findViewById(R.id.text_Max_Min);

        }

    }


}
