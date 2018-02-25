package com.example.muhammadfarhan.farhan_1202144152_modul3;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Display;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private ArrayList<water> mWaterData;
    private waterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycleView = (RecyclerView) findViewById(R.id.recyclerview);

        Display screenOrientation = getWindowManager().getDefaultDisplay();
        int orientation = Configuration.ORIENTATION_UNDEFINED;

        if(screenOrientation.getWidth() < screenOrientation.getHeight()){
            orientation = Configuration.ORIENTATION_PORTRAIT;
            //Do something
            mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        }else {
            orientation = Configuration.ORIENTATION_LANDSCAPE;
            //Do something
            mRecycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        }

        int gridColumnCount = getResources().getInteger(R.integer.grid_column_count);

        mWaterData = new ArrayList<>();
        mAdapter = new waterAdapter(this, mWaterData);
        mRecycleView.setAdapter(mAdapter);

        initializeData();
        // If there is more than one column, disable swipe to dismiss
        int swipeDirs;
        if(gridColumnCount > 1){
            swipeDirs = 0;
        } else {
            swipeDirs = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }


        //Helper class for creating swipe to dismiss and drag and drop functionality
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
                        | ItemTouchHelper.UP, swipeDirs) {

            /**
             * Method that defines the drag and drop functionality
             * @param recyclerView The RecyclerView that contains the list items
             * @param viewHolder The SportsViewHolder that is being moved
             * @param target The SportsViewHolder that you are switching the original one with.
             * @return returns true if the item was moved, false otherwise
             */
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {

                //Get the from and to position
                int from = viewHolder.getAdapterPosition();
                int to = target.getAdapterPosition();

                //Swap the items and notify the adapter
                Collections.swap(mWaterData, from, to);
                mAdapter.notifyItemMoved(from, to);
                return true;
            }

            /**
             * Method that defines the swipe to dismiss functionality
             * @param viewHolder The viewholder being swiped
             * @param direction The direction it is swiped in
             */
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                //Remove the item from the dataset
                mWaterData.remove(viewHolder.getAdapterPosition());

                //Notify the adapter
                mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });

        //Attach the helper to the RecyclerView
        helper.attachToRecyclerView(mRecycleView);

    }

    private void initializeData() {

        TypedArray watersImageRes = getResources().obtainTypedArray(R.array.water_images);

        //Get the resources from the XML file
        String[] watersList = getResources().getStringArray(R.array.water_images);

        //Clear the existing data (to avoid duplication)
        mWaterData.clear();

        //Create the ArrayList of Sports objects with the titles and information about each sport
        for(int i=0;i<watersList.length;i++){
            String[] judul = {"aqua", "ades", "amidis", "cleo", "club", "equil",
                    "evian", "leminerale", "pristine", "vit", "nestle"};
            mWaterData.add(new water(judul[i], "Air minum merk "+judul[i], "Lorem ipsum dolor sit amet, consectetur adipiscing elit. " +
                    "Pellentesque accumsan laoreet diam in viverra. Etiam consequat quis ligula id gravida." +
                    " Sed sed maximus nulla. Nunc pulvinar cursus justo eu luctus. Pellentesque pellentesque" +
                    " velit ut tortor imperdiet congue. Proin quam velit, luctus nec placerat eu, vehicula nec sapien. " +
                    "Vestibulum dapibus dictum dapibus. Sed a ipsum vel lectus pharetra auctor. " +
                    "Nam vel arcu quis orci elementum commodo. Duis luctus, risus in faucibus dapibus, diam augue " +
                    "feugiat enim, et eleifend libero augue ac massa.",
                    watersImageRes.getResourceId(i,0)));
        }
        watersImageRes.recycle();
        mAdapter.notifyDataSetChanged();
    }
}
