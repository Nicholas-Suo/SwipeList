package tech.xiaosuo.com.swipelist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import tech.xiaosuo.com.swipelist.Views.SwipeListView;

public class MainActivity extends AppCompatActivity {

    SwipeListView swipeListView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeListView = (SwipeListView)findViewById(R.id.swipe_list);
        SwipeAdapter adapter = new SwipeAdapter(getBaseContext());
        swipeListView.setAdapter(adapter);
        List<String> list = new ArrayList<String>();
        for(int i=0;i<1;i++){
            list.add("item "+i);
        }
        adapter.setData(list);
    }



}
