package com.baidu.mapapi.demo;


/*import com.mahesh.mydooplemenus.MainActivity;
import com.mahesh.mydooplemenus.MyDoople;
import com.mahesh.mydooplemenus.R;
*/
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class tab_MainActivity extends TabActivity{
	private static final int TAKE_PICTURE_ITEM = 0;
	private static final int SHOW_MY_INFO_ITEM = 1;
	private static final int LOG_OFF_ITEM = 2;
	private static final int SHOW_FRIENDS_ITEM = 3;
	private static final int SHOW_SHIELDING_PERSONS_ITEM = 4;
	private static final int SETTINGS_ITEM = 5;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabmain);//这里使用了上面创建的xml文件（Tab页面的布局）

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost();  // The activity TabHost
		TabSpec spec;
		Intent intent;  // Reusable Intent for each tab

		//第一个TAB
		intent = new Intent(this,tab1_chattingGroups.class);//新建一个Intent用作Tab1显示的内容
		//新建一个 Tab		
		//设置名称以及图标
		//设置显示的intent，这里的参数也可以是R.id.xxx
		//		spec = tabHost.newTabSpec("tab1").setIndicator("我参与的事件", res.getDrawable(android.R.drawable.ic_media_play)).setContent(intent);
		spec = tabHost.newTabSpec("tab1").setIndicator("我参与的事件", res.getDrawable(R.drawable.tab1)).setContent(intent);
		//参考：http://hi.baidu.com/gavin2862/item/d307d929e04e4798b63263be
		/*spec = tabHost.newTabSpec("tab1");
		spec.setIndicator("参与");
		spec.setContent(intent);*/
		tabHost.addTab(spec);//添加进tabHost

		//第二个TAB
		intent = new Intent(this,tab2_mapMain.class);//第二个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab2").setIndicator("身边的新鲜事", res.getDrawable(R.drawable.tab2)).setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost

		//第三个TAB
		intent = new Intent(this,tab3_chattingWithFriends.class);//第二个Intent用作Tab1显示的内容
		spec = tabHost.newTabSpec("tab3").setIndicator("私聊", res.getDrawable(R.drawable.tab3)).setContent(intent);//设置显示的intent，这里的参数也可以是R.id.xxx
		tabHost.addTab(spec);//添加进tabHost

		tabHost.setCurrentTab(1);
	}
	
	@Override  
    public boolean onCreateOptionsMenu(Menu menu){  
            // 方法一，用代码构建  
            /*menu.add(Menu.NONE, Menu.NONE, 1, "拍照上传");  
            menu.add(Menu.NONE, Menu.NONE, 2, "我的主页");  
            menu.add(Menu.NONE, Menu.NONE, 3, "注销");  
            menu.add(Menu.NONE, Menu.NONE, 4, "好友");  
            menu.add(Menu.NONE, Menu.NONE, 5, "屏蔽");  
            menu.add(Menu.NONE, Menu.NONE, 6, "设置");  */
		menu.add(Menu.NONE, TAKE_PICTURE_ITEM, Menu.NONE, "拍照上传");
	    menu.add(Menu.NONE, SHOW_MY_INFO_ITEM, Menu.NONE, "我的主页");
	    menu.add(Menu.NONE, LOG_OFF_ITEM, Menu.NONE, "注销");
	    menu.add(Menu.NONE, SHOW_FRIENDS_ITEM, Menu.NONE, "好友");
	    menu.add(Menu.NONE, SHOW_SHIELDING_PERSONS_ITEM, Menu.NONE, "屏蔽");
	    menu.add(Menu.NONE, SETTINGS_ITEM, Menu.NONE, "设置");
            return true;  
        }  
	
	public boolean onOptionsItemSelected(MenuItem item) {

		System.out.println(item);
		// when you click setting menu
		switch (item.getItemId()) {
		case 0:
//			Toast.makeText(tab_MainActivity.this, "Item 1", Toast.LENGTH_SHORT).show();
			//跳转
			takePicture();
			return true;
		case 1:
//			Toast.makeText(tab_MainActivity.this, "Item 2", Toast.LENGTH_SHORT).show();
			showMyInfo();
			return true;
		case 2:
//			Toast.makeText(tab_MainActivity.this, "Item 2", Toast.LENGTH_SHORT).show();
			return true;
		case 3:
//			Toast.makeText(tab_MainActivity.this, "Item 2", Toast.LENGTH_SHORT).show();
			showFriends();
			return true;
		case 4:
//			Toast.makeText(tab_MainActivity.this, "Item 2", Toast.LENGTH_SHORT).show();
			return true;
		case 5:
//			Toast.makeText(tab_MainActivity.this, "Item 2", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
	
	//跳转到上传图片的activity
		void takePicture(){
			//上传图片
			try {
				Intent intentofTakePicture = null;
				intentofTakePicture = new Intent(tab_MainActivity.this, button_takePicture.class);
				System.out.println("upload Picture");
				this.startActivity(intentofTakePicture);
			} catch ( ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}
	
	//跳转到“我的主页”Activity
		void showMyInfo(){
			//显示我的信息
			try {
				Intent intent = null;
				intent = new Intent(tab_MainActivity.this, button_myInfo.class);
				this.startActivity(intent);
			} catch ( ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}

	
	//跳转到好友列表
		void showFriends(){
			//显示好友列表
			try {
				Intent intent = null;
				intent = new Intent(tab_MainActivity.this, button_friends.class);
				this.startActivity(intent);
			} catch ( ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}
}
