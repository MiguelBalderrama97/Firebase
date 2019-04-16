package com.example.miguel.prototipo.Activities.Models;

import android.graphics.Bitmap;

public class Perro {

    private int icon, img1, img2, im3, edad, spinner;
    private String id, nombre,raza,dueño;
    private boolean sexo; //TRUE -> HEMBRA

    //    DATOS POR SI ESTA DESAPARECIDO
    private String colonia, fecha;
    private boolean estatus;

    public Perro(){}

    public Perro(int icon, int edad, int img1, int img2, int im3, String nombre, String raza, String dueño, boolean sexo, int spinner) {
        this.icon = icon;
        this.edad = edad;
        this.img1 = img1;
        this.img2 = img2;
        this.im3 = im3;
        this.nombre = nombre;
        this.raza = raza;
        this.dueño = dueño;
        this.sexo = sexo;
        this.estatus = false;
        this.colonia = "";
        this.fecha = "";
        this.spinner = spinner;
    }

    public int getSpinner() {
        return spinner;
    }

    public void setSpinner(int spinner) {
        this.spinner = spinner;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
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
