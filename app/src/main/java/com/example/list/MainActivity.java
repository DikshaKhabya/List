package com.example.list;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.list.R.id;
import static com.example.list.R.id.sort;
import static com.example.list.R.id.filter;
import static com.example.list.R.layout;


public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Menu menu;
    private ArrayList<Integer> numberList;
    private RecyclerView recyclerView;
    private EditText input_x, input_y;
    private boolean descSort;
    private boolean filterNumber;
    private MyAdapter myAdapter;
    private TextView noRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        toolbar = (Toolbar) findViewById(id.toolBar);
        noRecords = findViewById(id.noRecords);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(id.recyclerView);
        initialize();

    }

    private void initialize() {

        numberList = new ArrayList();
        int[] array = getResources().getIntArray(R.array.numbers);
        for (int number : array) {
            numberList.add(number);
        }
        descSort = false;
        filterNumber = true;
        if (myAdapter == null)
            setNumberList();

    }

    private void setNumberList() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(this);
        myAdapter.getNumbers().addAll(numberList);
        myAdapter.setMinimum(Collections.min(numberList));
        myAdapter.setMaximum(Collections.max(numberList));
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == sort) {
            if (descSort) {
                Collections.reverse(numberList);
            } else {
                Collections.sort(numberList);
            }
            updateList();
            descSort = !descSort;
            item.setIcon(descSort ? R.drawable.sortdecend : R.drawable.sortascend);
            return true;


        } else if (id == filter) {
            if (filterNumber) {
                alertdialog(item);
            } else {

                updateList();
                filterNumber = !filterNumber;
                item.setIcon(filterNumber ? R.drawable.filters : R.drawable.cross);
                menu.getItem(0).setVisible(true);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void alertdialog(final MenuItem item) {
        final String[] text = new String[2];
        final View textEntryView = getLayoutInflater().inflate(layout.alert_dialog, null);

        input_x = (EditText) textEntryView.findViewById(R.id.value_x);
        input_y = (EditText) textEntryView.findViewById(R.id.value_y);
        final AlertDialog alert = new AlertDialog.Builder(MainActivity.this)
                .setView(textEntryView)

                .setTitle(R.string.range).setNegativeButton("Cancle", null)
                .setPositiveButton("Enter", null).create();
        alert.setOnShowListener(
                new DialogInterface.OnShowListener() {


                    @Override
                    public void onShow(final DialogInterface dialog) {
                        final Button enter = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                        final Button cancel = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        enter.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                text[0] = input_x.getText().toString().trim();
                                text[1] = input_y.getText().toString().trim();


                                if (text[0].isEmpty())
                                    input_x.setError(getString(R.string.empty));
                                else if (text[1].isEmpty())
                                    input_y.setError(getString(R.string.empty));
                                else if (Integer.parseInt(text[0]) > Integer.parseInt(text[1]))
                                    input_y.setError(getString(R.string.ySmall));
                                else {
                                    myAdapter.getNumbers().clear();
                                    for (int i = 0; i < numberList.size(); i++) {
                                        if (numberList.get(i) >= Integer.parseInt(text[0]) && numberList.get(i) <= Integer.parseInt(text[1]))
                                            myAdapter.getNumbers().add(numberList.get(i));
                                    }
                                    if (myAdapter.getNumbers().isEmpty()) {
                                        dialog.dismiss();
                                        noRecords.setVisibility(View.VISIBLE);
                                        recyclerView.setVisibility(View.GONE);

                                    } else {
                                        myAdapter.setMinimum(Collections.min(myAdapter.getNumbers()));
                                        myAdapter.setMaximum(Collections.max(myAdapter.getNumbers()));
                                        myAdapter.notifyDataSetChanged();
                                        noRecords.setVisibility(View.GONE);
                                        recyclerView.setVisibility(View.VISIBLE);
                                        dialog.dismiss();

                                    }

                                    menu.getItem(0).setVisible(false);
                                    filterNumber = !filterNumber;
                                    item.setIcon(filterNumber ? R.drawable.filters : R.drawable.cross);
                                }
                            }

                        });
                    }
                });

        alert.show();
    }

    private void updateList() {
        myAdapter.getNumbers().clear();
        myAdapter.setMinimum(Collections.min(numberList));
        myAdapter.setMaximum(Collections.max(numberList));
        myAdapter.getNumbers().addAll(numberList);
        myAdapter.notifyDataSetChanged();


    }


}
