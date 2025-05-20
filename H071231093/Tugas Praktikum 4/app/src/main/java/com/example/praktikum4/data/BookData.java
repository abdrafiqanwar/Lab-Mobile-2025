package com.example.praktikum4.data;

import android.net.Uri;

import com.example.praktikum4.R;
import com.example.praktikum4.jclass.Book;

import java.util.ArrayList;
import java.util.List;

public class BookData {
    private static final List<Book> books = new ArrayList<>();

    static {
        books.add(new Book("Laskar Pelangi", "Andrea Hirata", 2005,
                "Kisah inspiratif tentang perjuangan anak-anak Belitung untuk pendidikan.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book1).toString(),
                false, 4.8f, "Drama"));

        books.add(new Book("Bumi Manusia", "Pramoedya Ananta Toer", 1980,
                "Kisah cinta dan perjuangan di masa kolonial Belanda.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book2).toString(),
                false, 4.9f, "History"));

        books.add(new Book("Negeri 5 Menara", "Ahmad Fuadi", 2009,
                "Perjalanan hidup santri dengan mimpi besar di pesantren.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book3).toString(),
                false, 4.7f, "Biography"));

        books.add(new Book("Supernova: Ksatria, Puteri, dan Bintang Jatuh", "Dee Lestari", 2001,
                "Novel yang menggabungkan sains, filsafat, dan cinta.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book4).toString(),
                false, 4.6f, "Fiction"));

        books.add(new Book("Ayat-Ayat Cinta", "Habiburrahman El Shirazy", 2004,
                "Kisah cinta Islami yang penuh nilai moral.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book5).toString(),
                false, 4.5f, "Romance"));

        books.add(new Book("The Wedding People", "Alison Espach", 2024,
                "Sebuah novel yang penuh semangat dan sangat bijaksana tentang seorang tamu pernikahan yang tak terduga dan orang-orang yang mengejutkan yang membantunya memulai hidup baru.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book6).toString(),
                false, 4.6f, "Fiction"));

        books.add(new Book("Where the Crawdads Sing", "Delia Owens", 2018,
                "Mengisahkan Kya Clark, gadis yang tumbuh sendiri di rawa-rawa Carolina Utara, menghadapi prasangka masyarakat dan misteri pembunuhan yang menimpanya.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book7).toString(),
                false, 4.5f, "Fiction"));

        books.add(new Book("Sapiens: A Brief History of Humankind", "Yuval Noah Harari", 2011,
                "Mengupas sejarah umat manusia dari era Homo sapiens awal hingga perkembangan ekonomi kapitalis dan teknologi modern.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book8).toString(),
                false, 4.7f, "Non-Fiction"));

        books.add(new Book("The Name of the Wind", "Patrick Rothfuss", 2007,
                "Petualangan Kvothe, seorang penyihir muda yang berbakat, menceritakan kisah hidupnya dari masa kecil hingga menjadi legenda.",
                Uri.parse("android.resource://com.example.praktikum4/" + R.drawable.book9).toString(),
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