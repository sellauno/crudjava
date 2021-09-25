package com.example.proyek1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyek1.R;
import com.example.proyek1.models.Student;

import java.util.List;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {

    private Context context;
    private List<Student> students;
    private OnStudentClickedListener listener;

    public StudentsAdapter(Context context, OnStudentClickedListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.namaText.setText(student.getNama());
        holder.nilaiText.setText(String.valueOf(student.getNilai()));

        holder.bind(student, listener);
    }

    @Override
    public int getItemCount() {
        return (students != null) ? students.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaText;
        TextView nilaiText;
//        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namaText = itemView.findViewById(R.id.tv_nama);
            nilaiText = itemView.findViewById(R.id.tv_nilai);
//            btnDelete = itemView.findViewById(R.id.btn_delete);
        }

        public void bind(final Student student, final OnStudentClickedListener listener) {
//            btnDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onBtnDeleteClicked(student);
//                }
//            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onStudentClicked(student);
                }
            });
        }
    }

    public interface OnStudentClickedListener {
        void onStudentClicked(Student student);
//        void onBtnDeleteClicked(Student student);
    }
}
