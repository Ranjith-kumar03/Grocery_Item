package tk.onlinesilkstore.room_application;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    public static final int EDIT_CODE_REQUEST = 2;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Grocery List");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton floatingActionButton = findViewById(R.id.floating_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteAdapter noteAdapter = new NoteAdapter();
        recyclerView.setAdapter(noteAdapter);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.get_allNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteAdapter.submitList(notes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                noteViewModel.delete(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Swiped Note got deleted", Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

        noteAdapter.setONItem_PrivateClickListener(new NoteAdapter.onItem_PrivateClickListener() {
            @Override
            public void oNItemCLickPrivate(Note note) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESC, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIOR, note.getPriority());

                startActivityForResult(intent, EDIT_CODE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            String Title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String Description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESC);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIOR, 1);

            Note note = new Note(Title, Description, priority);
            noteViewModel.insert(note);
            Toast.makeText(this, "Data Saved to Database", Toast.LENGTH_LONG).show();
        } else if (requestCode == EDIT_CODE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
            if(id==-1)
            {
                Toast.makeText(this, "Note cant be Updated", Toast.LENGTH_LONG).show();
                return;
            }
            String Title = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String Description = data.getStringExtra(AddEditNoteActivity.EXTRA_DESC);
            int priority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIOR, 1);
            Note note = new Note(Title, Description, priority);
            note.setId(id);
            noteViewModel.update(note);
            Toast.makeText(this, "Data Updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data cannot be Saved to Database", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all:
                noteViewModel.delete_all();
                Toast.makeText(MainActivity.this, "All notes delted", Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


}


