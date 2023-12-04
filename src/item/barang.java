package item;
import javax.swing.JOptionPane;

public class barang implements DetailPrintable{
    private String nama_barang, jenis;
    private int  jumlah_barang, total,harga;
    private double discount;

    public barang (String nama_barang, int jumlah_barang,int harga,double discount, String jenis) {
        this.nama_barang=nama_barang;
        this.jumlah_barang=jumlah_barang;
        this.harga=harga;
        this.jenis=jenis;
        this.discount=discount;
    }
    public void setNamaBarang (String nama_barang) {
        this.nama_barang=nama_barang;
    }
    public void setJumlahBarang (int jumlah_barang) {
        this.jumlah_barang=jumlah_barang;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public void setHarga(int harga) {
        this.harga = harga;
    }
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public String getNama_barang() {
        return nama_barang;
    }
    public int getJumlah_barang() {
        return jumlah_barang;
    }
    public double getDiscount() {
        return discount;
    }
    public int getHarga() {
        return harga;
    }
    public String getJenis() {
        return jenis;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
    @Override
    public void printDetail() {
        JOptionPane.showMessageDialog(null,"Nama Barang : "+nama_barang);
        JOptionPane.showMessageDialog(null,"jumlah barang : "+jumlah_barang);
        JOptionPane.showMessageDialog(null,"Total: "+total);   
    }
}