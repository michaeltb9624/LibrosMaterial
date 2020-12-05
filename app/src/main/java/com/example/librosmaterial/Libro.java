package com.example.librosmaterial;

public class Libro {
    private String id;
    private String ISBN;
    private String titulo;
    private String autor;
    private String noPaginas;
    private String editorial;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getNoPaginas() {
        return noPaginas;
    }

    public void setNoPaginas(String noPaginas) {
        this.noPaginas = noPaginas;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
}
