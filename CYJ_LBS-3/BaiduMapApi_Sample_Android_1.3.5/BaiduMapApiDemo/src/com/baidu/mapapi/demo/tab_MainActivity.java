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
		setContentView(R.layout.tabmain);//����ʹ�������洴����xml�ļ���Tabҳ��Ĳ��֣�

		Resources res = getResources(); // Resource object to get Drawables
		TabHost tabHost = getTabHost();  // The activity TabHost
		TabSpec spec;
		Intent intent;  // Reusable Intent for each tab

		//��һ��TAB
		intent = new Intent(this,tab1_chattingGroups.class);//�½�һ��Intent����Tab1��ʾ������
		//�½�һ�� Tab		
		//���������Լ�ͼ��
		//������ʾ��intent������Ĳ���Ҳ������R.id.xxx
		//		spec = tabHost.newTabSpec("tab1").setIndicator("�Ҳ�����¼�", res.getDrawable(android.R.drawable.ic_media_play)).setContent(intent);
		spec = tabHost.newTabSpec("tab1").setIndicator("�Ҳ�����¼�", res.getDrawable(R.drawable.tab1)).setContent(intent);
		//�ο���http://hi.baidu.com/gavin2862/item/d307d929e04e4798b63263be
		/*spec = tabHost.newTabSpec("tab1");
		spec.setIndicator("����");
		spec.setContent(intent);*/
		tabHost.addTab(spec);//��ӽ�tabHost

		//�ڶ���TAB
		intent = new Intent(this,tab2_mapMain.class);//�ڶ���Intent����Tab1��ʾ������
		spec = tabHost.newTabSpec("tab2").setIndicator("��ߵ�������", res.getDrawable(R.drawable.tab2)).setContent(intent);//������ʾ��intent������Ĳ���Ҳ������R.id.xxx
		tabHost.addTab(spec);//��ӽ�tabHost

		//������TAB
		intent = new Intent(this,tab3_chattingWithFriends.class);//�ڶ���Intent����Tab1��ʾ������
		spec = tabHost.newTabSpec("tab3").setIndicator("˽��", res.getDrawable(R.drawable.tab3)).setContent(intent);//������ʾ��intent������Ĳ���Ҳ������R.id.xxx
		tabHost.addTab(spec);//��ӽ�tabHost

		tabHost.setCurrentTab(1);
	}
	
	@Override  
    public boolean onCreateOptionsMenu(Menu menu){  
            // ����һ���ô��빹��  
            /*menu.add(Menu.NONE, Menu.NONE, 1, "�����ϴ�");  
            menu.add(Menu.NONE, Menu.NONE, 2, "�ҵ���ҳ");  
            menu.add(Menu.NONE, Menu.NONE, 3, "ע��");  
            menu.add(Menu.NONE, Menu.NONE, 4, "����");  
            menu.add(Menu.NONE, Menu.NONE, 5, "����");  
            menu.add(Menu.NONE, Menu.NONE, 6, "����");  */
		menu.add(Menu.NONE, TAKE_PICTURE_ITEM, Menu.NONE, "�����ϴ�");
	    menu.add(Menu.NONE, SHOW_MY_INFO_ITEM, Menu.NONE, "�ҵ���ҳ");
	    menu.add(Menu.NONE, LOG_OFF_ITEM, Menu.NONE, "ע��");
	    menu.add(Menu.NONE, SHOW_FRIENDS_ITEM, Menu.NONE, "����");
	    menu.add(Menu.NONE, SHOW_SHIELDING_PERSONS_ITEM, Menu.NONE, "����");
	    menu.add(Menu.NONE, SETTINGS_ITEM, Menu.NONE, "����");
            return true;  
        }  
	
	public boolean onOptionsItemSelected(MenuItem item) {

		System.out.println(item);
		// when you click setting menu
		switch (item.getItemId()) {
		case 0:
//			Toast.makeText(tab_MainActivity.this, "Item 1", Toast.LENGTH_SHORT).show();
			//��ת
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
	
	//��ת���ϴ�ͼƬ��activity
		void takePicture(){
			//�ϴ�ͼƬ
			try {
				Intent intentofTakePicture = null;
				intentofTakePicture = new Intent(tab_MainActivity.this, button_takePicture.class);
				System.out.println("upload Picture");
				this.startActivity(intentofTakePicture);
			} catch ( ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}
	
	//��ת�����ҵ���ҳ��Activity
		void showMyInfo(){
			//��ʾ�ҵ���Ϣ
			try {
				Intent intent = null;
				intent = new Intent(tab_MainActivity.this, button_myInfo.class);
				this.startActivity(intent);
			} catch ( ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}

	
	//��ת�������б�
		void showFriends(){
			//��ʾ�����б�
			try {
				Intent intent = null;
				intent = new Intent(tab_MainActivity.this, button_friends.class);
				this.startActivity(intent);
			} catch ( ActivityNotFoundException e) {
				e.printStackTrace();
			}
		}
}
