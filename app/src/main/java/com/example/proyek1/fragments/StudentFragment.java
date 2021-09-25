package com.example.proyek1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

import com.example.proyek1.Application;
import com.example.proyek1.Profile;
import com.example.proyek1.adapters.StudentsAdapter;
import com.example.proyek1.models.Student;
import com.example.proyek1.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StudentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StudentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentFragment extends Fragment implements StudentsAdapter.OnStudentClickedListener {

    private TextView studentText;
    private TextView bmrText;
    private RecyclerView caloriesView;
    private FloatingActionButton addButton;

    private StudentsAdapter studentsAdapter;

    private Profile profile;

    private OnFragmentInteractionListener mListener;

    public StudentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CaloryFragment.
     */
    public static StudentFragment newInstance() {
        StudentFragment fragment = new StudentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        profile = Application.provideProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        Context context = getActivity();

        studentText = view.findViewById(R.id.tv_student);
        caloriesView = view.findViewById(R.id.rv_students);
        addButton = view.findViewById(R.id.fab_add);

        // setup recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        studentsAdapter = new StudentsAdapter(context, this);
        caloriesView.setLayoutManager(layoutManager);
        caloriesView.setAdapter(studentsAdapter);

        if (mListener != null) {
            mListener.onStudentFragmentCreated(getView(), studentsAdapter, studentText);
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onAddStudentButtonClicked();
                }
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_student, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

    @Override
    public void onStudentClicked(Student student) {
        if (mListener != null) {
            mListener.onStudentClicked(student);
        }
    }

//    @Override
//    public void onBtnDeleteClicked(Student student) {
//        if (mListener != null) {
//            mListener.onBtnDeleteClicked(student);
//        }
//    }



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
        void onStudentFragmentCreated(final View view, final StudentsAdapter adapter, final TextView studentText);
        void onAddStudentButtonClicked();
        void onStudentClicked(Student student);
//        void onBtnDeleteClicked(Student student);
    }
}
