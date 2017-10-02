package com.example.asus1.collectionelfin;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /** 抽屉视图 */
    private DrawerLayout mDrawerLayout;

    /** 侧滑菜单视图 */
    private NavigationView mMenuNv;

    /** 打开侧滑菜单 */
    private TextView mOpenMenuTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化界面
        initUI();
        //初始化监听
        initListener();
    }

    /**
     * 初始化界面
     */
    private void initUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mMenuNv = (NavigationView) findViewById(R.id.nv_layout);
        mOpenMenuTv = (TextView) findViewById(R.id.tv_open);
    }

    /**
     * 初始化监听
     */
    private void initListener() {

        mOpenMenuTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开侧滑菜单
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // 设置侧滑菜单点击事件监听
        mMenuNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectItem(item.getItemId());
                // 关闭侧滑菜单
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * 响应item点击事件
     * @param itemid
     */
    private void selectItem(int itemid) {
        switch (itemid) {
            case R.id.menu_mon:
                Toast.makeText(MainActivity.this, "点击Mon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_tues:
                Toast.makeText(MainActivity.this, "点击Tues", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_wed:
                Toast.makeText(MainActivity.this, "点击Wed", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_thurs:
                Toast.makeText(MainActivity.this, "点击Thurs", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_fri:
                Toast.makeText(MainActivity.this, "点击Fri", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_sat:
                Toast.makeText(MainActivity.this, "点击 Sat", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_sun:
                Toast.makeText(MainActivity.this, "点击 Sun", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

}