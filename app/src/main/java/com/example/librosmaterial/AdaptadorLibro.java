package com.example.librosmaterial;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdaptadorLibro extends RecyclerView.Adapter<AdaptadorLibro.LibroViewHolder> {

    private ArrayList<Libro> libros;
    public OnLibroClickListener clickListener;

    public AdaptadorLibro(ArrayList<Libro> libros , OnLibroClickListener clickListener) {
        this.libros = libros;
        this.clickListener = clickListener;
    }

    @Override
    public LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_libro,parent , false);

        return new LibroViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull LibroViewHolder holder, int position) {
        Libro l = libros.get(position);
        StorageReference storageReference;
        storageReference = FirebaseStorage.getInstance().getReference();

        storageReference.child(l.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.foto);
            }
        });
        holder.ISBN.setText(l.getISBN());
        holder.titulo.setText(l.getTitulo());
        holder.autor.setText(l.getAutor());
        holder.noPaginas.setText(l.getNoPaginas());
        holder.editorial.setText(l.getEditorial());

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onLibroClick(l);
            }
        });
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public static class LibroViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView foto;
        private TextView ISBN;
        private TextView titulo;
        private TextView autor;
        private TextView noPaginas;
        private TextView editorial;
        private View v;


        public LibroViewHolder( View itemView) {
            super(itemView);
            v = itemView;
            foto = v.findViewById(R.id.imgFotoLibro);
            ISBN = v.findViewById(R.id.lblISBN);
            titulo = v.findViewById(R.id.lblTitulo);
            autor = v.findViewById(R.id.lblAutor);
            noPaginas = v.findViewById(R.id.lblNumeroPaginas);
            editorial = v.findViewById(R.id.lblEditorial);
        }

    }

    public interface OnLibroClickListener{
        void onLibroClick(Libro l);
    }
}
