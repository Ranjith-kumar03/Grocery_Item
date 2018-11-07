package tk.onlinesilkstore.room_application;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class NoteAdapter extends ListAdapter<Note, NoteAdapter.View_holder> {

    private  onItem_PrivateClickListener listener;

    public NoteAdapter() {
        super(DIFF_CLLBACK);
    }
    private static final DiffUtil.ItemCallback<Note>  DIFF_CLLBACK=new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note note, @NonNull Note t1) {
            return note.getId()==t1.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note note, @NonNull Note t1) {
            return (note.getTitle().equals(t1.getTitle()) && note.getDescription().equals(t1.getDescription())
                    &&note.getPriority()==t1.getPriority());
        }
    };


    @NonNull
    @Override
    public View_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item,viewGroup,false);

        return new View_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull View_holder view_holder, int i) {
        Note  current_Note= getItem(i);
        view_holder.tv1.setText(String.valueOf(current_Note.getId()));
       /* view_holder.tv2.setText(String.valueOf(current_Note.getId()));
        view_holder.tv3.setText(String.valueOf(current_Note.getId()));*/

        view_holder.tv2.setText(current_Note.getDescription());
        view_holder.tv3.setText(current_Note.getTitle());

    }



  public Note getNoteAt(int i)
  {
      return getItem(i);
  }
    public class View_holder extends RecyclerView.ViewHolder{

       private TextView tv1,tv2,tv3;
        public View_holder(@NonNull View view) {
            super(view);
            tv1=view.findViewById(R.id.priority);
            tv2=view.findViewById(R.id.description);
            tv3=view.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int posistion=getAdapterPosition();
                    if(listener!=null && posistion !=RecyclerView.NO_POSITION) {
                        listener.oNItemCLickPrivate(getItem(posistion));
                    }
                }
            });
        }
    }

    public interface onItem_PrivateClickListener {
        void oNItemCLickPrivate(Note note);
    }
    public void setONItem_PrivateClickListener(onItem_PrivateClickListener listener)
    {
        this.listener=listener;
    }
}
