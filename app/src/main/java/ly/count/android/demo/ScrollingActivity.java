package ly.count.android.demo;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import ly.count.android.sdk.Countly;

public class ScrollingActivity extends Activity {

    NestedScrollView scrollView;
    Countly c;

    View.OnScrollChangeListener l;
    ListView lv;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Countly.onCreate(this);
        c = Countly.sharedInstance();
        //Log.i(demoTag, "After calling init. This should return 'true', the value is:" + Countly.sharedInstance().isInitialized());

        l = new View.OnScrollChangeListener() {
//            private int lastScroll = 0;
            private int sectionLength = 500;
            int[] visitedSections = new int[100];

            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int section = scrollY/sectionLength;
                if(visitedSections[section] == 1) return;
                visitedSections[section] = 1;
                Map<String, String> data = new HashMap<>();
                data.put("section", "" + scrollY/sectionLength);
                c.recordEvent("scrollingEvent", data, 1);
            }
        };

        scrollView = (NestedScrollView) this.findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener(l);

    }



    @Override
    public void onStart()
    {
        super.onStart();
        Countly.sharedInstance().onStart(this);
    }

    @Override
    public void onStop()
    {
        Countly.sharedInstance().onStop();
        super.onStop();
    }
}
