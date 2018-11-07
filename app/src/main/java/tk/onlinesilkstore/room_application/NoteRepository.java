package tk.onlinesilkstore.room_application;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allnotes;

    public NoteRepository (Application application)
    {
        NoteDatabase noteDatabase=NoteDatabase.getInstance(application);
        noteDao=noteDatabase.noteDao();
        allnotes=noteDao.get_All_Notes();
    }

    public  void insert(Note note)
    {
     new InsertNoteAsyncTask(noteDao).execute(note);
    }

    public  void update(Note note)
    {
        new updateNoteAsyncTask(noteDao).execute(note);
    }
    public  void delete(Note note)
    {
       new deleteNoteAsyncTask(noteDao).execute(note);
    }
    public  void delet_all()
    {
       new deleteAllAsyncTask(noteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes()
    {
        return allnotes;
    }

    private  static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.insert(notes[0]);
            return null;
        }
    }

    private  static class updateNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao noteDao;

        private updateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.update(notes[0]);
            return null;
        }
    }

    private  static class deleteNoteAsyncTask extends AsyncTask<Note, Void, Void>
    {

        private NoteDao noteDao;

       private deleteNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }


        @Override
        protected Void doInBackground(Note... notes) {

            noteDao.delete(notes[0]);
            return null;
        }
    }

    private  static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>
    {

        private NoteDao noteDao;

        private deleteAllAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.delete_all_notes();
            return null;
        }
    }
}
