package com.example.librosmaterial;

import java.util.ArrayList;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class Datos {
    private static String bd = "Libros";

    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static ArrayList<Libro> libros = new ArrayList<>();

    private static StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    public static String getId(){
        return  databaseReference.push().getKey();
    }

    public static void setLibros(ArrayList<Libro> libros) {
        Datos.libros = libros;
    }

    public static void guardar (Libro l ){
        databaseReference.child(bd).child(l.getId()).setValue(l);
    }


    public static void eliminar(Libro l){
        databaseReference.child(bd).child(l.getId()).removeValue();
        storageReference.child(l.getId()).delete();
    }

}
