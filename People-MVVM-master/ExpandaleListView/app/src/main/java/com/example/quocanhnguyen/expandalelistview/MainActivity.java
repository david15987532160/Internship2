package com.example.quocanhnguyen.expandalelistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listViewExp)
    ExpandableListView explistView;

    ExpandableListAdapter listAdapter;
    List<String> listHeader;
    HashMap<String, List<String>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listHeader, listItem);
        explistView.setAdapter(listAdapter);

        explistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });

        explistView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(MainActivity.this, listHeader.get(groupPosition) + " Expanded", Toast.LENGTH_SHORT).show();
            }
        });

        explistView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(MainActivity.this, listHeader.get(groupPosition) + " Collapsed", Toast.LENGTH_SHORT).show();
            }
        });

        explistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        listHeader.get(groupPosition)
                                + ": "
                                + listItem.get(
                                listHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });
    }

    private void prepareListData() {
        listHeader = new ArrayList<>();
        listItem = new HashMap<>();

        listHeader.add("Top 250");
        listHeader.add("Now Showing");
        listHeader.add("Coming Soon..");

        List<String> top250 = new ArrayList<>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry man");

        List<String> nowShowing = new ArrayList<>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Up 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listItem.put(listHeader.get(0), top250);
        listItem.put(listHeader.get(1), nowShowing);
        listItem.put(listHeader.get(2), comingSoon);
    }
}