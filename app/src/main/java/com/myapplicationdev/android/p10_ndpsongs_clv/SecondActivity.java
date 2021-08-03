package com.myapplicationdev.android.p10_ndpsongs_clv;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.time.Year;
import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

	ListView lv;
    ArrayList<Song> songList;
	ArrayAdapter adapter;
	String moduleCode;
	int requestCode = 9;
    Button btn5Stars;
    CustomAdapter caSong;
    ArrayList<Year> years;
    ArrayAdapter<Year> aaspinner;
    Spinner spnYear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

        spnYear = findViewById(R.id.spinnerYear);
        years = new ArrayList<Year>();
        aaspinner = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, years);
        spnYear.setAdapter(aaspinner);

        setTitle(getTitle().toString() + " ~ " +  getResources().getText(R.string.title_activity_second));

		lv = (ListView) this.findViewById(R.id.lv);
        btn5Stars = (Button) this.findViewById(R.id.btnShow5Stars);

		DBHelper dbh = new DBHelper(this);
        songList = dbh.getAllSongs();
        dbh.close();

		/*adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, songList);
		lv.setAdapter(adapter);*/

        caSong=new CustomAdapter(this, R.layout.row, songList);
        lv.setAdapter(caSong);


        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int count = spnYear.getAdapter().getCount();

                String selectedYear = spnYear.getSelectedItem().toString();
                int year = Integer.parseInt(selectedYear);

                DBHelper dbh = new DBHelper(SecondActivity.this);
                int yr = year;

                for (int i = 0; i < count; i++) {
                    if (position == i) {
                        songList.clear();
                        songList.addAll(dbh.getAllSongsByYear(yr));
                        caSong.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("song", songList.get(position));
                startActivityForResult(i, requestCode);
            }
        });

        btn5Stars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(SecondActivity.this);
                songList.clear();
                songList.addAll(dbh.getAllSongsByStars(5));
                caSong.notifyDataSetChanged();
            }
        });
    }


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == this.requestCode && resultCode == RESULT_OK){
			DBHelper dbh = new DBHelper(this);
            songList.clear();
            songList.addAll(dbh.getAllSongs());
            dbh.close();
            caSong.notifyDataSetChanged();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


}
