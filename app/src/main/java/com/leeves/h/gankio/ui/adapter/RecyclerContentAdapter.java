package com.leeves.h.gankio.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leeves.h.gankio.R;
import com.leeves.h.gankio.data.entity.BaseData;
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

public class RecyclerContentAdapter extends RecyclerView.Adapter<RecyclerContentAdapter.ViewHolder> {

    private static final String TAG = "RecyclerFengMianAdapter";

    private Context mContext;
    private List<BaseData> mDataList;
    private String meiZhiUrl;

    public RecyclerContentAdapter(Context context, List<BaseData> dataList, String url) {
        mContext = context;
        mDataList = dataList;
        meiZhiUrl = url;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //判断显示分类及图片
        if (position == 0) {
            holder.mTvCategory.setVisibility(View.VISIBLE);
            holder.mIvMeizhi.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(meiZhiUrl).into(holder.mIvMeizhi);
        } else {
            isShowCategory(mDataList.get(position - 1).getType(), mDataList.get(position).getType(), holder);
            holder.mIvMeizhi.setVisibility(View.GONE);
        }
        //底部友情提示
        if (position == mDataList.size() - 1) {
            holder.mTvFoot.setVisibility(View.VISIBLE);
        }

        //设置文章字体的不同格式：   “via：作者” 是小字
        String via = "    (via:" + mDataList.get(position).getWho() + ")";
        int descLength = mDataList.get(position).getDesc().length();
        int typeLength = via.length();

        SpannableStringBuilder sb = new SpannableStringBuilder(mDataList.get(position).getDesc() + via);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.GRAY);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) dp2px(10));

        sb.setSpan(fcs, descLength, descLength + typeLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(ass, descLength, descLength + typeLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        holder.mTvCategory.setText(mDataList.get(position).getType());
        holder.mTvTitle.setText(sb);
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        }
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.iv_meizhi)
        ImageView mIvMeizhi;
        @BindView(R.id.tv_foot)
        TextView mTvFoot;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //跳转文章链接
            mTvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri uri = Uri.parse(mDataList.get(getLayoutPosition()).getUrl());
                    intent.setData(uri);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 是否显示Category
     *
     * @param categroy
     * @param nextCategroy
     * @param holder
     */
    private void isShowCategory(String categroy, String nextCategroy, ViewHolder holder) {
        if (TextUtils.equals(categroy, nextCategroy)) {
            if (holder.mTvCategory.getVisibility() == View.VISIBLE) {
                holder.mTvCategory.setVisibility(View.GONE);
            }
        } else {
            if (holder.mTvCategory.getVisibility() == View.GONE) {
                holder.mTvCategory.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 更新RecyclerAdapter
     *
     * @param list
     */
    public void refreshData(List<BaseData> list) {
        mDataList = list;
        notifyDataSetChanged();
    }

    private float dp2px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return dp * density;
    }
}
