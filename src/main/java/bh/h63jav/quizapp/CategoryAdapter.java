package bh.h63jav.quizapp;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.List;

class CategoryAdapter extends BaseAdapter {

    private final List<Category> categoryList;
    private Context context;

    CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return categoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View catView = inflater.inflate(R.layout.layout_category_item,null);
        ImageView img = (ImageView)catView.findViewById(R.id.imgCategory);
        String imgTitle = "categoryImgs/" + categoryList.get(i).getTitle().toLowerCase() + ".png";
        Log.d("IMAGELOADING","trying to load:"+imgTitle);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(imgTitle);
        Glide.with(catView)
                .load(storageReference)
                .apply(new RequestOptions().placeholder(R.drawable.question_mark).error(R.drawable.question_mark))
                .into(img);

        return catView;
    }

}
