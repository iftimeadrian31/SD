package beans;

public class StudentBean implements java.io.Serializable {
    private String nume = null;
    private String prenume = null;
    private int varsta = 0;
    private String oras=null;
    private String tara=null;

    public StudentBean() {
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }


    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getOras () {return oras ;}

    public void setOras(String oras){this.oras=oras;}

    public String getTara () {return tara ;}

    public void setTara(String tara){this.tara=tara;}
}