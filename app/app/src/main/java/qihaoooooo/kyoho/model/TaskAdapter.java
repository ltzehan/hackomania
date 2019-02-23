package qihaoooooo.kyoho.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import qihaoooooo.kyoho.R;
import qihaoooooo.kyoho.view.SquareImageView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int i) {
        Task t = tasks.get(i);

        // TODO integrate icon and time
        // holder.taskIcon.setImageIcon();
        holder.taskDesc.setText(t.getTitle());
        //holder.taskTimeLeft.setText(t.getTimeLeft());
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new TaskViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_view, viewGroup, false));
    }

    //
    //  Container for view
    //
    public static class TaskViewHolder extends RecyclerView.ViewHolder {

        public SquareImageView taskIcon;
        public TextView taskDesc;
        public TextView taskTimeLeft;

        public TaskViewHolder(View v) {
            super(v);

            taskIcon = (SquareImageView) v.findViewById(R.id.taskIcon);
            taskDesc = (TextView) v.findViewById(R.id.taskDesc);
            taskTimeLeft = (TextView) v.findViewById(R.id.taskTimeLeft);
        }
    }

}
