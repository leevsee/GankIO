package com.leeves.h.gankio.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leeves.h.gankio.R;
import com.leeves.h.gankio.data.entity.BaseData;
import com.leeves.h.gankio.http.HttpMethods;
import com.leeves.h.gankio.ui.adapter.RecyclerCategoryAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;
import it.gmariotti.recyclerview.itemanimator.SlideInOutLeftItemAnimator;
import rx.Subscriber;

/**
 * Function：
 * Created by h on 2016/10/25.
 *
 * @author
 */

public class ContentFragment extends Fragment {

    @BindView(R.id.fg_rv_content)
    RecyclerView mFgRvContent;
    @BindView(R.id.fg_swipe_fresh_layout)
    SwipeRefreshLayout mFgSwipeFreshLayout;

    private static final String TAG = ContentFragment.class.getSimpleName();
    public static final String CATEGORYNAME = "category";

    private RecyclerCategoryAdapter mRCAdapter;
    private Subscriber mSubscriber;
    private List<BaseData> mBaseDatas;
    private GridLayoutManager mGridLayoutManager;
    private String category;
    private int pages = 1;
    private int lastVisibleItem;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach: ======");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            category = bundle.getString(CATEGORYNAME);
        }
        Log.i(TAG, "onCreate: ========");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_gank, container, false);
        ButterKnife.bind(this, v);
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    //销毁所有的Fragment
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    //侧边栏选中首页效果
                    NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
                    navigationView.getMenu().getItem(0).setChecked(true);
                    //设置标题
                    Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.my_toolbar);
                    mToolbar.setTitle("干货大本营");
                    ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
                }
                return false;
            }
        });
        initFGRecycler();
        fgRecyclerListener();
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubscriber.unsubscribe();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initFGRecycler() {
        mRCAdapter = new RecyclerCategoryAdapter(getActivity(), null);
        //设置固定RecyclerView大小
        mFgRvContent.setHasFixedSize(true);
        //设置一列
        mGridLayoutManager = new GridLayoutManager(getActivity(), 1);
//        mLayoutManager = new LinearLayoutManager(this);
        mFgRvContent.setLayoutManager(mGridLayoutManager);
        //设置RecyclerView动画
        mFgRvContent.setItemAnimator(new SlideInOutLeftItemAnimator(mFgRvContent));
        AlphaAnimatorAdapter alphaAnimatorAdapter = new AlphaAnimatorAdapter(mRCAdapter, mFgRvContent);
        mFgRvContent.setAdapter(alphaAnimatorAdapter);
        //获取数据
        getCategoryData(category, 15, pages);
        mFgSwipeFreshLayout.setRefreshing(true);
    }


    private void fgRecyclerListener() {
        //设置RecyclerView的滑动状态监听器
        mFgRvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * 当RecyclerView的滑动状态改变时触发
             * @param recyclerView
             * @param newState
             */
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mRCAdapter.getItemCount()) {
                    if (pages != 1) {
                        getCategoryData(category, 15, pages);
                        mFgSwipeFreshLayout.setRefreshing(true);
                    } else {
                        mFgSwipeFreshLayout.setRefreshing(false);
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
        mFgSwipeFreshLayout.setColorSchemeResources(R.color.color1, R.color.color2, R.color.color3, R.color.color4);
//        //设置下拉刷新监听器
        mFgSwipeFreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (pages == 1) {
                    getCategoryData(category, 15, pages);
                } else {
                    mFgSwipeFreshLayout.setRefreshing(false);
                }
            }
        });
    }


    /**
     * 获得分类的数据
     *
     * @param catagory
     * @param num
     * @param page
     */
    private void getCategoryData(String catagory, int num, int page) {
        mSubscriber = new Subscriber<List<BaseData>>() {
            @Override
            public void onCompleted() {
                mFgSwipeFreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                mFgSwipeFreshLayout.setRefreshing(false);
                Snackbar.make((getActivity()).findViewById(R.id.coordinator_layout), "服务器不稳定，获取数据失败，请重新获取", Snackbar.LENGTH_LONG).setAction("刷新", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCategoryData(category, 15, pages);
                        mFgSwipeFreshLayout.setRefreshing(true);
                    }
                }).show();
                Log.i("==========getAllContetn", "onError:" + e.getMessage());
            }

            @Override
            public void onNext(List<BaseData> baseDatas) {
                if (pages == 1) {
                    mRCAdapter.refreshData(baseDatas);
                    pages++;
                } else {
                    mRCAdapter.addFengMianData(baseDatas);
                    pages++;
                }
            }
        };
        HttpMethods.getInstance().goToCategoryData(mSubscriber, catagory, num, page);
    }
}
