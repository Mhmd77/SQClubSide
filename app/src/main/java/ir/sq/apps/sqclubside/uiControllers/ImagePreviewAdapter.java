package ir.sq.apps.sqclubside.uiControllers;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sq.apps.sqclubside.R;

/**
 * Created by Mohammad on 5/27/2018.
 */
public class ImagePreviewAdapter extends
        RecyclerView.Adapter<ImagePreviewAdapter.ImageViewHolder> {

    private static final String TAG = ImagePreviewAdapter.class.getSimpleName();

    private Context context;
    private List<Bitmap> list;
    private OnItemClickListener onItemClickListener;

    public ImagePreviewAdapter(Context context, List<Bitmap> list,
                               OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_preview_item_imageview)
        ImageView imagePreviewItemImageview;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }

        public void bind(final Bitmap model,
                         final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.image_preview_item, parent, false);
        ButterKnife.bind(this, view);

        ImageViewHolder viewHolder = new ImageViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Bitmap item = list.get(position);
        holder.bind(item, onItemClickListener);
        holder.imagePreviewItemImageview.setImageBitmap(item);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}