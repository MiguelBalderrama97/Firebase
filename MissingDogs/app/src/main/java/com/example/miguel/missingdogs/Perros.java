package com.example.miguel.missingdogs;

public class Perros {

    private int icon;
    private String nombre,raza,dueño;

//    DATOS POR SI ESTA DESAPECIDO
    private String colonia, fecha;
    private boolean estatus;

    public Perros(){}

    public Perros(int icon, String nombre, String raza, String dueño, String colonia, String fecha, boolean estatus) {
        this.icon = icon;
        this.nombre = nombre;
        this.raza = raza;
        this.dueño = dueño;
        this.colonia = colonia;
        this.fecha = fecha;
        this.estatus = estatus;
    }

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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
}
