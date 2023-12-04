package user;
import item.Pemesanan;

public class User {
    private String username;
    private String password;
    private String tanggalLahir;
    private String jenisKelamin;
    private String telepon;
    private Pemesanan pemesanan; // Setiap User memiliki objek Pemesanan sendiri
    private String status;

     // Existing constructor
     public User(String username, String password, String telepon, String tanggalLahir, String jenisKelamin) {
        this.username = username;
        this.password = password;
        this.telepon = telepon;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        // this.pemesanan = new Pemesanan();
    }

    // Copy constructor
    public User(String username, String telepon, String tanggalLahir, String jenisKelamin) {
        this.username = username;
        this.telepon = telepon;
        this.tanggalLahir = tanggalLahir;
        this.jenisKelamin = jenisKelamin;
        // this.pemesanan = new Pemesanan();
    }
    // Constructor with status parameter
    public User(String status) {
        this.status = status;
    }
    public void copyPemesanan(Pemesanan otherPemesanan) {
        // If you want to copy the Pemesanan object, uncomment the line below
        // this.pemesanan = new Pemesanan(otherPemesanan);
    }

    protected boolean checkCredentials(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
    public String getTelepon() {
        return telepon;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getTanggalLahir() {
        return tanggalLahir;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }
    public Pemesanan getPemesanan() {
        return pemesanan;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
