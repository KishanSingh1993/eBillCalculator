package in.ktechnos.ebillcalculator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CourseRVAdapter extends ListAdapter<MeterModal, CourseRVAdapter.ViewHolder> {

    // creating a variable for on item click listener.
    private OnItemClickListener listener;

    // creating a constructor class for our adapter class.
    CourseRVAdapter() {
        super(DIFF_CALLBACK);
    }

    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<MeterModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<MeterModal>() {
        @Override
        public boolean areItemsTheSame(MeterModal oldItem, MeterModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(MeterModal oldItem, MeterModal newItem) {
            // below line is to check the course name, description and course duration.
            return oldItem.getServiceNumber().equals(newItem.getServiceNumber()) &&
                    oldItem.getCurrentReadings().equals(newItem.getCurrentReadings());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is use to inflate our layout
        // file for each item of our recycler view.
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // below line of code is use to set data to
        // each item of our recycler view.
        MeterModal model = getCourseAt(position);
        holder.serviceNumber.setText(String.format("Service Number: %s", model.getServiceNumber()));
        holder.currentReading.setText(String.format("Last Reading: %s", model.getCurrentReadings()));

    }

    // creating a method to get course modal for a specific position.
    public MeterModal getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // view holder class to create a variable for each view.
        TextView serviceNumber, currentReading;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            serviceNumber = itemView.findViewById(R.id.idTVCourseName);
            currentReading = itemView.findViewById(R.id.idTVCourseDuration);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(MeterModal model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
