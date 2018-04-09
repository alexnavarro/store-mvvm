package br.com.alexandrenavarro.store_mvvm_live.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandrenavarro.store_mvvm_live.R;
import br.com.alexandrenavarro.store_mvvm_live.data.App;

public class StoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int VIEW_TYPE_EDITOR_CARD = 0;
    private static final int VIEW_TYPE_TOP_APPS_CARD = 1;

    private List<Integer> listRows = new ArrayList<>();
    private List<App> apps = new ArrayList<>();
    private static RecyclerView horizontalList;
    private Context mContext;

    public StoreAdapter(Context context){
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_EDITOR_CARD){
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
            return new SimpleViewHolder(view, false);
        }else if(viewType == VIEW_TYPE_TOP_APPS_CARD){
            final View view = LayoutInflater.from(mContext).inflate(R.layout.item_recycler, parent, false);
            return new SimpleViewHolder(view, true);
        }

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return VIEW_TYPE_EDITOR_CARD;
        }else if (position == 1){
            return VIEW_TYPE_TOP_APPS_CARD;
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SimpleViewHolder){
            String title = mContext.getString(R.string.editors_choice);
            ((SimpleViewHolder) holder).title.setText(title);
            HorizontalAdapter horizontalAdapter = ((SimpleViewHolder) holder).horizontalAdapter;
            horizontalAdapter.updateListApp(apps);
        }else {
            String title = mContext.getString(R.string.local_top_apps);
            ((SimpleViewHolder) holder).title.setText(title);
            HorizontalAdapter horizontalAdapter = ((SimpleViewHolder) holder).horizontalAdapter;
            horizontalAdapter.updateListApp(apps);
        }
    }

    @Override
    public int getItemCount() {
        return listRows.size();
    }

    public void updateListApp(List<App> appList) {
        this.apps.clear();
        this.apps.addAll(appList);
        this.listRows.add(1);
        this.listRows.add(1);
        notifyDataSetChanged();
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;
        private HorizontalAdapter horizontalAdapter;

        public SimpleViewHolder(View view, boolean localApp) {
            super(view);
            Context context = itemView.getContext();
            title = (TextView) view.findViewById(R.id.textView);
            horizontalList = (RecyclerView) itemView.findViewById(R.id.recyclerViewHorizontal);
            horizontalList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            horizontalAdapter = new HorizontalAdapter(localApp);
            horizontalList.setAdapter(horizontalAdapter);
        }
    }
}
