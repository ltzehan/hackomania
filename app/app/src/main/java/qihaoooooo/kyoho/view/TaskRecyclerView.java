package qihaoooooo.kyoho.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class TaskRecyclerView extends RecyclerView {

    private View noTaskTextView;

    public TaskRecyclerView(Context context) {
        super(context);
    }

    public TaskRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AdapterDataObserver noTaskObserver = new AdapterDataObserver() {

        @Override
        public void onChanged() {
            Adapter<?> adapter =  getAdapter();
            if(adapter != null && noTaskTextView != null) {
                if(adapter.getItemCount() == 0) {
                    noTaskTextView.setVisibility(View.VISIBLE);
                    TaskRecyclerView.this.setVisibility(View.GONE);
                }
                else {
                    noTaskTextView.setVisibility(View.GONE);
                    TaskRecyclerView.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };



    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if(adapter != null) {
            adapter.registerAdapterDataObserver(noTaskObserver);
        }

        noTaskObserver.onChanged();
    }

    public void setNoTaskTextView(View noTaskTextView) {
        this.noTaskTextView = noTaskTextView;
    }

}
