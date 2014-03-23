package com.baidu.mapapi.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class tab1_chattingGroups extends Activity{
	
	/*private ListView listView;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.tab1_chattinggroups);
        
        listView = new ListView(this);
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
        setContentView(listView);
        
        
    }
	
	 private List<String> getData(){
         
	        List<String> data = new ArrayList<String>();
	        data.add("事件1");
	        data.add("事件2");
	        data.add("事件3");
	        data.add("事件4");
	         
	        return data;
	    }*/
	// Array of strings storing country names
    String[] friendsName = new String[] {
    		"张三 发起的新鲜事\n参与者：李四，王五",
            "李四 发起的新鲜事\n参与者：张三 王五",
            "牛人 发起的新鲜事\n参与者：奋斗者 张三 李四 王五 ...",
            "美女",
            "大神",
            "奋斗者",
            "哈哈",
            "好玩的",
            "流氓",
            "码神"
    };
    
    // Array of integers points to images stored in /res/drawable-ldpi/
    int[] friendsImage = new int[]{
    		R.drawable.g1,
    		R.drawable.g2,
    		R.drawable.g3,
    		R.drawable.f4,
    		R.drawable.f5,
    		R.drawable.f6,
    		R.drawable.f7,
    		R.drawable.f8,
    		R.drawable.f9,
    		R.drawable.f10
    };
	
    // Array of strings to store currencies
    String[] friendsSimpleDetail = new String[]{
    	"TA提到：\n“	我喜欢游泳，游啊游啊泳~~~”",
    	"TA提到：\n“	哪位同学在仙林掉了东西！”",
    	"TA提到：\n“	哈哈哈”",
    	"Renminbi",
    	"Bangladeshi Taka",
    	"Nepalese Rupee",
    	"Afghani",
    	"North Korean Won",
    	"South Korean Won",
    	"Japanese Yen"
    };
    
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1chattinggropus);        
      
        // Each row in the list stores sth.
        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();        
        
        for(int i=0;i<10;i++){
        	HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("txt", friendsName[i]);
            hm.put("cur",friendsSimpleDetail[i]);
            hm.put("flag", Integer.toString(friendsImage[i]) );            
            aList.add(hm);        
        }
        
        // Keys used in Hashmap
        String[] from = { "flag","txt","cur" };
        
        // Ids of views in listview_layout
        int[] to = { R.id.flag,R.id.txt,R.id.cur};        
        
        // Instantiating an adapter to store each items
        // R.layout.listview_layout defines the layout of each item
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.tab1groupslistview, from, to);
        
        // Getting a reference to listview of main.xml layout file
        ListView listView = ( ListView ) findViewById(R.id.listview);
        
        // Setting the adapter to the listView
        listView.setAdapter(adapter);
        
        
     
    }
 	
	
}
