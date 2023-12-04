package item;
import javax.swing.JOptionPane;

public class barangElektronik extends barang {
    private String jenis;
    
    public barangElektronik (String nama_barang, int jumlah_barang,int harga, String jenis, String kategori,double discount) {
        super(nama_barang, jumlah_barang,harga,discount,jenis);
        this.jenis=jenis;
    }
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    public String getJenis() {
        return jenis;
    }
    @Override
    public void printDetail() {
        // TODO Auto-generated method stub
        super.printDetail();
        JOptionPane.showMessageDialog(null, "Jenis Barang Eletronik "+jenis);
    }
}
