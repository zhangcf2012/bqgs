package com.wyxz.chaogao.ui.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.libs.utils.AndroidUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;
import com.umeng.fb.model.UserInfo;
import com.wyxz.chaogao.R;

/**
 * 意见反馈
 * @author zhangchengfu
 *
 */
public class FeedbackActivity extends BaseLoadingActivity implements OnClickListener {
    
    private FeedbackAgent mFeedbackAgent;
    private Conversation mDefaultConversation;
    private static final String KEY_UMENG_CONTACT_INFO_PLAIN_TEXT = "plain";
    private static final String FIRST_TIME_USE_FEEDBACK = "firstTimeUsingFeedback";
    private static final String LAST_FEEDBACK_CONTENT = "userLastFeedbackInput";
    
    private ListView mReplyListView;
    private EditText mReplyContentEditText;
    private EditText mContactInfoEditText;
    private Button mBtnSend;
    
    private FeedListAdapter mAdapter;
    
    private SharedPreferences mPreference;
    
    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        return View.inflate(this, R.layout.umeng_fb_activity_conversation, null);
    }
    
    private void showContactInfo() {
        try {
            String contact_info = "";
            UserInfo info = mFeedbackAgent.getUserInfo();
            if (info == null) {
                mContactInfoEditText.setText("");
                return;
            }
            Map<String, String> contact = info.getContact();
            if (contact == null) {
                mContactInfoEditText.setText("");
                return;
            }
            contact_info = contact.get(KEY_UMENG_CONTACT_INFO_PLAIN_TEXT);
            if (contact_info == null) {
                mContactInfoEditText.setText("");
                return;
            }
            mContactInfoEditText.setText(contact_info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initListView() {
        mReplyListView = (ListView) findViewById(R.id.umeng_fb_reply_list);
        mAdapter = new FeedListAdapter(this);
        mReplyListView.setAdapter(mAdapter);
    }
    @Override
    public void initView() {
        setTitle(getString(R.string.more_feed_back));
        setLeftDrawable(R.drawable.btn_left_back_icon_selector_night);
        hideRightView(true);
        mPreference = getSharedPreferences("weiboprefer", 0);
        try {
            mFeedbackAgent = new FeedbackAgent(this);
            mDefaultConversation = mFeedbackAgent.getDefaultConversation();
            showContactInfo();
            initListView();
        } catch (Exception e) {
            e.printStackTrace();
            this.finish();
        }
        mContactInfoEditText = (EditText) this.findViewById(R.id.umeng_fb_contact_info);
        mReplyContentEditText = (EditText) findViewById(R.id.umeng_fb_reply_content);
        mReplyListView = (ListView) findViewById(R.id.umeng_fb_reply_list);
        mBtnSend = (Button) findViewById(R.id.umeng_fb_send);
        mBtnSend.setOnClickListener(this);
        String replyContent = mPreference.getString(LAST_FEEDBACK_CONTENT, "");// 从保存的SharedPreference中恢复用户上一次填写的内容
        mReplyContentEditText.setText(replyContent);
    }
    
    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(v.getId() == R.id.umeng_fb_send){
            if (!checkNetWorkStatusAndShowToast()) {
                return;
            }
            sendFeedBack();
        }
    }
    @Override
    protected void onLeftCLick() {
        onBackPressed();
    }
    
    void sync() {
        Conversation.SyncListener listener = new Conversation.SyncListener()
        {
            @Override
            public void onSendUserReply(List<Reply> replyList) {
                // LogUtil.v(TAG, "onSendUserReply");
                mAdapter.notifyDataSetChanged();
            }
            
            @Override
            public void onReceiveDevReply(List<DevReply> replyList) {
                // LogUtil.v(TAG, "onReceiveDevReply");
                mAdapter.notifyDataSetChanged();
            }
        };
        mDefaultConversation.sync(listener);
    }
    
    // 保存用户未发送的内容
    private void saveReplyContent() {
        String userLastFeedbackInput = mReplyContentEditText.getEditableText().toString().trim();
        mPreference.edit().putString(LAST_FEEDBACK_CONTENT, userLastFeedbackInput).commit();
    }
    
    private void saveContactInfo() {
        try {
            UserInfo info = mFeedbackAgent.getUserInfo();
            if (info == null) {
                info = new UserInfo();
            }
            Map<String, String> infoMap = info.getContact();
            if (infoMap == null) {
                infoMap = new HashMap<String, String>();
            }
            String contact = mContactInfoEditText.getEditableText().toString();
            infoMap.put(KEY_UMENG_CONTACT_INFO_PLAIN_TEXT, contact);
            info.setContact(infoMap);
            mFeedbackAgent.setUserInfo(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void sendFeedBack() {
        saveContactInfo();
//        MobclickAgent.onEvent(this, Configuration.UmengEvents.REQ_FEEDBACK);
        try {
            String content = mReplyContentEditText.getEditableText().toString().trim();
            if ("".equals(content)) {
                Toast.makeText(this, getString(R.string.umeng_fb_no_content), Color.WHITE).show();
                return;
            }
            
            mReplyContentEditText.getEditableText().clear();
            mDefaultConversation.addUserReply(content);
            
            sync();
            
            // hide soft input window after sending.
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(mReplyContentEditText.getWindowToken(), 0);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        sync();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        saveReplyContent();
    }
    
    public class FeedListAdapter extends BaseAdapter {
        
        private Context mContext;
        private LayoutInflater mInflater;
        
        public FeedListAdapter(Context context) {
            mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }
        
        @Override
        public int getCount() {
            List<Reply> replyList = mDefaultConversation.getReplyList();
            return (replyList == null) ? 1 : replyList.size() + 1;
        }
        
        @Override
        public Object getItem(int position) {
            return mDefaultConversation.getReplyList().get(position);
        }
        
        @Override
        public long getItemId(int position) {
            return position;
        }
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.umeng_fb_list_item, null);
                holder.replyDate = (TextView) convertView.findViewById(R.id.umeng_fb_reply_date);
                holder.replyContent = (TextView) convertView.findViewById(R.id.umeng_fb_reply_content);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            Reply reply = ((position == 0) ? null : mDefaultConversation.getReplyList().get(position - 1));
            if (position == 0 || reply instanceof DevReply) {
                boolean flag = position == 0;
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.leftMargin = AndroidUtils.dip2px(mContext, 8);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                holder.replyContent.setLayoutParams(layoutParams);
                
                holder.replyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(flag ? new Date() : reply
                        .getDatetime()));
                holder.replyContent.setText(flag ? FeedbackActivity.this.getResources().getString(
                        R.string.umeng_fb_reply_content_default) : reply.getContent());
                holder.replyContent.setBackgroundDrawable(FeedbackActivity.this.getResources().getDrawable(
                        R.drawable.umeng_fb_reply_left_bg));
                
            } else {
                --position;
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.rightMargin = AndroidUtils.dip2px(mContext, 8);
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.replyContent.setLayoutParams(layoutParams);
                
                holder.replyDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reply.getDatetime()));
                holder.replyContent.setText(reply.getContent());
                holder.replyContent.setBackgroundDrawable(FeedbackActivity.this.getResources().getDrawable(
                        R.drawable.umeng_fb_reply_right_bg));
            }
            return convertView;
        }
        
        class ViewHolder {
            TextView replyDate;
            TextView replyContent;
        }
        
    }
    
}
