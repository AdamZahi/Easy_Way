package tn.esprit.models.trajet;

public class Ligne {
    private int id;
    private String dep,arr,type;

    public Ligne() {}
    public Ligne(int id, String dep, String arr, String type) {
        this.id = id;
        this.dep = dep;
        this.arr = arr;
        this.type = type;
    }
    public Ligne(String dep, String arr, String type) {
        this.dep = dep;
        this.arr = arr;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getArr() {
        return arr;
    }

    public void setArr(String arr) {
        this.arr = arr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Ligne{" +
                "dep='" + dep + '\'' +
                ", arr='" + arr + '\'' +
                ", type='" + type + '\'' +
                '}'+"\n";
    }
}
