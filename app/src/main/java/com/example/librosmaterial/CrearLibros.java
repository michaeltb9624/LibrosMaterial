package com.example.librosmaterial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CrearLibros extends AppCompatActivity {

    private EditText ISBN,titulo,autor,noPaginas,editorial;
    private ImageView foto;
    private Uri uri;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_libros);
        ISBN = findViewById(R.id.editTextISBN);
        titulo= findViewById(R.id.editTextTitulo);
        autor = findViewById(R.id.editTextAutor);
        noPaginas= findViewById(R.id.editTextNoPaginas);
        editorial = findViewById(R.id.editTextEditorial);
        foto = findViewById(R.id.imgFotoSel);
        storageReference = FirebaseStorage.getInstance().getReference();

    }


    public void guardar(View v){
        String isbn,tit, aut, id, noPag , edito;
        Libro l ;
        InputMethodManager imp;

        if (validar()) {

            isbn = ISBN.getText().toString();
            tit = titulo.getText().toString();
            aut = autor.getText().toString();
            noPag = noPaginas.getText().toString();
            edito = editorial.getText().toString();
            id = Datos.getId();
            imp = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            l = new Libro(id, isbn, tit, aut , noPag,edito);
            l.guardar();
            limpiar();
            subir_foto(id);
            imp.hideSoftInputFromWindow(ISBN.getWindowToken(), 0);
            Snackbar.make(v, R.string.mensaje_libro_guardado, Snackbar.LENGTH_LONG).show();
            uri=null;
        }

    }

    public void subir_foto(String id){

        StorageReference child = storageReference.child(id);
        UploadTask uploadTask = child.putFile(uri);
    }

    public void limpiar (View v){
        limpiar();
    }
    public void limpiar(){
        ISBN.setText("");
        titulo.setText("");
        autor.setText("");
        noPaginas.requestFocus();
        editorial.requestFocus();
        foto.setImageResource(android.R.drawable.ic_menu_gallery);
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(CrearLibros.this , MainActivity.class);
        startActivity(i);
    }


    public void seleccionarFoto(View v) {
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in,getString(R.string.seleccione_foto)),1);
    }

    protected void onActivityResult(int requestCode, int resultCode , Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == 1){
            uri = data.getData();
            if(uri != null){
                foto.setImageURI(uri);
            }
        }
    }

    public boolean validar(){
        if(ISBN.getText().toString().isEmpty()){
            ISBN.setError(getString(R.string.mensaje_error_ISBN));
            ISBN.requestFocus();
            return false;
        }

        if(titulo.getText().toString().isEmpty()){
            titulo.setError(getString(R.string.mensaje_error_titulo));
            titulo.requestFocus();
            return false;
        }

        if(autor.getText().toString().isEmpty()){
            autor.setError(getString(R.string.mensaje_error_autor));
            autor.requestFocus();
            return false;
        }

        if(noPaginas.getText().toString().isEmpty()){
            noPaginas.setError(getString(R.string.mensaje_error_no_paginas));
            noPaginas.requestFocus();
            return false;
        }

        if(editorial.getText().toString().isEmpty()){
            editorial.setError(getString(R.string.mensaje_error_editorial));
            editorial.requestFocus();
            return false;
        }

        if (uri == null){
            Snackbar.make((View)ISBN, R.string.mensaje_error_foto, Snackbar.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}