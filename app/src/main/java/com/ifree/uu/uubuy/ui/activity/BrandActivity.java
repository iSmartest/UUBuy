package com.ifree.uu.uubuy.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.ifree.uu.uubuy.R;
import com.ifree.uu.uubuy.app.MyApplication;
import com.ifree.uu.uubuy.listener.RecyclerItemTouchListener;
import com.ifree.uu.uubuy.service.entity.CommodityListEntity;
import com.ifree.uu.uubuy.service.entity.SecondActivitiesEntity;
import com.ifree.uu.uubuy.service.presenter.CommodityPresenter;
import com.ifree.uu.uubuy.service.presenter.SecondListPresenter;
import com.ifree.uu.uubuy.service.view.CommodityListView;
import com.ifree.uu.uubuy.service.view.SecondListView;
import com.ifree.uu.uubuy.ui.adapter.BrandAdapter;
import com.ifree.uu.uubuy.ui.adapter.MarketOrStoreAdapter;
import com.ifree.uu.uubuy.ui.base.BaseActivity;
import com.ifree.uu.uubuy.uitls.GlideImageLoader;
import com.ifree.uu.uubuy.uitls.ToastUtils;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author：小火
 * Email：1403241630@qq.com
 * Created by 2018/9/14 0014
 * Description:4 车 5 品牌 6 教育
 */
public class BrandActivity extends BaseActivity implements View.OnClickListener {
    private CommodityPresenter mCommodityPresenter;
    @BindView(R.id.xr_brand)
    XRecyclerView xRecyclerView;
    private ImageView mIcon;
    private View headView;
    private int page = 1;
    private String fristActivitiesName;
    private String storeId;
    private String fristActivitiesType;
    private BrandAdapter mAdapter;
    private List<CommodityListEntity.DataBean.CommodityList> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_brand;
    }

    @Override
    protected void initView() {
        hideBack(6);
        setRightText("收藏");
        fristActivitiesName = getIntent().getStringExtra("fristActivitiesName");
        storeId = getIntent().getStringExtra("fristActivitiesId");
        fristActivitiesType = getIntent().getStringExtra("fristActivitiesType");
        mCommodityPresenter = new CommodityPresenter(context);
        setTitleText(fristActivitiesName);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(layoutManager);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulse);
        xRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        headView = LayoutInflater.from(context).inflate(R.layout.header_brand, null);
        mIcon = headView.findViewById(R.id.iv_activities_picture);
        mIcon.setOnClickListener(this);
        headView.findViewById(R.id.tv_brand_coupon).setOnClickListener(this);
        if (headView != null) xRecyclerView.addHeaderView(headView);
        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                loadData();
                xRecyclerView.refreshComplete();
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

        mAdapter = new BrandAdapter(context, mList);
        xRecyclerView.setAdapter(mAdapter);

        xRecyclerView.addOnItemTouchListener(new RecyclerItemTouchListener(xRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                int position = vh.getAdapterPosition() - 2;
                if (position < 0 | position >= mList.size()) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("commodityId",mList.get(position).getCommodityId());
                bundle.putString("type", mList.get(position).getType());
                MyApplication.openActivity(context, CommodityActivity.class, bundle);
            }
        });
    }


    @Override
    protected void loadData() {
        mCommodityPresenter.onCreate();
        mCommodityPresenter.attachView(mCommodityListView);
        mCommodityPresenter.getSearchCommodityListInfo(storeId, page, "1", "加载中...");
    }

    private CommodityListView mCommodityListView = new CommodityListView() {
        @Override
        public void onSuccess(CommodityListEntity mCommodityListEntity) {
            if (mCommodityListEntity.getResultCode().equals("1")) {
                ToastUtils.makeText(context, mCommodityListEntity.getMsg());
                return;
            }
            List<CommodityListEntity.DataBean.CommodityList> commodityLists = mCommodityListEntity.getData().getCommodityList();
            if (commodityLists != null && !commodityLists.isEmpty()) {
                mList.addAll(commodityLists);
                mAdapter.notifyDataSetChanged();
            }
            GlideImageLoader.imageLoader(context, mCommodityListEntity.getData().getStorePic(), mIcon);
        }

        @Override
        public void onError(String result) {
            ToastUtils.makeText(context, result);
        }
    };

    @OnClick({R.id.tv_base_rightText})
    public void onViewClicked() {
        ToastUtils.makeText(context, "收藏");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_activities_picture:
                MyApplication.openActivity(context, ActivitiesDetailsActivity.class);
                break;
            case R.id.tv_brand_coupon:
                MyApplication.openActivity(context, CouponCenterActivity.class);
                break;
        }
    }
}