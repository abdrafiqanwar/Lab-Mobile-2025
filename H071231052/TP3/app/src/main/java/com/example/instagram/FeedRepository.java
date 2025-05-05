package com.example.instagram;

import android.os.Build;

import com.example.instagram.model.FeedModel;
import com.example.instagram.model.PhotoModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FeedRepository {
    private static List<FeedModel> feedList;

    private static int postCount = 1;


    public static void addFeed(FeedModel feed) {
        if (feedList == null) {
            feedList = new ArrayList<>();
        }
        feedList.add(0, feed);
    }

    public static void setFeedList(List<FeedModel> list) {
        if (list == null) {
            feedList = new ArrayList<>();
        } else {
            feedList = list;
        }
    }

    public static int getPostCount() {
        return postCount++;
    }

    public static List<FeedModel> getFeedList() {
        if (feedList == null) {
            feedList = new ArrayList<>();

            List<PhotoModel> painAkatsuki = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/89/60/5f/89605f98af8f1e892e7840dc27d35e53.jpg")
            );

            List<PhotoModel> painAkatsuki2 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/57/54/69/575469642614732872aaf6ffca67e7bf.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/8e/31/46/8e3146a37c1b6e5534931f24a8ab5d7e.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/33/d2/29/33d2297ffc1b2ba65af008a06aff03b9.jpg")
            );

            List<PhotoModel> konan = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/e2/9e/93/e29e939b9dbaf2c61952ed19e50adba2.jpg")
            );

            List<PhotoModel> zoro = List.of(
                    new PhotoModel("https://i.pinimg.com/736x/88/b0/bf/88b0bfee437cd3e3b41a33b1ec4f5d3b.jpg")
            );

            List<PhotoModel> dk1 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/33/83/b5/3383b551be7289b0779b064d07a2ba6a.jpg")
            );

            List<PhotoModel> dk2 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/7e/06/17/7e06172ac57be8bd583fde6e14002ce5.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/9b/87/15/9b871592266f01b408422a234418a02d.jpg")
            );

            List<PhotoModel> dk3 = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/88/89/e7/8889e72523cd5aea5decc9c05486504e.jpg")
            );

            List<PhotoModel> ibunda = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/f2/9c/cc/f29ccc60b7b32872a973b77dc2855906.jpg")
            );

            List<PhotoModel> dontol = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/36/69/ab/3669abb39e26aa38585f1ec6d7e78d2c.jpg")
            );

            //Highlight
            List<PhotoModel> highlightZoro = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/2e/d1/8c/2ed18cee554da4948e1a6ec5761cd64f.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/a8/ec/db/a8ecdb6b743d702e94425e61557bc9d0.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/a0/51/a9/a051a946ca9fe5b116a1a7703e23ce06.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/30/56/f3/3056f3412b37f037e263af3403a69c37.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/7d/10/49/7d104903d377d3acbb41e0339c26da76.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/3d/e8/96/3de8960266fdf09e0ea40033ff52cca3.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/58/29/c0/5829c01dfbdc6e159b82d2f821aeffb8.jpg")
            );

            List<PhotoModel> highlightAkatsuki = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/5a/c7/4c/5ac74c71dc0b6697cb996447568806fa.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/1f/9f/fd/1f9ffdfbdcb4a34ad1e8c7840726cf19.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/60/19/b1/6019b18cb1b93c78c4f5d78358283895.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/18/2d/04/182d0443232b222a37b626aca5575dd9.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/e4/e3/48/e4e348c820a53f508c53138a7fac9ab9.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/8b/fd/8f/8bfd8f5bc5dcbbe1079fa183bebfc319.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/75/a2/32/75a23254b488f58ec88e554c716a8d51.jpg")
            );

            List<PhotoModel> highlightKonan = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/53/ea/23/53ea2356086c62c05f21c1c56a28faef.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/ad/7a/40/ad7a4039f2b2bf655cf5db65e5072516.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/89/d9/e2/89d9e219a39a7d85ab1d347dd1ded6f1.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/4b/f2/1c/4bf21c54ca9f6651a3929aac26c90e14.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/59/a5/d6/59a5d6cb1fd27bf8c655d7b04dd0efa6.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/30/85/59/3085595d89b5f9e55169b7058e8fd7d0.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/3f/1b/8f/3f1b8fa780bff860e4db95a85d683a75.jpg")
            );

            List<PhotoModel> highlightHero = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/61/d2/ac/61d2ac1bf5767016e261eafcd9ef603e.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/d7/d6/dc/d7d6dc63b824f8e2a79319735028bc95.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/05/51/19/055119b5575cbdbba5eddbe2571b8820.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/33/83/b5/3383b551be7289b0779b064d07a2ba6a.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/14/80/6f/14806fa5157b57f74cd0b7b851937297.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/2c/b1/06/2cb1066c9e47d451bae0f543c8261e62.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/e2/ec/2c/e2ec2c3de3f51159edeb08eadde7287c.jpg")
            );

            List<PhotoModel> highlightIbunda = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/8a/c9/bd/8ac9bde4cb1bdeac341e45afb5abf77b.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/fc/c4/01/fcc40158bed52204fbcc07f555b1e39d.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/b0/a6/3d/b0a63d346a6024cee9a4c6cd70eba94d.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/1c/c7/37/1cc7377c5712d36904fd8e91ba521005.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/5b/b7/16/5bb716af4e22f32aad6375fbf8e47299.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/b9/c0/49/b9c0491be0341b1ad6ec9785b8836a05.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/c5/7e/42/c57e4284c40bbc97a6fd7eea87f3a2df.jpg")
            );

            List<PhotoModel> highlightDenis = Arrays.asList(
                    new PhotoModel("https://i.pinimg.com/736x/90/54/e2/9054e26d2f585b1836b452668d4c7400.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/21/73/cb/2173cb4e77fd8618efb4cdf6eb812e9a.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/9c/bb/ed/9cbbed6a665d1446fd500899054423b7.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/2e/33/d1/2e33d1e7d72889693999ed1fb54d36b8.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/ba/c2/6a/bac26a69ba0fc19114cde5a73e58d3e5.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/03/f9/42/03f942cd71d487e240b24e2dce21d959.jpg"),
                    new PhotoModel("https://i.pinimg.com/736x/14/41/83/144183f7ca5d1fa792da67a581e76736.jpg")
            );

            feedList.add(new FeedModel("https://i.pinimg.com/736x/11/2f/d8/112fd82370628f13a9e5e28e96911ba1.jpg", "pain_akatsuki", painAkatsuki, "ngehe", 2039812, 8346, "i want to destroy the world",
                    "akatsuki",
                    "https://i.pinimg.com/736x/11/2f/d8/112fd82370628f13a9e5e28e96911ba1.jpg",
                    highlightAkatsuki,
                    2,
                    12613714,
                    4
            ));
            feedList.add(new FeedModel("https://i.pinimg.com/736x/5a/bd/7e/5abd7e387ac2f328faf958b325190d5a.jpg", "konan", konan, "i love pain", 24096, 117, "i think i lost everything",
                    "capekma",
                    "https://i.pinimg.com/736x/5a/bd/7e/5abd7e387ac2f328faf958b325190d5a.jpg",
                    highlightKonan,
                    1,
                    108827,
                    367
            ));

            feedList.add(new FeedModel("https://i.pinimg.com/736x/11/2f/d8/112fd82370628f13a9e5e28e96911ba1.jpg", "pain_akatsuki", painAkatsuki2, "mweeeeeeeee", 9999, 8346, "i want destroy this world"
                    ,
                    "akatsuki",
                    "https://i.pinimg.com/736x/11/2f/d8/112fd82370628f13a9e5e28e96911ba1.jpg",
                    highlightAkatsuki,
                    2,
                    12613714,
                    4
            ));

            feedList.add(new FeedModel("https://i.pinimg.com/736x/08/8c/0f/088c0f5996e4a84c1ea31bebb0e22adf.jpg", "zoro", zoro, "im lost", 31515, 3760, "Listen to the wind \n",
                    "aaaaaaaaaaaaaaaaa",
                    "https://i.pinimg.com/736x/ff/7b/4d/ff7b4d261e4c442c3632dc748f37bf00.jpg",
                    highlightZoro,
                    1,
                    107488,
                    982
            ));

            feedList.add(new FeedModel("https://i.pinimg.com/736x/5f/ad/f1/5fadf1ef3603a5b4f86a00676e3a3541.jpg", "ngeeeenn", dk1, "yakin dan percaya\n",
                    3000,
                    1200,
                    "kelass king",
                    "mimpiku",
                    "https://i.pinimg.com/736x/7c/5e/11/7c5e114a0378b095c0d4187b3675a2e6.jpg",
                    highlightHero,
                    3,
                    313147,
                    179));

            feedList.add(new FeedModel("https://i.pinimg.com/736x/5f/ad/f1/5fadf1ef3603a5b4f86a00676e3a3541.jpg", "ngeeeenn", dk2, "tuhan selalu ada\n",
                    3000,
                    1200,
                    "kelass king",
                    "mimpiku",
                    "https://i.pinimg.com/736x/7c/5e/11/7c5e114a0378b095c0d4187b3675a2e6.jpg",
                    highlightHero,
                    3,
                    313147,
                    179));

            feedList.add(new FeedModel("https://i.pinimg.com/736x/5f/ad/f1/5fadf1ef3603a5b4f86a00676e3a3541.jpg", "ngeeeenn", dk3, "dimanapun dan kapanpun",
                    11101,
                    24,
                    "kelass king",
                    "mimpiku",
                    "https://i.pinimg.com/736x/7c/5e/11/7c5e114a0378b095c0d4187b3675a2e6.jpg",
                    highlightHero,
                    3,
                    313147,
                    179));


            feedList.add(new FeedModel("https://i.pinimg.com/736x/8f/5f/77/8f5f77662ba813950dcf1540659b887a.jpg", "ibunda", ibunda, "kukokop ga nih\n",
                    710339,
                    10800,
                    "kelass king",
                    "mimpiku",
                    "https://i.pinimg.com/736x/5c/cb/f9/5ccbf956b17c213a1ab2e225291abe22.jpg",
                    highlightIbunda,
                    1,
                    6470781,
                    420
            ));

            feedList.add(new FeedModel("https://i.pinimg.com/736x/c2/7b/83/c27b83dbf8badf775dc2dd1d52afd902.jpg", "Dontol", dontol, "adiiiiiit tolongin",
                    490688,
                    99200,
                    "Adit tolongin dit\n",
                    "Walawe",
                    "https://i.pinimg.com/736x/c2/7b/83/c27b83dbf8badf775dc2dd1d52afd902.jpg",
                    highlightDenis,
                    1,
                    33909686,
                    398
            ));
        }
        return feedList;
    }
}
