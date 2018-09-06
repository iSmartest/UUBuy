package com.ifree.uu.uubuy.ui.activity;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.ifree.uu.uubuy.R;
import com.ifree.uu.uubuy.app.MyApplication;
import com.ifree.uu.uubuy.listener.RecyclerItemTouchListener;
import com.ifree.uu.uubuy.ui.adapter.MarketOrStoreAdapter;
import com.ifree.uu.uubuy.ui.adapter.StoreAdapter;
import com.ifree.uu.uubuy.ui.base.BaseActivity;
import com.ifree.uu.uubuy.uitls.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: 小火
 * Email:1403241630@qq.com
 * Created by 2018/8/30.
 * Description:
 */
public class StoreActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.xr_store)
    XRecyclerView xRecyclerView;
    private View headView;
    private int page = 1;
    private StoreAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initView() {

        hideBack(5);
        setTitleText("特步店");
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        headView = LayoutInflater.from(context).inflate(R.layout.header_store, null);
        headView.findViewById(R.id.ll_store_activities).setOnClickListener(this);
        headView.findViewById(R.id.tv_store_share).setOnClickListener(this);
        if (headView != null) xRecyclerView.addHeaderView(headView);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        page = 1;
//                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                        loadData();
                        xRecyclerView.refreshComplete();
                    }

                }, 2000);
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData();
                xRecyclerView.loadMoreComplete();
                mAdapter.notifyDataSetChanged();
                xRecyclerView.setNoMore(true);
            }
        });

//        mAdapter = new MarketOrStoreAdapter(context, mList);
        xRecyclerView.setAdapter(mAdapter);
        xRecyclerView.setRefreshing(true);

        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 1;
//                MyApplication.openActivity(context,CommodityActivity.class);
                MyApplication.openActivity(context, StoreActivity.class);
            }
        });
    }

    @OnClick({R.id.tv_base_rightText})
    public void onViewClicked() {
        ToastUtils.makeText(context, "收藏");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_store_activities:
                MyApplication.openActivity(context, ActivitiesDetailsActivity.class);
                break;
            case R.id.tv_store_share:
                ToastUtils.makeText(context, "你点击分享");
                break;
        }
    }

}