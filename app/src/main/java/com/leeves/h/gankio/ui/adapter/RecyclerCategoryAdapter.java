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
import android.widget.TextView;

import com.leeves.h.gankio.R;
import com.leeves.h.gankio.data.entity.BaseData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Function：
 * Created by h on 2016/10/25.
 *
 * @author Leeves
 */

public class RecyclerCategoryAdapter extends RecyclerView.Adapter<RecyclerCategoryAdapter.ViewHolder> {

    private static final String TAG = RecyclerCategoryAdapter.class.getSimpleName();
    private Context mContext;
    private List<BaseData> mCategoryDataList;


    public RecyclerCategoryAdapter(Context context, List<BaseData> dataList) {
        mContext = context;
        mCategoryDataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gank,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //判断是否显示分类TextView
        if (position == 0) {
            holder.mTvCategory.setVisibility(View.VISIBLE);
        } else {
            isShowCategory(mCategoryDataList.get(position - 1).getType(), mCategoryDataList.get(position).getType(), holder);
        }
        //在TextView显示不同样式的文字
        String via = "    (via:" + mCategoryDataList.get(position).getWho() + ")";
        int descLength = mCategoryDataList.get(position).getDesc().length();
        int typeLength = via.length();

        SpannableStringBuilder sb = new SpannableStringBuilder(mCategoryDataList.get(position).getDesc() + via);
        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.GRAY);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan((int) dp2px(10));

        sb.setSpan(fcs, descLength, descLength + typeLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(ass, descLength, descLength + typeLength, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        holder.mTvCategory.setText(mCategoryDataList.get(position).getType());
        holder.mTvTitle.setText(sb);

    }

    @Override
    public int getItemCount() {
        if (mCategoryDataList != null) {
            return mCategoryDataList.size();
        }
        return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category)
        TextView mTvCategory;
        @BindView(R.id.tv_title)
        TextView mTvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            //跳转文章链接
            mTvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri uri = Uri.parse(mCategoryDataList.get(getLayoutPosition()).getUrl());
                    intent.setData(uri);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 是否显示Category
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
     * 首次更新List
     * @param list
     */
    public void refreshData(List<BaseData> list) {
        mCategoryDataList = list;
        notifyDataSetChanged();
    }

    /**
     * 刷新添加新的List
     * @param dateList
     */
    public void addFengMianData(List<BaseData> dateList) {
        mCategoryDataList.addAll(dateList);
        notifyDataSetChanged();
    }

    private float dp2px(int dp) {
        float density = mContext.getResources().getDisplayMetrics().density;
        return dp * density;
    }

}
