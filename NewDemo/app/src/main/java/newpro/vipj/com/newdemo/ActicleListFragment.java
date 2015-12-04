
package newpro.vipj.com.newdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Objects;


public class ActicleListFragment extends BaseFragment {

    RecyclerView rv;
    MySwipeRefreshLayout mSwipeLayout;

    private ArrayList<Object> macticleMods;
    private MainRecyclerViewAdapter mActcleAdapter;
    private boolean isHasMore = true;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.recyclerview);
        mSwipeLayout = (MySwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        macticleMods = new ArrayList<Object>();
//        mSwipeLayout.setOnRefreshListener(ge);
//        setupRecyclerView();
        linearLayoutManager = new LinearLayoutManager(mActivity);
        rv.setLayoutManager(linearLayoutManager);
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        macticleMods.add(new Object());
        mActcleAdapter = new MainRecyclerViewAdapter(getActivity(),macticleMods);
        rv.setAdapter(mActcleAdapter);
//        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore() {
//                int size = macticleMods.size();
//                if (isHasMore && !mSwipeLayout.isRefreshing()) {
//                    getActicle(1, (int) macticleMods.get(size - 1).id);
//                }
//            }
//        });
    }

//    private void setupRecyclerView() {
//        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
//        if (isInDB()){
//            macticleMods =  (ArrayList<Object>)new Select().from(ActicleMod.class)
//                    .orderBy(false, ActicleMod$Table.ID).queryList();
//            mActcleAdapter = new MainRecyclerViewAdapter(mActivity, macticleMods);
//            rv.setAdapter(mActcleAdapter);
//        }else {
//            getActicle(0,0);//首次获取数据
//        }
//    }

//    private boolean isInDB() {
//        return new Select().from(ActicleMod.class).where()
//                .limit(1).queryList().size() > 0;
//    }
/*

    private void getActicle(final int page,int id){
        mSwipeLayout.setRefreshing(true);
        new HttpHelper.Builder().toUrl(URLUtils.ACTICLE)
                .addParams("page", page + "")
                .addParams("id", id + "")
                .executeGet(new AsyncHandlerTextBase() {
                    @Override public void onSuccess(int statusCode, Header[] headers, String result) {
                        super.onSuccess(statusCode, headers, result);

                        final ArrayList<ActicleMod> acticleMods = JsonUtil.jsonToList(result, ActicleMod.class);
                        mSwipeLayout.setRefreshing(false);

                        if (ListUtils.isEmpty(acticleMods)) {
                            if (page > 0){
                                showToast("全部加载完毕");
                                isHasMore = false;
                            }else if(page < 0){
                                showToast("小编正为你编辑更多文章");
                            }
                            return;
                        }

                        //page = 0 首次 <0 刷新 >0 加载更多
                        if (page == 0) {
                            macticleMods = acticleMods;
                            mActcleAdapter = new MainRecyclerViewAdapter(mActivity, macticleMods);
                            rv.setAdapter(mActcleAdapter);
                        } else if (page > 0) {
                            macticleMods.addAll(acticleMods);
                            mActcleAdapter.notifyItemInserted(macticleMods.size());
                        } else if (page < 0) {
                            for (ActicleMod mainMod : acticleMods) {
                                macticleMods.add(0, mainMod);
                            }
//                            mActcleAdapter.notifyItemRangeChanged(0,acticleMods.size());
                            mActcleAdapter.notifyDataSetChanged();
                        }

                        new WeakHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                ActicleMod acticleMod;
                                for (ActicleMod mainMod : acticleMods) {
                                    acticleMod = mainMod;
                                    acticleMod.save();
                                }
                            }
                        });
                    }

                    @Override public void onFailure(int statusCode, Header[] headers, String result, Throwable throwable) {
                        mSwipeLayout.setRefreshing(false);
                    }
                }).build();
    }

    @Override public void onRefresh() {
        new WeakHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (ListUtils.isEmpty(macticleMods)) {
                    getActicle(0, 0);//首次获取数据
                } else {
                    getActicle(-1, (int) macticleMods.get(0).id);
                }
            }
        }, 300);
    }
*/

    public void onEvent(String select) {
        linearLayoutManager.scrollToPosition(0);
    }

}
