    package com.example.library.data.classjava;

    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.List;

    public class DummyBookData {
        public static List<Book> getDummyBooks() {
            List<Book> books = new ArrayList<>();


            books.add(new Book(1, "Laut Bercerita", "Leila S. Chudori", 2017, "drawable/lautbercerita", 120, 4.8f, Arrays.asList("Fiksi", "Sejarah"), System.currentTimeMillis(), "Mengisahkan perjalanan hidup seorang aktivis muda yang hilang di masa rezim Orde Baru. Novel ini menyajikan kisah yang mengharukan tentang keluarga, perjuangan, dan kehilangan dalam konteks sejarah Indonesia."));
            books.add(new Book(2, "Hujan", "Tere Liye", 2016, "drawable/hujan", 98, 4.6f, Arrays.asList("Fiksi", "Romansa"), System.currentTimeMillis() - 1000, "Bercerita tentang cinta dan kehilangan dalam dunia futuristik yang penuh bencana alam. 'Hujan' mengajak pembaca merenungi makna cinta sejati dan ketabahan."));
            books.add(new Book(3, "Atomic Habits", "James Clear", 2018, "drawable/atomichabbits", 150, 4.9f, Arrays.asList("Non-Fiksi", "Pengembangan Diri"), System.currentTimeMillis() - 2000, "Panduan praktis untuk membentuk kebiasaan kecil yang berdampak besar. Buku ini memberikan strategi ilmiah dan nyata untuk menciptakan perubahan positif dalam hidup."));
            books.add(new Book(4, "The Alchemist", "Paulo Coelho", 1988, "drawable/thealchemist", 200, 4.7f, Arrays.asList("Fiksi", "Petualangan"), System.currentTimeMillis() - 3000, "Mengisahkan perjalanan seorang gembala muda bernama Santiago dalam pencarian harta karun dan makna hidup. Penuh filosofi dan inspirasi tentang mengikuti impian."));
            books.add(new Book(5, "Filosofi Teras", "Henry Manampiring", 2019, "drawable/filosofiteras", 90, 4.3f, Arrays.asList("Non-Fiksi", "Filosofi"), System.currentTimeMillis() - 4000, "Memperkenalkan filsafat Stoisisme secara ringan dan relevan dengan kehidupan sehari-hari masyarakat modern Indonesia. Buku ini menawarkan cara berpikir jernih dan tenang."));
            books.add(new Book(6, "Dilan 1990", "Pidi Baiq", 2014, "drawable/dilan1990", 130, 4.5f, Arrays.asList("Fiksi", "Romansa"), System.currentTimeMillis() - 5000, "Sebuah kisah cinta remaja yang sederhana namun manis, berlatar belakang tahun 1990 di Bandung. Dilan, seorang anak motor yang romantis dan jenaka, jatuh cinta pada Milea."));
            books.add(new Book(7, "Sapiens", "Yuval Noah Harari", 2011, "drawable/sapiens", 220, 4.9f, Arrays.asList("Non-Fiksi", "Sejarah"), System.currentTimeMillis() - 6000, "Penjelasan mendalam dan provokatif tentang sejarah umat manusia, dari zaman purba hingga era modern. Buku ini mengupas bagaimana Homo sapiens mendominasi dunia."));
            books.add(new Book(8, "Laskar Pelangi", "Andrea Hirata", 2005, "drawable/laskarpelangi", 300, 4.6f, Arrays.asList("Fiksi", "Inspirasi"), System.currentTimeMillis() - 7000, "Mengangkat kisah nyata perjuangan sepuluh anak miskin di Belitung yang bersekolah di bawah keterbatasan. Penuh semangat, tawa, dan inspirasi akan pentingnya pendidikan."));
            books.add(new Book(9, "Rich Dad Poor Dad", "Robert Kiyosaki", 1997, "drawable/richdad", 250, 4.8f, Arrays.asList("Non-Fiksi", "Finansial"), System.currentTimeMillis() - 8000, "Pelajaran keuangan dari dua figur ayah dengan pandangan berbeda."));
            books.add(new Book(10, "Think and Grow Rich", "Napoleon Hill", 1937, "drawable/thinkgrowrich", 210, 4.7f, Arrays.asList("Motivasi", "Finansial"), System.currentTimeMillis() - 9000, "Klasik motivasi tentang cara berpikir yang mengarah pada kesuksesan."));
            books.add(new Book(11, "The Power of Habit", "Charles Duhigg", 2012, "drawable/habitpower", 180, 4.5f, Arrays.asList("Psikologi", "Self-help"), System.currentTimeMillis() - 10000, "Membongkar sains di balik kebiasaan dan bagaimana mengubahnya."));
            books.add(new Book(12, "Bumi", "Tere Liye", 2014, "drawable/bumi", 300, 4.6f, Arrays.asList("Fantasi", "Fiksi"), System.currentTimeMillis() - 11000, "Petualangan dunia paralel oleh anak-anak luar biasa."));
            books.add(new Book(13, "Orang-Orang Biasa", "Andrea Hirata", 2019, "drawable/orangbiasa", 180, 4.4f, Arrays.asList("Fiksi", "Kriminal"), System.currentTimeMillis() - 12000, "Kisah unik tentang kelompok pencuri yang cerdas."));
            books.add(new Book(14, "Negeri 5 Menara", "Ahmad Fuadi", 2009, "drawable/5menara", 170, 4.3f, Arrays.asList("Fiksi", "Inspiratif"), System.currentTimeMillis() - 13000, "Tentang perjuangan santri dengan mimpi besar."));
            books.add(new Book(15, "Harry Potter", "J.K. Rowling", 1997, "drawable/harrypotter", 1000, 4.9f, Arrays.asList("Fantasi", "Petualangan"), System.currentTimeMillis() - 14000, "Penyihir muda dan dunia sihirnya."));
            books.add(new Book(16, "To Kill a Mockingbird", "Harper Lee", 1960, "drawable/mockingbird", 800, 4.8f, Arrays.asList("Klasik", "Hukum"), System.currentTimeMillis() - 15000, "Perjuangan keadilan di Amerika Selatan."));
            books.add(new Book(17, "1984", "George Orwell", 1949, "drawable/1984", 700, 4.7f, Arrays.asList("Distopia", "Politik"), System.currentTimeMillis() - 16000, "Negara pengawasan dan pemberontakan pikiran."));
            books.add(new Book(18, "Animal Farm", "George Orwell", 1945, "drawable/animalfarm", 600, 4.6f, Arrays.asList("Sindikasi", "Satir"), System.currentTimeMillis() - 17000, "Revolusi peternakan melawan ketidakadilan."));
            books.add(new Book(19, "Ayat-Ayat Cinta", "Habiburrahman El Shirazy", 2004, "drawable/ayatcinta", 500, 4.5f, Arrays.asList("Romansa", "Religi"), System.currentTimeMillis() - 18000, "Cinta, iman, dan ujian di negeri orang."));
            books.add(new Book(20, "Perahu Kertas", "Dewi Lestari", 2009, "drawable/perahukertas", 400, 4.4f, Arrays.asList("Romansa", "Fiksi"), System.currentTimeMillis() - 19000, "Persahabatan dan cinta yang berkembang perlahan."));
            books.add(new Book(21, "Rectoverso", "Dewi Lestari", 2008, "drawable/rectoverso", 350, 4.3f, Arrays.asList("Antologi", "Romantis"), System.currentTimeMillis() - 20000, "Cerita pendek dan lagu yang saling melengkapi."));
            books.add(new Book(22, "Berani Tidak Disukai", "Ichiro Kishimi", 2013, "drawable/beranidisukai", 300, 4.6f, Arrays.asList("Psikologi", "Pengembangan Diri"), System.currentTimeMillis() - 21000, "Hidup bebas tanpa beban dari pendapat orang lain."));
            books.add(new Book(23, "The Subtle Art of Not Giving a F*ck", "Mark Manson", 2016, "drawable/subtleart", 320, 4.7f, Arrays.asList("Self-help", "Psikologi"), System.currentTimeMillis() - 22000, "Menyaring hal yang penting untuk kebahagiaan."));
            books.add(new Book(24, "The 7 Habits of Highly Effective People", "Stephen Covey", 1989, "drawable/7habits", 420, 4.8f, Arrays.asList("Manajemen Diri", "Motivasi"), System.currentTimeMillis() - 23000, "Prinsip hidup yang membawa efektivitas."));
            books.add(new Book(25, "Cantik Itu Luka", "Eka Kurniawan", 2002, "drawable/cantikluk", 230, 4.5f, Arrays.asList("Fiksi", "Sastra"), System.currentTimeMillis() - 24000, "Novel satir tentang luka sejarah dan cinta."));
            books.add(new Book(26, "Supernova", "Dewi Lestari", 2001, "drawable/supernova", 190, 4.3f, Arrays.asList("Sastra", "Fiksi Sains"), System.currentTimeMillis() - 25000, "Eksplorasi eksistensi dan sains dalam narasi imajinatif."));
            books.add(new Book(27, "Mariposa", "Luluk HF", 2018, "drawable/mariposa", 210, 4.2f, Arrays.asList("Romansa", "Remaja"), System.currentTimeMillis() - 26000, "Kisah gadis gigih mengejar cinta."));
            books.add(new Book(28, "Galaksi", "Poppi Pertiwi", 2019, "drawable/galaksi", 175, 4.1f, Arrays.asList("Fiksi", "Remaja"), System.currentTimeMillis() - 27000, "Anak geng sekolah yang jatuh cinta diam-diam."));
            books.add(new Book(29, "Sepotong Hati yang Baru", "Tere Liye", 2011, "drawable/hatibaru", 155, 4.4f, Arrays.asList("Romansa", "Fiksi"), System.currentTimeMillis() - 28000, "Cinta yang tertahan dalam keikhlasan."));
            books.add(new Book(30, "Tentang Kamu", "Tere Liye", 2016, "drawable/tentangkamu", 265, 4.6f, Arrays.asList("Fiksi", "Misteri"), System.currentTimeMillis() - 29000, "Kisah seorang pengacara menelusuri hidup misterius kliennya."));

            return books;
        }
    }