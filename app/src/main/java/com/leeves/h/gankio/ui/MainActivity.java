package com.leeves.h.gankio.ui;


import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.leeves.h.gankio.R;
import com.leeves.h.gankio.data.entity.FengMian;
import com.leeves.h.gankio.http.HttpMethods;
import com.leeves.h.gankio.ui.adapter.RecyclerFengMianAdapter;
import com.leeves.h.gankio.ui.base.NavigationViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;
import rx.Subscriber;


public class MainActivity extends NavigationViewActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.rv_fengmian)
    RecyclerView mRvFengmian;
    @BindView(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeFreshLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;



    private Subscriber mSubscriber;
    private Snackbar mSnackbar;

    private GridLayoutManager mGridLayoutManager;
    private RecyclerFengMianAdapter mRecyclerAdapter;
    private int lastVisibleItem;
    private int pages = 1;


    @Override
    public boolean isMaterialMenu() {
        return true;
    }

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected String setToolbarTitle() {
        return "干货大本营";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initRecycler();
        recyclerListener();
        Log.i(TAG, "onCreate: =======");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: =======");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: =======");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: =======");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: =======");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop: =======");
    }

//    @OnClick(R.id.bn_test)
//    void testBn(){
////        getCategoryData("Android", 10, 1);
////        Log.i(TAG, "testBn: ======"+getFragmentManager().getBackStackEntryCount());
////       Toast toast =  Toast.makeText(this,"123",Toast.LENGTH_SHORT);
////        toast.setGravity(Gravity.CENTER,0,0);
////        toast.show();
////        Log.i(TAG, "testBn: ======"+pages);
//        DrawerLayout mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        Log.i(TAG, "testBn: ======"+mDrawer.isDrawerOpen(GravityCompat.START)+"="+mDrawer.isDrawerOpen(GravityCompat.END));
//
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 初始化RecyclerView
     */
    private void initRecycler() {
        mRecyclerAdapter = new RecyclerFengMianAdapter(this, null);
        //设置固定RecyclerView大小
        mRvFengmian.setHasFixedSize(true);
        //设置一列
        mGridLayoutManager = new GridLayoutManager(this, 1);
//        mLayoutManager = new LinearLayoutManager(this);
        mRvFengmian.setLayoutManager(mGridLayoutManager);
        //设置RecyclerView动画
        mRvFengmian.setItemAnimator(new SlideInOutLeftItemAnimator(mRvFengmian));
        AlphaAnimatorAdapter alphaAnimatorAdapter = new AlphaAnimatorAdapter(mRecyclerAdapter, mRvFengmian);
        mRvFengmian.setAdapter(alphaAnimatorAdapter);
        //获取数据
        getAllContetn(8, pages);
        mSwipeFreshLayout.setRefreshing(true);
    }


    private void recyclerListener() {
        //设置RecyclerView的滑动状态监听器
        mRvFengmian.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 当RecyclerView的滑动状态改变时触发
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //判断是否滑动到底部
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mRecyclerAdapter.getItemCount()) {
                    //判断是否为第一次加载
                    if (pages != 1) {
                        mSwipeFreshLayout.setRefreshing(true);
                        getAllContetn(8, pages);
                    } else {
                        mSwipeFreshLayout.setRefreshing(false);
                    }
                    Log.i(TAG, "onScrollStateChanged: " + pages);
                }
            }

            /**
             * 当RecyclerView滑动时触发
             * @param recyclerView
             * @param dx
             * @param dy
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
            }
        });

        //设置下拉刷新颜色
        mSwipeFreshLayout.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
//        //设置下拉刷新监听器
        mSwipeFreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (pages == 1) {
                    getAllContetn(8, pages);
                } else {
                    mSwipeFreshLayout.setRefreshing(false);
                }
            }
        });
    }


    /**
     * 从api获得数据
     *
     * @param num
     * @param page
     */
    private void getAllContetn(int num, int page) {
        mSubscriber = new Subscriber<List<FengMian>>() {
            @Override
            public void onCompleted() {
                mSwipeFreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mSwipeFreshLayout.setRefreshing(false);
                Snackbar.make(findViewById(R.id.coordinator_layout),"服务器不稳定，获取数据失败，请重新获取",Snackbar.LENGTH_LONG).setAction("刷新", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            getAllContetn(8, pages);
                            mSwipeFreshLayout.setRefreshing(true);
                    }
                }).show();
                Log.i("==========getAllContetn", "onError:" + e.getMessage());
            }

            @Override
            public void onNext(List<FengMian> fengMien) {
                if (pages == 1) {
                    mRecyclerAdapter.refreshData(fengMien);
                    pages++;
                } else {
                    mRecyclerAdapter.addFengMianData(fengMien);
                    pages++;
                }
            }
        };
        HttpMethods.getInstance().goToContent(mSubscriber, num, page);
    }
}
