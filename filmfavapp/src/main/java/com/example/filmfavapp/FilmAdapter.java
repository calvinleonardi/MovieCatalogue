package com.example.filmfavapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {

    private Context context;
    private Cursor cursor;

    public FilmAdapter(Context context) {
        this.context = context;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_film, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(cursor.moveToPosition(position));
    }

    @Override
    public int getItemCount() {
        return cursor == null ? 0 : cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_name)
        TextView title;
        @BindView(R.id.txt_description)
        TextView desc;
        @BindView(R.id.movie_voteavg)
        TextView voteavg;
        @BindView(R.id.movie_votecount)
        TextView votecount;
        @BindView(R.id.img_photo)
        ImageView poster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(boolean position) {
            String url = Components.POSTER_BASE_URL + cursor.getString(cursor.getColumnIndexOrThrow(Components.POSTER_COLUMN));

            if (position) {
                title.setText(cursor.getString(cursor.getColumnIndexOrThrow(Components.TITLE_COLUMN)));
                desc.setText(cursor.getString(cursor.getColumnIndexOrThrow(Components.DESCRIPTION_COLUMN)));
                voteavg.setText(cursor.getString(cursor.getColumnIndexOrThrow(Components.VOTE_AVG)));
                votecount.setText(cursor.getString(cursor.getColumnIndexOrThrow(Components.VOTE_COUNT)));
                Glide.with(itemView.getContext())
                        .load(url)
                        .placeholder(R.color.colorAccent)
                        .dontAnimate()
                        .into(poster);
            }
        }
    }
}
