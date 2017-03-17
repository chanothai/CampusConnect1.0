package gallery.zicure.company.com.gallery.view;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by 4GRYZ52 on 3/13/2017.
 */

public class StateGalleryRecyclerView extends RecyclerView {
    private Adapter<ViewHolder> galleryAdapter;
    public StateGalleryRecyclerView(Context context) {
        super(context);
    }

    public StateGalleryRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StateGalleryRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.adapter = galleryAdapter;
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
//        if (!(state instanceof SavedState)){
//            super.onRestoreInstanceState(state);
//            return;
//        }
//        SavedState ss = (SavedState) state;
//        super.onRestoreInstanceState(ss.getSuperState());
//        this.setAdapter(ss.adapter);
        super.onRestoreInstanceState(state);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        galleryAdapter = adapter;
    }

    private static class SavedState extends BaseSavedState {
        Adapter<ViewHolder> adapter;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.adapter = in.readParcelable(Adapter.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeValue(adapter);
        }

        public static final Creator<StateGalleryRecyclerView.SavedState> CREATOR = new Creator<StateGalleryRecyclerView.SavedState>() {
            public StateGalleryRecyclerView.SavedState createFromParcel(Parcel in) {
                return new StateGalleryRecyclerView.SavedState(in);
            }

            public StateGalleryRecyclerView.SavedState[] newArray(int size) {
                return new StateGalleryRecyclerView.SavedState[size];
            }
        };
    }
}
