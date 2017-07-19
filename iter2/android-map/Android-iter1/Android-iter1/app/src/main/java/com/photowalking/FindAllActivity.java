package com.photowalking;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.photowalking.adapter.TestAdapter;
import com.photowalking.model.SendBytes;
import com.photowalking.utils.OkManager;

/**
 * Created by liujinxu on 17/7/3.
 */

public class FindAllActivity extends AppCompatActivity {

    private List<SendBytes> friends = new ArrayList<SendBytes>();

    public static String getFriUrl = "http://192.168.1.165:8080/bzbp/rest/user/getPictureById/1";

    static String my_uid = "";




    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.friend_all);
        Intent intent=getIntent();
        final String me = intent.getStringExtra("me");
        my_uid = me;
        loadFriends();

//        ((ImageButton) findViewById(R.id.top_add))
//                .setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(FindAllActivity.this,
//                                AddFriendActivity.class);
//                        intent.putExtra("me",me);
//                        startActivityForResult(intent, 1);
//                    }
//                });
    }

    public boolean loadFriends() {
        new FetchFriendInfo().execute(getFriUrl);
        return true;

    }


    public class FetchFriendInfo extends AsyncTask<String, Void, List<SendBytes>> {

        @Override
        protected List<SendBytes> doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }
            String friUrl = params[0];
            OkManager manager = new OkManager<SendBytes>();
            List<SendBytes> friList = manager.getAll(friUrl, SendBytes.class);
            return friList;
        }

        @Override
        protected void onPostExecute(List<SendBytes> friends) {
            if (friends != null) {
                TestAdapter adapter = null;
                try {
                    adapter = new TestAdapter(FindAllActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (SendBytes f : friends) {
                    Log.v("friend: ", ""+f.getUid());
                    adapter.addItem(f);
                }
                ListView flist = (ListView) findViewById(R.id.friend_tab_list);
                flist.setAdapter(adapter);
//                flist.setOnItemClickListener(new MyOnItemClickListener());
            }
        }

//        private class MyOnItemClickListener implements AdapterView.OnItemClickListener {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position,
//                                    long id) {
//
//                final Friend friend = (Friend) parent.getItemAtPosition(position);
//                final Intent intent = new Intent(FindAllActivity.this,ShowUserInfoActivity.class);
//                new Thread(new Runnable(){
//                    @Override
//                public void run() {
//                        User user = getUser(friend);
//                        Gson gson = new Gson();
//                        String jsonstr = gson.toJson(user);
//                        intent.putExtra("jsonstr",jsonstr);
//                        intent.putExtra("me",my_uid);
//                    }}).start();
//                startActivityForResult(intent, 1);
//            }
//        }
//
//        public User getUser(Friend friend) {
//            User user = new User();
//            OkManager okManager = new OkManager();
//            user = okManager.getUserByUid("/", String.valueOf(friend.getUid()));
//            return user;
//        }

    }
}


