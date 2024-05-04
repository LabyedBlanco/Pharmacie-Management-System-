package example.Services;

public class Fournisseur {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String address;

    public Fournisseur(int id, String name, String email, String phone, String address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAdresse() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAdresse(String address) {
        this.address = address;
    }


}
