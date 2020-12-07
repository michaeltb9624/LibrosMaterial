package com.example.librosmaterial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetalleLibro extends AppCompatActivity {
    private TextView ISBN,titulo,autor,noPaginas,editorial;
    private ImageView foto;
    private  Bundle bundle;
    private Intent intent;
    private StorageReference storageReference;
    private String id;
    private Libro l ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_libro);
        bundle = getIntent().getBundleExtra("datos");

        ISBN= findViewById(R.id.lblISBNDetalle);
        titulo = findViewById(R.id.lblTituloDetalle);
        autor = findViewById(R.id.lblAutorDetalle);
        noPaginas = findViewById(R.id.lblNoPaginasDetalle);
        editorial = findViewById(R.id.lblEditorialDetalle);
        foto = findViewById(R.id.imgFotoDetalle);

        storageReference = FirebaseStorage.getInstance().getReference();

        ISBN.setText(bundle.getString("ISBN"));
        titulo.setText(bundle.getString("titulo"));
        autor.setText(bundle.getString("autor"));
        noPaginas.setText(bundle.getString("noPaginas"));
        editorial.setText(bundle.getString("editorial"));
        id = bundle.getString("id");
        l = new Libro(id, ISBN.getText().toString() ,titulo.getText().toString(),autor.getText().toString(), noPaginas.getText().toString(),editorial.getText().toString());
        Log.e("id" ,  id);

        storageReference.child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto);
            }
        });
    }


    public void eliminar (View v){
        Log.e("entre", "entre a elimnar");
        String positivo, negativo;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Persona");
        builder.setMessage(R.string.mensaje_eliminar);
        positivo = getString(R.string.mensaje_positivo);
        negativo = getString(R.string.mensaje_negativo);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                l.eliminar();
                onBackPressed();
            }
        });

        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public  void onBackPressed(){
        finish();
        Intent intent = new Intent(DetalleLibro.this , MainActivity.class);
        startActivity(intent);
    }
}