package item;
import javax.swing.JOptionPane;

public class barangPakaian extends barang {
    protected String ukuran;
    
    public barangPakaian(String nama_barang, int jumlah_barang, int harga, String ukuran, double discount, String jenis) {
        super(nama_barang, jumlah_barang, harga, discount,jenis);
        this.ukuran = ukuran;
    }
    public void setUkuran(String ukuran) {
        this.ukuran = ukuran;
    }
    public String getUkuran() {
        return ukuran;
    }
    @Override
    public void printDetail() {
        // TODO Auto-generated method stub
        super.printDetail();
        JOptionPane.showMessageDialog(null, "Ukuran Pakain : "+ukuran);
    }
}
