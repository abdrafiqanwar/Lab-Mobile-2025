package com.example.praktikum4.data;

import android.net.Uri;

import com.example.praktikum4.R;
import com.example.praktikum4.jclass.Book;

import java.util.ArrayList;
import java.util.List;

public class BookData {
    private static final List<Book> books = new ArrayList<>();

    static {
        books.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", 1997,
                "Harry Potter, seorang anak yatim piatu, mengetahui bahwa ia adalah seorang penyihir dan diundang untuk bersekolah di Hogwarts. Di sana, ia menemukan rahasia tentang dirinya, orang tuanya, dan Batu Bertuah.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img).toString(),
                false, 4.5f, "Fantasy"));

        books.add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling\n" +
                "\n", 1998,
                "Tahun kedua Harry di Hogwarts terganggu oleh teror yang menyebar di sekolah setelah dibukanya Kamar Rahasia yang legendaris.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_1).toString(),
                false, 4.4f, "Fantasy"));

        books.add(new Book("Harry Potter and the Prisoner of Azkaban", "J.K. Rowling", 1999,
                "Harry mengetahui bahwa seorang tahanan berbahaya bernama Sirius Black melarikan diri dari Azkaban dan diyakini mengejarnya.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_2).toString(),
                false, 4.6f, "Fantasy"));

        books.add(new Book("Harry Potter and the Goblet of Fire", "J.K. Rowling", 2000,
                "Harry secara tak sengaja terlibat dalam Turnamen Triwizard yang berbahaya dan menghadapi kembalinya musuh bebuyutannya, Lord Voldemort.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_4).toString(),
                false, 4.6f, "Fantasy"));

        books.add(new Book("Harry Potter and the Order of the Phoenix", "J.K. Rowling", 2003,
                "Hogwarts berada di bawah pengawasan ketat Kementerian Sihir. Harry dan teman-temannya membentuk kelompok rahasia untuk melawan kekuatan gelap.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_5).toString(),
                false, 4.5f, "Fantasy"));

        books.add(new Book("Harry Potter and the Half-Blood Prince", "J.K. Rowling", 2005,
                "Harry menemukan buku milik \"Pangeran Berdarah-Campuran\" yang membantunya belajar sihir dan memahami masa lalu Voldemort.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_6).toString(),
                false, 4.6f, "Fantasy"));

        books.add(new Book(" Harry Potter and the Deathly Hallows", "J.K. Rowling", 2007,
                "Harry, Ron, dan Hermione berusaha menghancurkan Horcrux terakhir untuk mengalahkan Voldemort dalam pertempuran terakhir.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_7).toString(),
                false, 4.6f, "Fantasy"));

        books.add(new Book("Harry Potter and the Cursed Child", "J.K. Rowling", 2016,
                "Berlatar 19 tahun setelah Harry Potter and the Deathly Hallows, cerita ini mengikuti Albus Severus Potter, putra Harry Potter, saat ia bergumul dengan warisan keluarganya di Hogwarts. Ia berteman dengan Scorpius Malfoy, anak dari Draco, dan bersama-sama mereka melakukan perjalanan waktu yang memicu perubahan besar dalam sejarah dunia sihir.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_8).toString(),
                false, 3.6f, "Fantasy"));

        books.add(new Book("The Name of the Wind", "Patrick Rothfuss", 2007,
                "Petualangan Kvothe, seorang penyihir muda yang berbakat, menceritakan kisah hidupnya dari masa kecil hingga menjadi legenda.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.img_8).toString(),
                false, 4.8f, "Fantasy"));

        books.add(new Book("The Diary of a Young Girl", "Anne Frank", 1947,
                "Kumpulan catatan harian Anne Frank selama bersembunyi dari penganiayaan Nazi, menggambarkan ketakutan, harapan, dan kemanusiaannya.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book10).toString(),
                false, 4.6f, "Biography"));

        books.add(new Book("Guns, Germs, and Steel", "Jared Diamond", 1997,
                "Menganalisis faktor geografis dan lingkungan yang memengaruhi perkembangan peradaban manusia dan ketimpangan global.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book11).toString(),
                false, 4.4f, "History"));

        books.add(new Book("And the Mountains Echoed", "Khaled Hosseini", 2013,
                "bercerita tentang Abdullah dan adiknya, Pari, yang terpisah karena keputusan sulit keluarga mereka di Afghanistan. Novel ini mengikuti dampak keputusan itu lintas generasi dan berbagai tempat, menggambarkan cinta, pengorbanan, dan hubungan keluarga yang penuh luka dan kehangatan.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book12).toString(),
                false, 4.2f, "Biography"));

        books.add(new Book("Gone Girl", "Gillian Flynn", 2012,
                "Thriller psikologis tentang hilangnya Amy Dunne dan kecurigaan terhadap suaminya, Nick, yang membuka lapisan kebohongan.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book13).toString(),
                false, 4.2f, "Mystery"));

        books.add(new Book("The Shining", "Stephen King", 1977,
                "Jack Torrance dan keluarganya merawat hotel terpencil di musim dingin, di mana kekuatan gaib dan kegilaan mulai menguasai Jack.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book14).toString(),
                false, 4.5f, "Horror"));

        books.add(new Book("A Thousand Splendid Suns", "Khaled Hosseini", 2007,
                "Kisah persahabatan dua wanita Afghanistan, Mariam dan Laila, yang bertahan menghadapi perang dan penindasan.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book15).toString(),
                false, 4.7f, "Drama"));

    }

    public static List<Book> getBooks() {
        return books;
    }

    public static void addBook(Book newBook) {
        books.add(0, newBook);
    }
}