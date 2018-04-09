package br.com.alexandrenavarro.store_mvvm_live.view_model;

import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import br.com.alexandrenavarro.store_mvvm_live.data.App;
import br.com.alexandrenavarro.store_mvvm_live.transformation.RoundedCornersTransform;

public class ItemEditorViewModel extends BaseObservable{

    private App app;

    public ItemEditorViewModel(App app){
        this.app = app;
    }

    public String getGraphicURL(){
        return app.getGraphic();
    }

    public String getIconUrl(){
        return app.getIcon();
    }

    public String getAppName(){
        return app.getName();
    }

    public void setApp(App app) {
        this.app = app;
        notifyChange();
    }

    public String getRating(){
        return app.getRating() == 0 ? ".." : Double.toString(app.getRating());
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url){
        float density = imageView.getContext().getResources().getDisplayMetrics().density;
        int height = (int) (200.0 * density);
        int widthPixels = imageView.getContext().getResources().getDisplayMetrics().widthPixels;
        Picasso.with(imageView.getContext()).load(url)
                .resize(widthPixels/2, height)
                .centerInside()
                .onlyScaleDown()
                .into(imageView);
    }

    @BindingAdapter("imageIconUrl")
    public static void setIconImageUrl(ImageView imageView, String url){
        Picasso.with(imageView.getContext()).load(url)
                .centerCrop()
                .fit()
                .into(imageView);
    }
}