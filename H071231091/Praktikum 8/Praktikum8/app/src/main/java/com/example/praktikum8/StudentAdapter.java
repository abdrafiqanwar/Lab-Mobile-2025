package com.example.praktikum8;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    // Daftar data mahasiswa yang akan ditampilkan
    private final ArrayList<Student> students = new ArrayList<>();
    private final Activity activity;

    public StudentAdapter(Activity activity) {
        this.activity = activity;
    }
    public void setStudents(ArrayList<Student> students) {
        this.students.clear();
        this.students.addAll(students);     // Tambahkan data baru
        notifyDataSetChanged();             // Beri tahu adapter agar RecyclerView diperbarui
    }


    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);

        return new StudentViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // Ambil data Student
        holder.bind(students.get(position));
    }


    @Override
    public int getItemCount() {
        return students.size(); // Jumlah mahasiswa
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {
        final TextView tvName, tvNim, tvTimestamp;
        final CardView cardView;

        StudentViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvNim = itemView.findViewById(R.id.tv_item_nim);
            tvTimestamp = itemView.findViewById(R.id.tv_item_timestamp);
            cardView = itemView.findViewById(R.id.card_view);          // CardView untuk keseluruhan item
        }


        void bind(Student student) {
            tvName.setText(student.getName());  // Tampilkan nama
            tvNim.setText(student.getNim());

            // Menampilkan informasi waktu dibuat atau diperbarui
            if (student.getUpdatedAt() != null && !student.getUpdatedAt().isEmpty()) {
                tvTimestamp.setText("Updated at " + student.getUpdatedAt());
            } else if (student.getCreatedAt() != null && !student.getCreatedAt().isEmpty()) {
                tvTimestamp.setText("Created at " + student.getCreatedAt());
            } else {
                tvTimestamp.setVisibility(View.GONE);
            }

            //form
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(activity, FormActivity.class);
                intent.putExtra(FormActivity.EXTRA_STUDENT, student);
                activity.startActivityForResult(intent, FormActivity.REQUEST_UPDATE); // Buka form untuk edit
            });
        }
    }
}
