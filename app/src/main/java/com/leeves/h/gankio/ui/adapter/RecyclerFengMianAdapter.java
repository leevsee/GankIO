package com.leeves.h.gankio.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leeves.h.gankio.R;
import com.leeves.h.gankio.data.entity.FengMian;
import com.leeves.h.gankio.ui.ContentActity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Function：
 * Created by h on 2016/10/20.
 *
 * @author Leeves
 */

public class RecyclerFengMianAdapter
        extends RecyclerView.Adapter<RecyclerFengMianAdapter.ViewHolder> {

    private static final String TAG = "RecyclerFengMianAdapter";

    private Context mContext;
    private List<FengMian> mFengMianList;

    public RecyclerFengMianAdapter(Context context, List<FengMian> dateList) {
        mContext = context;
        mFengMianList = dateList;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cover, parent, false);
        return new ViewHolder(v);
    }

    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTvDate.setText(mFengMianList.get(position).getDate());
        holder.mTvCoverTitle.setText(mFengMianList.get(position).getTitle());
        Picasso.with(mContext).load(mFengMianList.get(position).getMeiZhiImgUrl()).resize(250, 250).into(holder.mIvCover);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (mFengMianList != null) {
            return mFengMianList.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cover)
        ImageView mIvCover;
        @BindView(R.id.tv_cover_title)
        TextView mTvCoverTitle;
        @BindView(R.id.tv_date)
        TextView mTvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

//            mIvCover.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(mContext,getLayoutPosition(),Toast.LENGTH_SHORT).show();
////                    Log.d(TAG, "ImageView -------" + mFengMianList.get(getLayoutPosition()).getDate());
//                }
//            });
//            mTvCoverTitle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext, ContentActity.class);
//                    intent.putExtra("date", mFengMianList.get(getLayoutPosition()).getDate());
//                    intent.putExtra("meiZhiUrl", mFengMianList.get(getLayoutPosition()).getMeiZhiImgUrl());
//                    mContext.startActivity(intent);
//                }
//            });
            //跳转
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ContentActity.class);
                    intent.putExtra("date", mFengMianList.get(getLayoutPosition()).getDate());
                    intent.putExtra("meiZhiUrl", mFengMianList.get(getLayoutPosition()).getMeiZhiImgUrl());
                    mContext.startActivity(intent);
                }
            });
        }

    }

    /**
     * 首次更新List
     * @param dateList
     */
    public void refreshData(List<FengMian> dateList) {
        mFengMianList = dateList;
        notifyDataSetChanged();
    }

    /**
     * 刷新添加新的List
     * @param dateList
     */
    public void addFengMianData(List<FengMian> dateList) {
        mFengMianList.addAll(dateList);
        notifyDataSetChanged();
    }

    private float dp2px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return dp * density;
    }
}
