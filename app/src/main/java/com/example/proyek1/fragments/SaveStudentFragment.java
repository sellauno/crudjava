package com.example.proyek1.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyek1.services.StudentService;
import com.google.android.material.textfield.TextInputEditText;

import com.example.proyek1.Constant;
import com.example.proyek1.R;
import com.example.proyek1.models.Student;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SaveStudentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SaveStudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaveStudentFragment extends Fragment {

    private Student student;

    private TextInputEditText namaText;
    private TextInputEditText nilaiText;
    private Button deleteButton;
    private StudentService nilaiService;


    private OnFragmentInteractionListener mListener;

    public SaveStudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SaveCaloryFragment.
     */
    public static SaveStudentFragment newInstance(Student student) {
        SaveStudentFragment fragment = new SaveStudentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("student", student);


        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_save_student, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        namaText = view.findViewById(R.id.input_nama);
        nilaiText = view.findViewById(R.id.input_nilai);
        deleteButton = view.findViewById(R.id.btn_delete);

        Bundle bundle = getArguments();
        if (bundle != null) {
            student = bundle.getParcelable("student");
            namaText.setText(student.getNama());
            nilaiText.setText(String.valueOf(student.getNilai()));
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            if (mListener != null) {
                if (student == null) {
                    student = new Student();
                }
                // TODO: Ambil nilai calory yang didapatkan dari tampilan
                student.setNilai(Integer.parseInt(nilaiText.getText().toString()));
                student.setNama(namaText.getText().toString());

//                mListener.onBtnDeleteClicked(getView(), student);
                mListener.onSaveMenuClicked(getView(), student);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onSaveMenuClicked(View view, Student student);
//        void onBtnDeleteClicked(View view, Student student);
    }
}
