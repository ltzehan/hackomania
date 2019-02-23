package qihaoooooo.kyoho.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class TaskRecyclerView extends RecyclerView {

    private View noTaskTextView;
    private boolean noTasks;

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
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);

            if (noTasks) {
                noTaskTextView.setVisibility(View.GONE);
                TaskRecyclerView.this.setVisibility(View.VISIBLE);

                noTasks = false;
            }
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);

            Adapter<?> adapter = getAdapter();

            if (adapter != null && adapter.getItemCount() == 0) {
                noTaskTextView.setVisibility(View.VISIBLE);
                TaskRecyclerView.this.setVisibility(View.GONE);

                noTasks = true;
            }
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        adapter.registerAdapterDataObserver(noTaskObserver);

    }

    public void setNoTaskTextView(View noTaskTextView) {
        this.noTaskTextView = noTaskTextView;
    }

}
