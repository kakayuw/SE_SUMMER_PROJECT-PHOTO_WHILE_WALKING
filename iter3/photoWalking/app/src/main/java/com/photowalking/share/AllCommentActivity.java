package com.photowalking.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.photowalking.R;
import com.photowalking.adapter.CommentAdapter;
import com.photowalking.adapter.ShareAdapter;
import com.photowalking.fragment.ShareFragment;
import com.photowalking.model.Comment;
import com.photowalking.model.ShareItem;
import com.photowalking.utils.OkManager;
import com.photowalking.utils.UrlPath;
import com.photowalking.viewUtils.StatusBarUtil;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by liujinxu on 17/8/12.
 */

public class AllCommentActivity extends Activity{

    @Bind(R.id.share_comment_back)
    LinearLayout ll_back;
    @Bind(R.id.share_comment_num)
    TextView tv_num;
    @Bind(R.id.share_comment_list)
    ListView commentList;
    @Bind(R.id.share_comment_loading)
    LinearLayout ll_load;
    @Bind(R.id.share_comment_err)
    LinearLayout ll_err;
    @Bind(R.id.share_comment_btn)
    Button btn_try;
    @Bind(R.id.share_comment_edit)
    EditText et_comment;
    @Bind(R.id.share_comment_tv)
    TextView textView;
    @Bind(R.id.share_comment_send)
    LinearLayout ll_send;

    private String uid;
    private String sid;
    private String uname;
    private CommentAdapter adapter;
    private Integer uidb = null;
    private String unameb = null;
    private int num;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusBarUtil.setColor(this, R.color.primary);
        setContentView(R.layout.share_comment);
        ButterKnife.bind(this);
        Intent intent1 = this.getIntent();
        uid = intent1.getStringExtra("uid");
        sid = intent1.getStringExtra("sid");
        uname = intent1.getStringExtra("uname");
        num = intent1.getIntExtra("num", 0);


        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_num.setText("("+String.valueOf(num)+")");

        btn_try.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new LoadCommentTask().execute();
            }
        });

        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str= et_comment.getText().toString();
                if(TextUtils.isEmpty(str)){
                    textView.setTextColor(ContextCompat.getColor(AllCommentActivity.this,R.color.monsoon));
                    ll_send.setEnabled(false);
                } else{
                    textView.setTextColor(ContextCompat.getColor(AllCommentActivity.this,R.color.primary));
                    ll_send.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        ll_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String words = et_comment.getText().toString();
                final Comment comment = new Comment(sid,Integer.parseInt(uid),uidb,uname,unameb,words);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkManager okManager = new OkManager();
                        Gson gson = new Gson();
                        String jsonstr = gson.toJson(comment);
                        final String result = okManager.sendStringByPost(UrlPath.addCommentUrl, jsonstr);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String msg = "发表失败";
                                if(result.charAt(0)=='c'){
                                    msg = "发表成功";
                                    String cid = result.substring(1);
                                    comment.setCid(Integer.valueOf(cid));
                                    adapter.addItem(comment);
                                    adapter.notifyDataSetChanged();
                                    ++num;
                                    tv_num.setText("("+String.valueOf(num)+")");
                                    ((ShareAdapter)ShareFragment.listView.getAdapter()).addComment(sid, num);
                                }
                                ETreset();
                                Toast.makeText(AllCommentActivity.this,msg,Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).start();
            }
        });

        commentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                uidb = adapter.getItem(position).getUida();
                unameb = adapter.getItem(position).getUnamea();
                final String[] choices = new String[3];
                choices[0] = "回复评论";
                choices[1] = "复制评论";
                if(unameb.equals(uname))
                    choices[2] = "删除评论";
                else
                    choices[2] = "分享评论";
                new AlertDialog.Builder(AllCommentActivity.this)
                        .setItems(choices, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case 0:
                                        et_comment.setHint("@"+unameb+":");
                                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.showSoftInput(et_comment,InputMethodManager.SHOW_FORCED);
                                        break;
                                    case 1:
                                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData mClipData = ClipData.newPlainText("Label",adapter.getItem(position).getComment());
                                        cm.setPrimaryClip(mClipData);
                                        Toast.makeText(AllCommentActivity.this,"已复制到剪贴板",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        if(choices[2].equals("删除评论")){
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    OkManager manager = new OkManager();
                                                    final String result = manager.sendStringByGet(UrlPath.delCommentUrl
                                                            +adapter.getItem(position).getCid()+"/"+sid);
                                                    runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            String msg = "删除失败";
                                                            if(result.equals("success")){
                                                                msg = "删除成功";
                                                                adapter.delItem(position);
                                                                adapter.notifyDataSetChanged();
                                                                --num;
                                                                tv_num.setText("("+String.valueOf(num)+")");
                                                                ((ShareAdapter)ShareFragment.listView.getAdapter()).delComment(sid, num);
                                                            }
                                                            Toast.makeText(AllCommentActivity.this,msg,Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            }).start();
                                        }else{
                                            Intent shareIntent = new Intent();
                                            shareIntent.setAction(Intent.ACTION_SEND);
                                            shareIntent.setType("text/plain");
                                            shareIntent.putExtra(Intent.EXTRA_TEXT, adapter.getItem(position).getComment());
                                            startActivity(Intent.createChooser(shareIntent, "分享到"));
                                            startActivity(shareIntent);
                                        }
                                    default: break;
                                }
                                dialog.dismiss();
                            }
                        }).show();
            }
        });

        new LoadCommentTask().execute();

    }

    private void ETreset(){
        et_comment.setText("");
        et_comment.setHint(R.string.comment_hint);
        uidb=null;
        unameb=null;
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm.isActive())
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);

    }

    private void showLoad(){
        ll_err.setVisibility(View.GONE);
        commentList.setVisibility(View.GONE);
        ll_load.setVisibility(View.VISIBLE);
    }


    public class LoadCommentTask extends AsyncTask<Void, Void, List<Comment>> {

        @Override
        protected void onPreExecute() {
            showLoad();
        }

        @Override
        protected List<Comment> doInBackground(Void... params) {
            OkManager manager = new OkManager<>();
            List<Comment> res = manager.getAll(UrlPath.allCommentUrl+sid, Comment.class);
            return res;
        }

        @Override
        protected void onPostExecute(List<Comment> res) {
            ll_load.setVisibility(View.GONE);
            if (res == null){
                ll_err.setVisibility(View.VISIBLE);
                commentList.setVisibility(View.GONE);
                return;
            }
            ll_err.setVisibility(View.GONE);
            commentList.setVisibility(View.VISIBLE);
            try {
                if(adapter == null)
                    adapter = new CommentAdapter(AllCommentActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (Comment c : res) {
                adapter.addItem(c);
            }
            commentList.setAdapter(adapter);
        }
    }

}
