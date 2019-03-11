package com.example.miguel.prototipo.Activities.Models;

public class Perro {

    private int icon, edad, img1, img2, im3;
    private String id, nombre,raza,dueño;

    //    DATOS POR SI ESTA DESAPECIDO
    private String colonia, fecha;
    private boolean estatus;

    public Perro(){}

    public Perro(int icon, int edad, String nombre, String raza, String dueño, boolean estatus) {
        this.icon = icon;
        this.edad = edad;
        this.nombre = nombre;
        this.raza = raza;
        this.dueño = dueño;
        this.estatus = estatus;
        this.colonia = "";
        this.fecha = "";
        this.id = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImg1() {
        return img1;
    }

    public void setImg1(int img1) {
        this.img1 = img1;
    }

    public int getImg2() {
        return img2;
    }

    public void setImg2(int img2) {
        this.img2 = img2;
    }

    public int getIm3() {
        return im3;
    }

    public void setIm3(int im3) {
        this.im3 = im3;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getDueño() {
        return dueño;
    }

    public void setDueño(String dueño) {
        this.dueño = dueño;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }
}
