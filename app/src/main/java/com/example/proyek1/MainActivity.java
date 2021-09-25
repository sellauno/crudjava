package com.example.proyek1;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import com.example.proyek1.adapters.StudentsAdapter;
import com.example.proyek1.fragments.StudentFragment;
import com.example.proyek1.fragments.ProfileFragment;
import com.example.proyek1.fragments.SaveStudentFragment;
import com.example.proyek1.generator.ServiceGenerator;
import com.example.proyek1.models.Student;
import com.example.proyek1.services.StudentService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements
		StudentFragment.OnFragmentInteractionListener,
		SaveStudentFragment.OnFragmentInteractionListener {

	private Profile profile;

	private StudentService studentService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		Profile profile = Application.provideProfile();

		studentService = ServiceGenerator.createService(StudentService.class);

        Fragment startFragment =
				StudentFragment.newInstance();
		changeFragment(startFragment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		switch (id) {
			case R.id.action_profile:
				changeFragment(ProfileFragment.newInstance());
				return true;
			case R.id.action_calories:
			    changeFragment(StudentFragment.newInstance());
				return true;
			case R.id.action_settings:
				return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void changeFragment(Fragment fragment) {
        changeFragment(fragment, false);
	}

	private void changeFragment(Fragment fragment, boolean addToBackStack) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment_container, fragment);
		if (addToBackStack)
			transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onStudentFragmentCreated(final View view, final StudentsAdapter adapter, final TextView studentText) {
		Call<List<Student>> studentsCall = studentService.getStudents();
		studentsCall.enqueue(new Callback<List<Student>>() {
			@Override
			public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
				List<Student> students = response.body();
				adapter.setStudents(students);

				// Tambahkan logic di baris ini untuk mengkalkulasi total student
			}

			@Override
			public void onFailure(Call<List<Student>> call, Throwable t) {
				Snackbar.make(view, "Oops!", Snackbar.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onAddStudentButtonClicked() {
		SaveStudentFragment fragment = new SaveStudentFragment();
		changeFragment(fragment);

	}

	@Override
	public void onStudentClicked(Student student) {
		SaveStudentFragment fragment = new SaveStudentFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable("student", student);
		fragment.setArguments(bundle);
		changeFragment(fragment);
	}

	@Override
	public void onSaveMenuClicked(final View view, Student student) {
		Call<Student> studentCall = (student.getId() == null)
				? studentService.addStudent(student.getNama(), student.getNilai())
				: studentService.editStudent(student.getId(),student.getNama(), student.getNilai());
		studentCall.enqueue(new Callback<Student>() {
			@Override
			public void onResponse(Call<Student> call, Response<Student> response) {
				Snackbar.make(view, "Save successfull", Snackbar.LENGTH_SHORT).show();
				changeFragment(StudentFragment.newInstance());
			}

			@Override
			public void onFailure(Call<Student> call, Throwable t) {
				Snackbar.make(view, "Error has occured!", Snackbar.LENGTH_SHORT).show();
			}
		});
	}

//	@Override
//	public void onBtnDeleteClicked(View view, Student student) {
//		Call<Student> studentCall = studentService.deleteStudent(student.getId());
//		studentCall.enqueue(new Callback<Student>() {
//			@Override
//			public void onResponse(Call<Student> call, Response<Student> response) {
//				Snackbar.make(view, "Delete successfull", Snackbar.LENGTH_SHORT).show();
//				changeFragment(StudentFragment.newInstance());
//			}
//
//			@Override
//			public void onFailure(Call<Student> call, Throwable t) {
//				Snackbar.make(view, "Error has occured!", Snackbar.LENGTH_SHORT).show();
//			}
//		});
//	}

}
