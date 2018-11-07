package tk.onlinesilkstore.room_application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {
    private EditText title,desription;

    private NumberPicker numberPicker;
    public static final String EXTRA_ID="tk.onlinesilkstore.room_application.EXTRA_ID";
    public static final String EXTRA_TITLE="tk.onlinesilkstore.room_application.EXTRA_TITLE";
    public static final String EXTRA_DESC="tk.onlinesilkstore.room_application.EXTRA_DESC";
    public static final String EXTRA_PRIOR="tk.onlinesilkstore.room_application.EXTRA_PRIOR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title=findViewById(R.id.new_text_title);
        desription=findViewById(R.id.new_text_Description);
        numberPicker=findViewById(R.id.priority_id);
        numberPicker.setMaxValue(1000);
        numberPicker.setMinValue(1);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        Intent intent=getIntent();
        if(intent.hasExtra(EXTRA_ID))
        {
            setTitle("Edit Grocery List");
            title.setText(intent.getStringExtra(EXTRA_TITLE));
            desription.setText(intent.getStringExtra(EXTRA_DESC));
            numberPicker.setValue(intent.getIntExtra(EXTRA_PRIOR, 1));

        }else {


            setTitle("Add Grocery List");
        }
    }

    private void saveNote()
    {
        String mTitle=title.getText().toString().toString();
        String mDescription=desription.getText().toString().toString();
        int mPriority=numberPicker.getValue();

        if(mTitle.trim().length()==0 || mDescription.trim().length()==0 || mPriority==0)
        {
            Toast.makeText(this,"Please fill in the Information Required full",Toast.LENGTH_LONG).show();
            return;
        }

            Intent data = new Intent();
            data.putExtra(EXTRA_TITLE, mTitle);
            data.putExtra(EXTRA_DESC, mDescription);
            data.putExtra(EXTRA_PRIOR, mPriority);


            int id=getIntent().getIntExtra(EXTRA_ID,-1);
            if(id!=-1)
            {
                data.putExtra(EXTRA_ID,id);
            }

            setResult(RESULT_OK, data);
            finish();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.save:
                saveNote();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
