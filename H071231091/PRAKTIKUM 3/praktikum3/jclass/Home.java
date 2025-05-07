
// Import library yang diperlukan
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// Deklarasi kelas Home yang merupakan turunan dari AppCompatActivity
public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Memanggil metode onCreate dari superclass
        setContentView(R.layout.activity_home); // Mengatur layout untuk activity ini menggunakan file XML activity_home
        // ...existing code...
    }

    // Tambahkan metode atau logika lain di sini jika diperlukan
    // ...existing code...
}
