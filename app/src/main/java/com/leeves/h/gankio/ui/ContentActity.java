package com.leeves.h.gankio.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leeves.h.gankio.R;
import com.leeves.h.gankio.data.entity.BaseData;
import com.leeves.h.gankio.http.HttpMethods;
import com.leeves.h.gankio.ui.adapter.RecyclerContentAdapter;
import com.leeves.h.gankio.ui.base.ToolbarActivity;
import com.leeves.h.gankio.util.MyDateUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;
import rx.Subscriber;

/**
 * Function：
 * Created by h on 2016/10/24.
 *
 * @author Leeves
 */

public class ContentActity extends ToolbarActivity {

    private static final String TAG = ContentActity.class.getSimpleName();

    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_fresh_layout)
    SwipeRefreshLayout mSwipeFreshLayout;

    private List<BaseData> mDataList;
    private Subscriber mSubscriber;
    private RecyclerContentAdapter mRecyclerContentAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean isGetData = false;
    private String mDate;
    private String mMeiZhiUrl;

    @Override
    protected int provideContentViewId() {

        return R.layout.activity_gank_data;
    }

    @Override
    protected String setToolbarTitle() {
        Intent intent = getIntent();
        if (intent != null) {
            mDate = intent.getStringExtra("date");
            mMeiZhiUrl = intent.getStringExtra("meiZhiUrl");
        }
        return mDate;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initRecycler();
        recyclerListener();
    }


    @Override
    public boolean isCanBack() {
        return true;
    }

    @Override
    public boolean isMaterialMenu() {
        return false;
    }

    private void initRecycler() {
        mRecyclerContentAdapter = new RecyclerContentAdapter(this, mDataList,mMeiZhiUrl);
        //设置固定RecyclerView大小
        mRvContent.setHasFixedSize(true);
        //设置LinearLayout
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRvContent.setLayoutManager(mLinearLayoutManager);
        //设置RecyclerView动画
        mRvContent.setItemAnimator(new SlideInOutLeftItemAnimator(mRvContent));
        AlphaAnimatorAdapter alphaAnimatorAdapter = new AlphaAnimatorAdapter(mRecyclerContentAdapter, mRvContent);
        mRvContent.setAdapter(alphaAnimatorAdapter);
        //获取数据
        getDayData(MyDateUtils.toGetYear(mDate), MyDateUtils.toGetMonth(mDate), MyDateUtils.toGetDay(mDate));
        mSwipeFreshLayout.setRefreshing(true);
    }

    //设置下拉刷新
    private void recyclerListener() {
        mSwipeFreshLayout.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3,R.color.color4);
        mSwipeFreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //判断是否加载了数据
                if (!isGetData) {
                    getDayData(MyDateUtils.toGetYear(mDate), MyDateUtils.toGetMonth(mDate), MyDateUtils.toGetDay(mDate));
                } else {
                    mSwipeFreshLayout.setRefreshing(false);
                }
            }
        });

    }

    /**
     * 获得某一天的数据
     *
     * @param year
     * @param month
     * @param date
     */
    private void getDayData(int year, int month, int date) {
        mSubscriber = new Subscriber<List<BaseData>>() {
            @Override
            public void onCompleted() {
                isGetData = true;
                mSwipeFreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mSwipeFreshLayout.setRefreshing(false);
                Snackbar.make(findViewById(R.id.ly_gank),"服务器不稳定，获取数据失败，请重新获取",Snackbar.LENGTH_LONG).setAction("刷新", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDayData(MyDateUtils.toGetYear(mDate), MyDateUtils.toGetMonth(mDate), MyDateUtils.toGetDay(mDate));
                        mSwipeFreshLayout.setRefreshing(true);
                    }
                }).show();
            }

            @Override
            public void onNext(List<BaseData> baseJsons) {
                mRecyclerContentAdapter.refreshData(baseJsons);
            }
        };
        HttpMethods.getInstance().goToDayData(mSubscriber, year, month, date);
    }
}
