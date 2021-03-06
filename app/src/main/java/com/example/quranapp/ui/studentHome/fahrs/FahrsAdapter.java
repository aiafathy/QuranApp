package com.example.quranapp.ui.studentHome.fahrs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quranapp.R;
import com.example.quranapp.db.DbHandler;
import com.example.quranapp.db.Quran;

import java.util.List;

public class FahrsAdapter extends RecyclerView.Adapter<FahrsAdapter.MyViewHolder> {

    private List<String> suraList;
    private Context context;

    public FahrsAdapter(List<String> suraList, Context context) {
        this.suraList = suraList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fahrs_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {

        myViewHolder.suraNameTextView.setText(suraList.get(position));

        myViewHolder.suraNameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHandler dbHandler = new DbHandler(context);
                dbHandler.getWritableDatabase();
                List<Quran> quranList = dbHandler.getAllAyatInsideSura(String.valueOf(position + 1));
                int pageStart = quranList.get(0).getPage();
                int pageEnd = quranList.get(quranList.size() - 1).getPage();


                SuraFragment suraFragment = new SuraFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("page_start", pageStart);
                bundle.putInt("page_end", pageEnd);
                suraFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.add(R.id.fragment_container, suraFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return suraList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView suraNameTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            suraNameTextView = itemView.findViewById(R.id.sura_name);
        }
    }
}


