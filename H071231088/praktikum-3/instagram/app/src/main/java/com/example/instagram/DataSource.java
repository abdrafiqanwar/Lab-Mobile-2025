package com.example.instagram;

import java.util.ArrayList;

public class DataSource {
    public static ArrayList<Post> posts = generateDummyPosts();

    private static ArrayList<Post> generateDummyPosts(){
        ArrayList<Post> posts = new ArrayList<>();

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Frieren
                "Frieren",
                "2 jam yang lalu",
                "Dunia ini luas, banyak yang belum aku lihat. Petualangan belum berakhir.",
                1250,
                212,
                58,
                1,
                2500,  // Followers
                150,   // Following
                "Penyihir abadi yang pernah berpetualang bersama pahlawan, mencari arti kehidupan setelah bertahun-tahun."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Himmel
                "Himmel",
                "3 jam yang lalu",
                "Kehidupan ini penuh dengan pertempuran dan harapan. Setiap langkah adalah petualangan.",
                1024,
                156,
                45,
                1,
                1900,
                120,
                "Pahlawan yang memiliki semangat tak pernah padam, namun banyak yang telah hilang selama bertahun-tahun."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Heiter
                "Heiter",
                "1 hari yang lalu",
                "Hidup harus dinikmati, meskipun penuh dengan perjuangan dan kesulitan.",
                1340,
                134,
                45,
                1,
                2200,
                180,
                "Seorang yang penuh semangat, meskipun banyak yang terjadi, ia selalu berusaha melihat sisi cerah dari kehidupan."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Eisen
                "Eisen",
                "4 jam yang lalu",
                "Berjuang untuk keadilan, meski waktu terus berlalu, semangatku tetap membara.",
                1100,
                102,
                23,
                1,
                1800,
                130,
                "Seorang ksatria tua yang tak kenal lelah, bertarung demi melindungi dunia dengan kehormatan."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Fern
                "Fern",
                "1 jam yang lalu",
                "Dunia ini sangat indah dan penuh misteri. Aku ingin mengetahui lebih banyak.",
                1200,
                80,
                28,
                1,
                2000,
                150,
                "Pemuda yang penuh rasa ingin tahu dan semangat untuk memecahkan misteri dunia."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Stark
                "Stark",
                "6 jam yang lalu",
                "Kekuatan sejati adalah kemampuan untuk tetap berjuang meskipun semuanya melawanmu.",
                980,
                130,
                56,
                1,
                1700,
                145,
                "Seorang pejuang yang keras kepala, selalu siap melawan meskipun tantangan besar dihadapinya."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Flamme
                "Flamme",
                "2 jam yang lalu",
                "Jangan biarkan api dalam dirimu padam. Semangat hidup adalah kunci untuk semua petualangan.",
                1050,
                200,
                72,
                1,
                2100,
                160,
                "Pemilik api dalam dirinya, penuh dengan energi dan semangat yang menyala-nyala."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Qual
                "Qual",
                "7 jam yang lalu",
                "Kadang hidup terasa sulit, tetapi setiap rintangan adalah pelajaran yang berharga.",
                990,
                75,
                36,
                1,
                1600,
                110,
                "Penuh dengan pertanyaan tentang dunia, dia selalu mencoba untuk mencari makna dalam setiap tindakan."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Demon King
                "Demon King",
                "5 jam yang lalu",
                "Bukan hanya kekuatan yang penting, tetapi juga kecerdasan dan keinginan untuk terus belajar.",
                1300,
                150,
                67,
                1,
                2500,
                180,
                "Raja Iblis yang penuh misteri dan kebijaksanaan, namun sangat bijak dalam mengendalikan kekuatannya."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Aura
                "Aura",
                "2 hari yang lalu",
                "Kekuatan tanpa kendali hanya akan menghancurkan, jadi aku harus mengendalikannya.",
                1200,
                100,
                50,
                1,
                2100,
                160,
                "Seorang yang sangat kuat dengan aura yang menakutkan, tetapi berusaha mengendalikan kemampuannya."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Lügner
                "Lügner",
                "6 jam yang lalu",
                "Aku akan membuktikan bahwa kebenaran selalu datang, meskipun penuh kebohongan.",
                950,
                130,
                67,
                1,
                1750,
                140,
                "Seseorang yang selalu terjebak dalam kebohongan, namun tetap berusaha untuk menemukan jalan menuju kebenaran."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Linie
                "Linie",
                "1 jam yang lalu",
                "Berjalan di jalan yang benar adalah hal yang paling sulit. Namun, itu adalah yang paling berharga.",
                1050,
                89,
                45,
                1,
                1900,
                125,
                "Sosok yang tidak pernah melenceng dari tujuan, meskipun harus melewati jalan yang penuh rintangan."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Draht
                "Draht",
                "3 jam yang lalu",
                "Aku lebih memilih kekuatan pikiranku daripada kekuatan fisik, karena kecerdasan adalah senjataku.",
                1300,
                150,
                45,
                1,
                2100,
                170,
                "Seorang pemikir dan cerdik, senjatanya adalah otak dan strategi yang dia rancang."
        ));

        posts.add(new Post(
                R.drawable.frieren,  // Gambar untuk Graf Granat
                "Graf Granat",
                "1 hari yang lalu",
                "Bergantung pada otak dan keberanian, dunia ini membutuhkan lebih banyak dari itu.",
                1400,
                135,
                53,
                1,
                2300,
                180,
                "Sosok yang cerdas dan penuh strategi, selalu mencari cara untuk mencapai tujuan tanpa kekerasan."
        ));

        return posts;
    }
}
