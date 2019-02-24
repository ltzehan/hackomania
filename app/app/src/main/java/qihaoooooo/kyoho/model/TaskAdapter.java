package qihaoooooo.kyoho.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import qihaoooooo.kyoho.MainActivity;
import qihaoooooo.kyoho.R;
import qihaoooooo.kyoho.view.SquareImageView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private final ArrayList<String> iconNames = new ArrayList<>(Arrays.asList("pills", "pyramid", "exercise", "hospital", "syringe"));
    private final ArrayList<Integer> iconResource = new ArrayList<>(Arrays.asList(R.drawable.pills, R.drawable.pyramid, R.drawable.exercise, R.drawable.hospital, R.drawable.syringe));

    private List<Task> tasks;

    public TaskAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int i) {
        final int pos = i;
        Task t = tasks.get(i);

        int index = iconNames.indexOf(t.getImageId());
        holder.taskIcon.setImageResource(iconResource.get(index));
        holder.taskDesc.setText(t.getTitle());
        holder.taskTimeLeft.setText("Daily task");
        holder.taskDoneCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task t = tasks.remove(pos);
                t.setCompleted(true);
                MainActivity.myDB.updateTask(t);

                int attack = t.getAttack();
                MainActivity.user.incrementAttack(attack);
                MainActivity.myDB.updateUser(MainActivity.user);
                MainActivity.attackBar.setBarProp(20, MainActivity.user.getAttack());
                System.out.println("USERRRR"+MainActivity.user.getAttack());

                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, tasks.size());
            }
        });
        holder.taskAttackValue.setText("" + t.getAttack());
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
        public SquareImageView taskDoneCheck;
        public TextView taskAttackValue;

        public TaskViewHolder(View v) {
            super(v);

            taskIcon = (SquareImageView) v.findViewById(R.id.taskIcon);
            taskDesc = (TextView) v.findViewById(R.id.taskDesc);
            taskTimeLeft = (TextView) v.findViewById(R.id.taskTimeLeft);
            taskDoneCheck = (SquareImageView) v.findViewById(R.id.taskDoneCheck);
            taskAttackValue = (TextView) v.findViewById(R.id.taskAttackTextView);
        }
    }

}
