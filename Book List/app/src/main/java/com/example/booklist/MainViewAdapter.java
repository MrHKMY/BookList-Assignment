package com.example.booklist;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainViewAdapter extends RecyclerView.Adapter<MainViewAdapter.MainViewHolder> {
    ArrayList<String> titleList;
    ArrayList<String> colorList;
    private Context mContext;

    public MainViewAdapter(ArrayList<String> titleList, ArrayList<String> colorList, Context context) {
        this.titleList = titleList;
        this.colorList = colorList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_content, parent, false);

        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        final Context context = holder.textView.getContext();
        holder.textView.setText(titleList.get(position));

        AssetManager assetManager = context.getAssets();
        InputStream inputStream;

        //set image based on colorList from data.json
        try {
            inputStream = assetManager.open(colorList.get(position));
            Drawable d = Drawable.createFromStream(inputStream, null);
            holder.imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //launch alertDialog onClick
                detailDialog(titleList.get(position), colorList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public RelativeLayout parentLayout;

        public MainViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.bookImages);
            textView = view.findViewById(R.id.bookTitleID);
            parentLayout = view.findViewById(R.id.parentLayout);
        }
    }

    private void detailDialog(String bookTitle, String bookCover) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.dialog_layout, null);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);

        TextView title = view.findViewById(R.id.dialog_bookTitle);
        ImageView imageView = view.findViewById(R.id.dialog_bookImage);
        Button readButton = view.findViewById(R.id.dialog_ReadButton);
        Button shareButton = view.findViewById(R.id.dialog_ShareButton);
        Button deleteButton = view.findViewById(R.id.dialog_DeleteButton);
        
        title.setText(bookTitle);

        AssetManager assetManager = mContext.getAssets();
        InputStream inputStream;

        try {
            inputStream = assetManager.open(bookCover);
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Read", Toast.LENGTH_SHORT).show();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Share", Toast.LENGTH_SHORT).show();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Delete", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setView(view);
        alertDialog.show();
    }
}
