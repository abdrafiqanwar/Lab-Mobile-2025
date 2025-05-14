package com.example.tp04fragment.Data;

import com.example.tp04fragment.Models.Book;
import com.example.tp04fragment.R;

import java.util.ArrayList;

public class DataBook {
    public static ArrayList<Book> books = generateDummyBooks();

    private static ArrayList<Book> generateDummyBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(R.drawable.laut_bercerita,
                "Laut Bercerita",
                "Leila S. Chudori",
                2017,
                "Sebuah kisah kehilangan, perlawanan, dan harapan.",
                "Drama",
                "Laut Bercerita mengisahkan tentang Biru Laut, seorang aktivis yang menghilang pada masa penculikan mahasiswa 1998. Novel ini menyentuh tema keluarga, perjuangan, dan kekuasaan.",
                379,
                false,
                4.5
        ));

        books.add(new Book(R.drawable.gunung,
                "3726 MDPL",
                "Nurwina Sari",
                2024,
                "Bumi adalah tempat yang luas sekali. Manusia bertemu dengan manusia-manusia yang banyak, berbagi kisah, kagum dan sayang.",
                "Slice of Life",
                "Rangga Raja, mahasiswa semester akhir Fakultas Kehutanan di Malang, telah memendam perasaan selama empat tahun kepada adik tingkatnya, Andini Hangura. Setiap tahun, ia rutin mengirimkan pesan ulang tahun kepada Andini, meski tak pernah dibalas. Hingga suatu hari, saat Rangga mengirimkan foto dari puncak Gunung Rinjani, Andini akhirnya membalas pesannya. Balasan itu menjadi awal dari kedekatan mereka. \n Namun, hubungan mereka diuji oleh masa lalu Andini dan ketidaksetujuan orang tuanya terhadap hubungan tersebut. Kisah ini menggambarkan perjuangan cinta, ketulusan, dan pencarian jati diri, dengan latar belakang keindahan alam Indonesia, khususnya Gunung Rinjani yang memiliki ketinggian 3.726 meter di atas permukaan laut.",
                274,
                false,
                5));

        books.add(new Book(R.drawable.meong,
                "The Kamogawa Food Detectives",
                "Hisashi Kashiwai",
                2014,
                "Di sebuah gang tenang di Kyoto, terdapat restoran unik bernama Kamogawa Diner yang dikelola oleh pasangan ayah-anak, Nagare dan Koishi Kamogawa.",
                "Slice of Life",
                "Buku ini terdiri dari enam cerita pendek yang masing-masing berfokus pada seorang pelanggan yang ingin mencicipi kembali hidangan spesial dari masa lalu mereka. Koishi, sang putri, mewawancarai pelanggan untuk menggali detail tentang hidangan tersebut, sementara Nagare, ayahnya yang juga mantan detektif polisi, melakukan penyelidikan untuk menemukan resep dan bahan-bahan yang tepat. Setiap cerita menggambarkan bagaimana makanan dapat menjadi jembatan antara masa lalu dan masa kini, serta menyembuhkan luka emosional.",
                208,
                false,
                4.8));

        books.add(new Book(R.drawable.convience,
                "The Convenience Store by the Sea",
                "Joanne Harris",
                2019,
                "Di sebuah desa kecil di pinggir laut, toko serba ada kecil menjadi saksi bisu rahasia, harapan, dan luka lama para penduduknya.",
                "Drama",
                "oko kecil di pinggir laut itu seolah tak berubah selama bertahun-tahun. Namun di balik rak-rak berdebu dan barang-barang sederhana, penjaga toko menyimpan luka masa lalu yang tak kunjung sembuh. Ketika pendatang baru datang ke desa dan mulai sering berbelanja di sana, rahasia lama mulai terkuak. Toko itu bukan sekadar tempat berbelanja: ia adalah tempat di mana harapan bertemu dengan kesepian, dan di mana setiap orang harus memilih apakah mereka ingin melanjutkan hidup atau terus terjebak di masa lalu.",
                320,
                false,
                3.8));

        books.add(new Book(R.drawable.toko,
                "Keajaiban Toko Kelontong Namiya",
                "Keigo Higashino",
                2012,
                "Tiga remaja menemukan toko kelontong misterius yang bisa menjawab pertanyaan mereka.",
                "Fantasy",
                "Toko kelontong Namiya dulunya terkenal sebagai tempat orang-orang datang untuk meminta nasihat, dengan menulis surat yang dimasukkan lewat kotak surat di depan toko. Meski pemiliknya sudah lama tiada, pada malam itu, tiga pencuri yang bersembunyi di dalam toko mendapati surat misterius dari masa lalu. Mereka pun memutuskan untuk menjawab surat itu. Setiap surat yang mereka balas menguak kisah-kisah kehidupan yang penuh harapan, kegagalan, dan penebusan dosa. Perlahan, mereka menyadari bahwa mereka sedang terlibat dalam sesuatu yang jauh lebih besar dan mengubah cara mereka memandang hidup.",
                384,
                false,
                4.3));

        books.add(new Book(R.drawable.bookshop,
                "Welcome to the Hyunam-Dong Bookshop",
                "Hwang Bo-reum",
                2022,
                "Yeongju telah menjalani hidup sesuai harapan masyarakat: pendidikan tinggi, pernikahan yang layak, dan karier mapan. Namun, semua itu runtuh.",
                "Slice of Life",
                "Setelah meninggalkan pekerjaan dan pernikahannya yang tidak memuaskan, Yeongju membuka toko buku kecil di lingkungan tenang Hyunam-Dong. Awalnya, ia menghadapi tantangan emosional dan kesulitan dalam menjalankan bisnis. Namun, dengan bantuan Minjun, seorang barista muda yang penuh semangat, dan pelanggan-pelanggan setia seperti Jimi, Jungsuh, dan Mincheol, toko buku tersebut berkembang menjadi pusat komunitas yang hangat. Melalui interaksi sehari-hari, mereka semua menemukan makna baru dalam hidup dan kekuatan penyembuhan dari buku-buku.",
                320,
                false,
                4));

        books.add(new Book(R.drawable.lonliness,
                "Loneliness Is My Best Friend",
                "Alvi Syahrin",
                2022,
                "Mengajak untuk berdamai dengan kesepian dan menjadikannya sebagai teman.",
                "Sci-Fi",
                "Dalam buku ini, Alvi Syahrin membahas berbagai aspek kesepian yang sering dialami banyak orang, seperti merasa tidak memiliki teman, menjadi opsi kedua dalam hidup orang lain, atau merasa tidak dicintai. Dengan pendekatan yang hangat dan empatik, penulis mengajak pembaca untuk menerima kesepian sebagai bagian dari kehidupan dan menjadikannya sebagai kesempatan untuk bertumbuh dan memperkuat hubungan dengan diri sendiri dan Tuhan.",
                306,
                false,
                4.2));

        books.add(new Book(R.drawable.insecurity,
                "Insecurity Is My Middle Name",
                "Alvi Syahrin",
                2021,
                "Buku ini mengajak pembaca untuk berdamai dengan perasaan tidak aman (insecure) yang sering kali menghambat pertumbuhan pribadi.",
                "Slice of Life",
                "Dalam buku ini, Alvi Syahrin membahas berbagai aspek perasaan insecure yang umum dialami banyak orang, seperti merasa tidak cukup baik, takut gagal, atau merasa tertinggal dari orang lain. Dengan gaya penulisan yang ringan dan personal, penulis mengajak pembaca untuk memahami bahwa perasaan tersebut adalah hal yang wajar dan dapat menjadi pendorong untuk berkembang. Buku ini juga memberikan panduan praktis untuk mengelola dan mengatasi perasaan insecure, serta menekankan pentingnya menerima diri sendiri dan terus bertumbuh.",
                264,
                false,
                4.2));

        books.add(new Book(R.drawable.overthinking,
                "Overthinking Is My Hobby, And I Hate It",
                "Alvi Syahrin",
                2023,
                "Buku ini mengajak pembaca untuk memahami dan mengelola kebiasaan overthinking yang sering kali menghambat kebahagiaan dan produktivitas.",
                "Slice of Life",
                "Dalam buku ini, Alvi Syahrin membahas berbagai aspek overthinking yang umum dialami banyak orang, seperti rasa cemas berlebihan, ketakutan akan masa depan, dan keraguan terhadap diri sendiri. Dengan gaya penulisan yang ringan dan personal, penulis mengajak pembaca untuk memahami bahwa overthinking adalah hal yang wajar namun perlu dikelola. Buku ini juga memberikan panduan praktis untuk mengatasi overthinking dan menekankan pentingnya menerima diri sendiri.",
                268,
                false,
                4.3));

        books.add(new Book(R.drawable.things,
                "The Things You Can See Only When You Slow Down",
                "Haemin Sunim",
                2012,
                "Buku ini merupakan panduan mindfulness yang mengajarkan pentingnya memperlambat langkah dalam kehidupan yang serba cepat.",
                "Slice of Life",
                "Dalam dunia yang bergerak cepat, kita sering kali merasa terjebak dalam kesibukan tanpa akhir. Haemin Sunim mengajak pembaca untuk merenung dan menemukan kedamaian dengan memperlambat langkah. Melalui delapan bab yang mencakup topik seperti istirahat, mindfulness, gairah, hubungan, cinta, kehidupan, masa depan, dan spiritualitas, penulis membagikan wawasan dan nasihat yang sederhana namun mendalam. Buku ini diperkaya dengan lebih dari dua puluh ilustrasi penuh warna yang menenangkan, mendorong pembaca untuk menyadari bahwa ketika kita memperlambat langkah, dunia pun ikut melambat bersama kita.",
                288,
                false,
                4.5));

        books.add(new Book(R.drawable.bumi,
                "Bumi",
                "Tere Liye",
                2014,
                "Petualangan remaja dengan kekuatan luar biasa.",
                "Fantasy",
                "Raib, Seli, dan Ali menjelajahi dunia paralel penuh misteri dan teknologi.",
                440,
                false,
                4.7
        ));

        books.add(new Book(R.drawable.midnight,
                "The Midnight Library",
                "Matt Haig",
                2020,
                "Di antara kehidupan dan kematian, terdapat sebuah perpustakaan. Di dalamnya, rak-rak buku membentang tanpa akhir.",
                "Fantasy",
                "Nora Seed, seorang wanita berusia 35 tahun, merasa hidupnya penuh dengan penyesalan dan kekecewaan. Setelah serangkaian kejadian buruk, termasuk kehilangan pekerjaan dan kematian kucing kesayangannya, Nora memutuskan untuk mengakhiri hidupnya. Namun, alih-alih mati, ia terbangun di Perpustakaan Tengah Malam—sebuah tempat antara hidup dan mati. Di sana, setiap buku mewakili kehidupan alternatif yang bisa ia jalani jika membuat pilihan berbeda. Dengan bimbingan pustakawan lamanya, Mrs. Elm, Nora menjelajahi berbagai versi kehidupannya, mencari tahu apa yang benar-benar membuat hidup layak dijalani.",
                304,
                false,
                4.2));

        books.add(new Book(R.drawable.cantik,
                "Cantik Itu Luka",
                "Eka Kurniawan",
                2002,
                "Kisah cinta, sejarah, dan tragedi di Indonesia.",
                "Drama",
                "Cantik Itu Luka mengisahkan perjalanan hidup Dewi Ayu, seorang wanita cantik yang terjebak dalam sejarah kelam Indonesia.",
                500,
                false,
                4.7));

        books.add(new Book(R.drawable.museum,
                "The Museum of Lost and Found",
                "Leila Sales",
                2023,
                "Vanessa Lepp, seorang siswi kelas enam dari Ohio, merasa bingung dan terluka setelah sahabatnya, Bailey Dominguez, tiba-tiba menjauh tanpa alasan yang jelas.",
                "Fantasy",
                "Vanessa tidak mengerti mengapa Bailey, sahabatnya sejak lama, tiba-tiba menjauh. Dalam upaya mencari jawaban, Vanessa menemukan sebuah museum yang terlantar dan memutuskan untuk membuat pameran tentang persahabatannya dengan Bailey. Melalui proses ini, ia mulai menyadari dinamika kompleks dalam hubungan mereka dan bagaimana perubahan adalah bagian alami dari pertumbuhan. Museum tersebut juga menjadi tempat bagi orang lain untuk berbagi cerita mereka, termasuk kakaknya, Sterling, dan teman-teman baru dari sekolah. Bersama-sama, mereka menemukan bahwa kehilangan dapat membawa pada penemuan diri dan hubungan baru yang lebih bermakna.",
                304,
                false,
                4.1));

        books.add(new Book(R.drawable.pulang,
                "Pulang",
                "Tere Liye",
                2015,
                "Kisah agen rahasia dan pergulatan batin.",
                "Thriller",
                "Bujang menjalani hidup sebagai bagian dari organisasi misterius.",
                400,
                false,
                4.4));

        return books;
    }
}
