package br.com.alexandrenavarro.store_mvvm_live.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import br.com.alexandrenavarro.store_mvvm_live.R;
import br.com.alexandrenavarro.store_mvvm_live.data.App;
import br.com.alexandrenavarro.store_mvvm_live.databinding.ItemEditorsChoiceBinding;
import br.com.alexandrenavarro.store_mvvm_live.databinding.ItemTopAppsBinding;
import br.com.alexandrenavarro.store_mvvm_live.view_model.ItemEditorViewModel;

public class HorizontalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<App> apps = new ArrayList<>();
    private boolean localApp;

    public HorizontalAdapter(boolean localApp) {
        this.localApp = localApp;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (localApp) {
            ItemTopAppsBinding itemTopAppsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                    R.layout.item_top_apps, parent, false);
            return new AppLocalViewHolder(itemTopAppsBinding);
        }

        ItemEditorsChoiceBinding itemEditorsChoiceBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_editors_choice, parent, false);
        return new AppViewHolder(itemEditorsChoiceBinding);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AppViewHolder) {
            ((AppViewHolder) holder).bindApp(apps.get(position));
        } else {
            ((AppLocalViewHolder) holder).bindApp(apps.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public void updateListApp(List<App> apps) {
        this.apps.clear();

        if (localApp) {
            this.apps.addAll(apps);
        } else {
            for (App app : apps) {
                if (!TextUtils.isEmpty(app.getGraphic())) {
                    this.apps.add(app);
                }
            }
        }

        notifyDataSetChanged();
    }

    public static class AppViewHolder extends RecyclerView.ViewHolder {

        private ItemEditorsChoiceBinding itemEditorsChoiceBinding;

        public AppViewHolder(ItemEditorsChoiceBinding itemEditorsChoiceBinding) {
            super(itemEditorsChoiceBinding.container);
            this.itemEditorsChoiceBinding = itemEditorsChoiceBinding;
        }

        void bindApp(App app) {
            if (itemEditorsChoiceBinding.getViewModel() == null) {
                itemEditorsChoiceBinding.setViewModel(new ItemEditorViewModel(app));
            } else {
                itemEditorsChoiceBinding.getViewModel().setApp(app);
            }
        }
    }

    public static class AppLocalViewHolder extends RecyclerView.ViewHolder {

        private ItemTopAppsBinding itemTopAppsBinding;

        public AppLocalViewHolder(ItemTopAppsBinding itemTopAppsBinding) {
            super(itemTopAppsBinding.cardView);
            this.itemTopAppsBinding = itemTopAppsBinding;
        }

        void bindApp(App app) {
            if (itemTopAppsBinding.getViewModel() == null) {
                itemTopAppsBinding.setViewModel(new ItemEditorViewModel(app));
            } else {
                itemTopAppsBinding.getViewModel().setApp(app);
            }
        }
    }
}