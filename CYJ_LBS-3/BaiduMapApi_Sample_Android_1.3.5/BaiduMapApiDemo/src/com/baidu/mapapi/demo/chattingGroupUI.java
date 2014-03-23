package com.baidu.mapapi.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class chattingGroupUI extends Activity{
	
	private Button sendButton = null;
	private EditText contentEditText = null;
	private ListView chatListView = null;
	private List<tab3_chatEntity> chatList = null;
	private ChatAdapter chatAdapter = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chattinggroup_ui);
        
        //给顶端的textview加上事件监听器
        TextView chat=(TextView)findViewById(R.id.chat);
        chat.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		Intent intent=new Intent();
        		intent.setClass(chattingGroupUI.this, chattingGroupDetails.class);
        		startActivity(intent);
        	}
        });
        
        
        contentEditText = (EditText) this.findViewById(R.id.et_content);
        sendButton = (Button) this.findViewById(R.id.btn_send);
        chatListView = (ListView) this.findViewById(R.id.listview);
        
        chatList = new ArrayList<tab3_chatEntity>();
        tab3_chatEntity chatEntity = null;
        tab3_chatEntity chatEntity1 = null;
        tab3_chatEntity chatEntity2 = null;
        for (int i = 0; i < 2; i++) {
        	chatEntity = new tab3_chatEntity();
//			if (i % 2 == 0) {
				chatEntity.setComeMsg(true);
				chatEntity.setContent("大家好");
				chatEntity.setChatTime("2013-09-20 15:12:32");
//			}else {

	        	chatEntity1 = new tab3_chatEntity();
				chatEntity1.setComeMsg(true);
				chatEntity1.setContent("大家都是在哪儿工作的啊？");
				chatEntity1.setChatTime("2013-09-20 15:13:32");
				
	        	chatEntity2 = new tab3_chatEntity();
				chatEntity2.setComeMsg(true);
				chatEntity2.setContent("哈，人很多嘛~");
				chatEntity2.setChatTime("2013-09-20 15:13:32");
//			}
			//每一条消息方法哦chatList里
			chatList.add(chatEntity);
			chatList.add(chatEntity1);
			chatList.add(chatEntity2);
		}
        
        chatAdapter = new ChatAdapter(this,chatList);
        chatListView.setAdapter(chatAdapter);
        
        sendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (!contentEditText.getText().toString().equals("")) {
					//发送消息
					send();
				}else {
					Toast.makeText(chattingGroupUI.this, "Content is empty", Toast.LENGTH_SHORT).show();
				}
			}
		});
        
    }
    
    private void send(){
    	tab3_chatEntity chatEntity = new tab3_chatEntity();
//    	chatEntity.setChatTime("2013-09-20 15:16:34");
    	String currentTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
    	chatEntity.setChatTime(currentTime);
    	chatEntity.setContent(contentEditText.getText().toString());
    	chatEntity.setComeMsg(false);
    	chatList.add(chatEntity);
    	chatAdapter.notifyDataSetChanged();
    	chatListView.setSelection(chatList.size() - 1);
    	contentEditText.setText("");
    }
    
    private  class ChatAdapter extends BaseAdapter{
    	private Context context = null;
    	private List<tab3_chatEntity> chatList = null;
    	private LayoutInflater inflater = null;
    	private int COME_MSG = 0;
    	private int TO_MSG = 1;
    	
    	public ChatAdapter(Context context,List<tab3_chatEntity> chatList){
    		this.context = context;
    		this.chatList = chatList;
    		inflater = LayoutInflater.from(this.context);
    	}

		@Override
		public int getCount() {
			return chatList.size();
		}

		@Override
		public Object getItem(int position) {
			return chatList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}
		
		@Override
		public int getItemViewType(int position) {
			// 区别两种view的类型，标注两个不同的变量来分别表示各自的类型  		 	
			tab3_chatEntity entity = chatList.get(position);
		 	if (entity.isComeMsg())
		 	{
		 		return COME_MSG;
		 	}else{
		 		return TO_MSG;
		 	}
		}

		@Override
		public int getViewTypeCount() {
			// 这个方法默认返回1，如果希望listview的item都是一样的就返回1，我们这里有两种风格，返回2 
			return 2;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ChatHolder chatHolder = null;
			if (convertView == null) {
				chatHolder = new ChatHolder();
				if (chatList.get(position).isComeMsg()) {
					convertView = inflater.inflate(R.layout.chat_from_item, null); 
				}else {
					convertView = inflater.inflate(R.layout.chat_to_item, null);
				}
				chatHolder.timeTextView = (TextView) convertView.findViewById(R.id.tv_time);
				chatHolder.contentTextView = (TextView) convertView.findViewById(R.id.tv_content);
				chatHolder.userImageView = (ImageView) convertView.findViewById(R.id.iv_user_image);
				convertView.setTag(chatHolder);
			}else {
				chatHolder = (ChatHolder)convertView.getTag();
			}
			
			chatHolder.timeTextView.setText(chatList.get(position).getChatTime());
			chatHolder.contentTextView.setText(chatList.get(position).getContent());
			chatHolder.userImageView.setImageResource(chatList.get(position).getUserImage());
			
			return convertView;
		}
		
		private class ChatHolder{
			private TextView timeTextView;
			private ImageView userImageView;
			private TextView contentTextView;
		}
    	
    }	

}
