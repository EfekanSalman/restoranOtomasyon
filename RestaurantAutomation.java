import java.io.*;
import java.util.*;

public class RestaurantAutomation {
    static final String USER_FILE = "users.txt";
    static Scanner scanner = new Scanner(System.in);
    static List<User> users = new ArrayList<>();
    static User currentUser = null;
    static List<List<MenuItem>> menus = new ArrayList<>();
    static List<MenuItem> cart = new ArrayList<>(); // Sepet
    static Map<MenuItem, Integer> stock = new HashMap<>(); // Stok
    static List<Reservation> reservations = new ArrayList<>();


    public static void main(String[] args) {
        initializeMenus();
        loadUsers();
        initializeStock();
        showWelcomeScreen();
    }

    static void initializeMenus() {
        menus.add(new ArrayList<>());
        menus.get(0).add(new MenuItem("Kahvalti Tabagi", 300));
        menus.get(0).add(new MenuItem("Serpme Kahvalti", 500));
        menus.get(0).add(new MenuItem("Sahanda Yumurta", 100));
        menus.get(0).add(new MenuItem("Sucuklu Yumurta", 150));
        menus.get(0).add(new MenuItem("Pankek Tabagi", 200));
        menus.get(0).add(new MenuItem("Pisi Tabagi", 300));
        menus.get(0).add(new MenuItem("Menemen", 300));
        menus.get(0).add(new MenuItem("Omlet", 120));

        menus.add(new ArrayList<>());
        menus.get(1).add(new MenuItem("Adana Kebap", 310));
        menus.get(1).add(new MenuItem("Urfa Kebap", 310));
        menus.get(1).add(new MenuItem("Kuzu Sis", 330));
        menus.get(1).add(new MenuItem("Çöp Sis", 330));
        menus.get(1).add(new MenuItem("Patlican Kebabi", 400));
        menus.get(1).add(new MenuItem("Tavuk Sis", 145));
        menus.get(1).add(new MenuItem("Tavuk Kanat", 250));
        menus.get(1).add(new MenuItem("Sac tava", 310));

        menus.add(new ArrayList<>());
        menus.get(2).add(new MenuItem("Mercimek Çorbası", 70));
        menus.get(2).add(new MenuItem("Domates Çorbası", 75));
        menus.get(2).add(new MenuItem("Tavuk Suyu Çorbası", 75));
        menus.get(2).add(new MenuItem("Kelle Paça Çorbası", 160));
        menus.get(2).add(new MenuItem("Beyran", 165));
        menus.get(2).add(new MenuItem("Mantar Çorbasi", 65));
        menus.get(2).add(new MenuItem("Yayla Çorbasi", 75));
        menus.get(2).add(new MenuItem("Yüksük Çorbasi", 80));

        menus.add(new ArrayList<>());
        menus.get(3).add(new MenuItem("Acili Ezme", 40));
        menus.get(3).add(new MenuItem("Cacik", 150));
        menus.get(3).add(new MenuItem("Girit Ezmesi", 230));
        menus.get(3).add(new MenuItem("Humus", 60));
        menus.get(3).add(new MenuItem("Haydari", 55));
        menus.get(3).add(new MenuItem("Zeytin Piyazi", 75));
        menus.get(3).add(new MenuItem("Patlican Salatasi", 75));
        menus.get(3).add(new MenuItem("Çig köfte", 80));

        menus.add(new ArrayList<>());
        menus.get(4).add(new MenuItem("Mevsim Salata", 60));
        menus.get(4).add(new MenuItem("Nurdagi Salata", 80));
        menus.get(4).add(new MenuItem("Çoban Salatasi", 50));
        menus.get(4).add(new MenuItem("Yesil Salata", 50));
        menus.get(4).add(new MenuItem("Gavurdagi Salatasi", 110));
        menus.get(4).add(new MenuItem("Kasik Salata", 75));
        menus.get(4).add(new MenuItem("Sezar Salata", 75));
        menus.get(4).add(new MenuItem("Rus Salatasi", 120));

        menus.add(new ArrayList<>());
        menus.get(5).add(new MenuItem("Kuru Patlican Dolmasi",70));
        menus.get(5).add(new MenuItem("Kizartma Içli Köfte", 90));
        menus.get(5).add(new MenuItem("Haslama Içli Köfte", 95));
        menus.get(5).add(new MenuItem("Patates Köftesi", 60));
        menus.get(5).add(new MenuItem("Mücver", 170));
        menus.get(5).add(new MenuItem("Sigara böregi", 35));
        menus.get(5).add(new MenuItem("Paçanga böregi", 55));
        menus.get(5).add(new MenuItem("Findik lahmacun", 70));

        menus.add(new ArrayList<>());
        menus.get(6).add(new MenuItem("Sicak Burma Kadayif",300));
        menus.get(6).add(new MenuItem("Gaziantep Katmeri", 290));
        menus.get(6).add(new MenuItem("Fistikli Baklava", 295));
        menus.get(6).add(new MenuItem("Fistikli Sarma", 300));
        menus.get(6).add(new MenuItem("Midye Baklava", 240));
        menus.get(6).add(new MenuItem("Cevizli Baklava", 235));
        menus.get(6).add(new MenuItem("Dondurmali Havuç Dilimi", 240));
        menus.get(6).add(new MenuItem("Söbiyet", 200));

        menus.add(new ArrayList<>());
        menus.get(7).add(new MenuItem("Coca Cola ",80));
        menus.get(7).add(new MenuItem("Ice Tea", 50));
        menus.get(7).add(new MenuItem("Sprite", 70));
        menus.get(7).add(new MenuItem("Soda", 35));
        menus.get(7).add(new MenuItem("Limonata", 75));
        menus.get(7).add(new MenuItem("Ayran", 55));
        menus.get(7).add(new MenuItem("Şalgam", 40));
        menus.get(7).add(new MenuItem("Şira", 70));
    }

    static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                users.add(new User(data[0], data[1], data[2], data[3]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void initializeStock() {
        for (List<MenuItem> menu : menus) {
            for (MenuItem item : menu) {
                stock.put(item, 10); // Başlangıçta her üründen 10 adet var
            }
        }
    }

    static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USER_FILE))) {
            for (User user : users) {
                bw.write(user.getName() + "," + user.getSurname() + "," + user.getUsername() + "," + user.getPassword());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void showWelcomeScreen() {
        int choice;
        do {
            System.out.println("1) Kayıt ol");
            System.out.println("2) Giriş yap");
            System.out.println("3) SSS");
            System.out.println("4) Çıkış yap");
            System.out.print("Seçiminiz: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Dummy read to clear the buffer

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    FAQ();
                    break;
                case 4:
                    System.out.println("Programdan çıkılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } while (choice != 4);
    }

    static void register() {
        System.out.print("İsiminizi giriniz: ");
        String name = scanner.nextLine();
        System.out.print("Soyisminizi giriniz: ");
        String surname = scanner.nextLine();
        System.out.print("Kullanıcı adınızı giriniz: ");
        String username = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        users.add(new User(name, surname, username, password));
        saveUsers();
        System.out.println("Kayıt başarıyla oluşturuldu.");
    }

    static void login() {
        System.out.print("Kullanıcı adı: ");
        String username = scanner.nextLine();
        System.out.print("Şifre: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Giriş başarılı! Hoş geldiniz, " + user.getName() + " " + user.getSurname() + ".");
                showMainMenu();
                return;
            }
        }
        System.out.println("Kullanıcı adı veya şifre hatalı.");
    }

    static void showMainMenu() {
        int choice;
        do {
            System.out.println("\n-----------Menü-----------");
            System.out.println("1) Kullanıcı Bilgileri");
            System.out.println("2) Sipariş ver");
            System.out.println("3) Sepetimi gör");
            System.out.println("4) Sepetten öğe sil");
            System.out.println("5) Popüler menüler");
            System.out.println("6) Rezervasyon Yap");
            System.out.println("7) Rezervasyon Görüntüle");
            System.out.println("8) Ödeme yap");
            System.out.println("9) Hakkımızda");
            System.out.println("10) Çıkış yap");
            System.out.print("Seçiminiz: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Dummy read to clear the buffer

            switch (choice) {
                case 1:
                    showUserInfo();
                    break;
                case 2:
                    orderFood(); // Yeni eklenen metot
                    break;
                case 3:
                    showCart();
                    break;
                case 4:
                    removeItemFromCart();
                    break;
                case 5:
                    showPopularMenus();
                    break;
                case 6:
                    makeReservation();
                    break;
                case 7:
                    showReservations();
                    break;
                case 8:
                    makePayement();
                    break;
                case 9:
                    aboutUs();
                    break;
                case 10:

                    currentUser = null; // Çıkış yap
                    System.out.println("Çıkış yapılıyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } while (choice != 10);
    }

    static void showUserInfo() {
        if (currentUser != null) {
            System.out.println("Kullanıcı Bilgileriniz:");
            System.out.println("--------------------------------------------------");
            System.out.printf("%-15s%-25s%n", "İsim:", currentUser.getName());
            System.out.printf("%-15s%-25s%n", "Soyisim:", currentUser.getSurname());
            System.out.printf("%-15s%-25s%n", "Kullanıcı Adı:", currentUser.getUsername());

            // Adres bilgilerini göster
            if (currentUser.getAddress() != null && !currentUser.getAddress().isEmpty()) {
                System.out.printf("%-15s%-25s%n", "Adres:", currentUser.getAddress());
                System.out.printf("%-15s%-25s%n", "Apartman İsmi:", currentUser.getApartmentName());
                System.out.printf("%-15s%-25s%n", "Daire Numarası:", currentUser.getApartmentNumber());
            } else {
                System.out.println("Adres bilgileri kaydedilmemiş.");
            }

            // Eğer kullanıcının kredi kartı bilgileri mevcutsa, onları da göster
            if (currentUser.getCardNumber() != null && !currentUser.getCardNumber().isEmpty()) {
                System.out.printf("%-15s%-25s%n", "Kart Numarası:", currentUser.getCardNumber());
                System.out.printf("%-15s%-25s%n", "Kart Üzerindeki İsim:", currentUser.getCardHolderName());
                System.out.printf("%-15s%-25s%n", "Son Kullanma Tarihi:", currentUser.getExpirationDate());
                System.out.printf("%-15s%-25s%n", "CCV Kodu:", currentUser.getCcv());
            } else {
                System.out.println("Kredi kartı bilgileri kaydedilmemiş.");
            }

            System.out.println("--------------------------------------------------");
            System.out.println("1) Bilgileri doğrula");
            System.out.println("2) Bilgilerimi güncelle");
            System.out.println("3) Ana menüye dön");
            System.out.print("Seçiminiz: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Dummy read to clear the buffer

            switch (choice) {
                case 1:
                    verifyUserInfo();
                    break;
                case 2:
                    updateUserInfo();
                    break;
                case 3:
                    System.out.println("Ana menüye dönülüyor...");
                    break;
                default:
                    System.out.println("Geçersiz seçim!");
            }
        } else {
            System.out.println("Kullanıcı bilgileri görüntülenemedi. Lütfen giriş yapınız.");
        }
    }


    static void verifyUserInfo() {
        System.out.println("Bilgileriniz doğrulanıyor...");
        // Kullanıcının bilgilerini doğrulama işlemi buraya eklenecek
        System.out.println("Bilgileriniz doğrulandı.");
        returnToMainMenu();
    }

    static void updateUserInfo() {
        System.out.println("1) Bilgilerimi güncelle");
        System.out.println("2) Bilgi ekle");
        System.out.print("Seçiminiz: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Dummy read to clear the buffer

        switch (choice) {
            case 1:
                updateExistingInfo();
                break;
            case 2:
                addNewInfo();
                break;
            default:
                System.out.println("Geçersiz seçim!");
        }
    }

    static void updateExistingInfo() {
        System.out.println("1) Şifrenizi değiştirin");
        System.out.println("2) Geri dön");
        System.out.print("Seçiminiz: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Dummy read to clear the buffer

        switch (choice) {
            case 1:
                changePassword();
                break;
            case 2:
                returnToMainMenu();
                break;
            default:
                System.out.println("Geçersiz seçim!");
        }
    }

    static void addNewInfo() {
        System.out.println("1) Kredi kartı ekle");
        System.out.println("2) Adres ekle");
        System.out.println("3) Geri dön");
        System.out.print("Seçiminiz: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Dummy read to clear the buffer

        switch (choice) {
            case 1:
                addCreditCard();
                break;
            case 2:
                addAddress();
                break;
            case 3:
                returnToMainMenu();
                break;
            default:
                System.out.println("Geçersiz seçim!");
        }
    }

    static void changePassword() {
        System.out.print("Yeni şifrenizi girin: ");
        String newPassword = scanner.nextLine();

        System.out.print("Eski şifrenizi girin: ");
        String oldPassword = scanner.nextLine();

        if (currentUser.getPassword().equals(oldPassword)) {
            currentUser.setPassword(newPassword);
            saveUsers();
            System.out.println("Şifre başarıyla değiştirildi.");
        } else {
            System.out.println("Eski şifrenizi yanlış girdiniz. Şifre değiştirilemedi.");
        }
    }

    static void addCreditCard() {
        boolean isValidCardNumber = false;
        boolean isValidCCV = false;

        String cardNumber;
        String cardHolderName;
        String expirationDate;
        String ccv;

        do {
            System.out.print("Kredi kartı numaranızı girin (16 haneli): ");
            cardNumber = scanner.nextLine();

            // Kredi kartı numarasının 16 haneli olup olmadığını kontrol et
            if (cardNumber.length() != 16) {
                System.out.println("Lütfen 16 haneli olarak giriniz.");
            } else {
                isValidCardNumber = true;
            }
        } while (!isValidCardNumber);

        System.out.print("Kart üstündeki ismi girin: ");
        cardHolderName = scanner.nextLine();

        System.out.print("Son kullanma tarihini (AA/YY formatında) girin: ");
        expirationDate = scanner.nextLine();

        do {
            System.out.print("CCV kodunu girin: ");
            ccv = scanner.nextLine();

            // CCV'nin 3 haneli olup olmadığını kontrol et
            if (ccv.length() != 3) {
                System.out.println("Lütfen 3 haneli olarak giriniz.");
            } else {
                isValidCCV = true;
            }
        } while (!isValidCCV);

        // Kullanıcıya ait `currentUser` nesnesinin kredi kartı bilgilerini kaydet
        if (currentUser != null) {
            currentUser.setCardNumber(cardNumber);
            currentUser.setCardHolderName(cardHolderName);
            currentUser.setExpirationDate(expirationDate);
            currentUser.setCcv(ccv);
            System.out.println("Kart bilgileriniz başarıyla kaydedildi.");
        } else {
            System.out.println("Kullanıcı bulunamadı. Lütfen tekrar giriş yapınız.");
        }
    }

    static void addAddress() {
        System.out.print("Oturduğunuz şehiri giriniz: ");
        String address = scanner.nextLine();

        System.out.print("Oturduğunuz apartman ismini giriniz: ");
        String apartmentName = scanner.nextLine();

        System.out.print("Oturduğunuz dairenin numarasını giriniz: ");
        String apartmentNumber = scanner.nextLine();

        System.out.println("Adres bilgileriz başarıyla alınmıştır");

        if (currentUser != null) {
            currentUser.setAddress(address);
            currentUser.setApartmentName(apartmentName);
            currentUser.setApartmentNumber(apartmentNumber);
            System.out.println("Adres bilgileriniz başarıyla kaydedildi.");
        } else {
            System.out.println("Kullanıcı bulunamadı. Lütfen tekrar giriş yapınız.");
        }
    }

    static void returnToMainMenu() {
        // Ana menüye dönme işlemi
        showMainMenu();
    }


    static class Reservation {
        String date;
        String time;

        Reservation(String date, String time) {
            this.date = date;
            this.time = time;
        }

        @Override
        public String toString() {
            return "Tarih: " + date + ", Saat: " + time;
        }
    }

    static void makeReservation() {
        System.out.println("--- Rezervasyon ---");
        System.out.println("1) Rezervasyon Yap");
        System.out.println("2) Geri Dön");
        System.out.print("Seçiminizi yapın: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                System.out.println("Rezervasyon yapılıyor...");
                chooseDayToCome();
                break;
            case 2:
                System.out.println("Rezervasyon işlemi iptal edildi.");
                break;
            default:
                System.out.println("Geçersiz seçenek! Lütfen tekrar deneyin.");
        }
    }

    static void chooseDayToCome() {
        System.out.println("----------------------------------");
        System.out.println("Hangi gün gelmek istersiniz?");
        System.out.println("1) Pazartesi");
        System.out.println("2) Salı");
        System.out.println("3) Çarşamba");
        System.out.println("4) Perşembe");
        System.out.println("5) Cuma");
        System.out.println("6) Cumartesi");
        System.out.println("7) Pazar");
        System.out.print("Seçiminiz: ");

        int dayChoice = scanner.nextInt();

        if (dayChoice < 1 || dayChoice > 7) {
            System.out.println("Geçersiz gün seçimi.");
            chooseDayToCome();
            return;
        }
        chooseTimeToCome(dayChoice);
    }

    static void chooseTimeToCome(int dayChoice) {
        System.out.println("----------------------------------");
        System.out.print("Saat kaçta rezervasyon yapmak istiyorsunuz? (Örneğin: 13:30) ");
        String chooseTimeToCome = scanner.next();

        String day = "";
        switch(dayChoice) {
            case 1:
                day = "Pazartesi";
                break;
            case 2:
                day = "Salı";
                break;
            case 3:
                day = "Çarşamba";
                break;
            case 4:
                day = "Perşembe";
                break;
            case 5:
                day = "Cuma";
                break;
            case 6:
                day = "Cumartesi";
                break;
            case 7:
                day = "Pazar";
                break;
        }
        Reservation reservation = new Reservation(day, chooseTimeToCome);
        reservations.add(reservation);

        System.out.println("Rezervasyonunuz başarıyla alındı:");
        System.out.println("Tarih: " + reservation.date + ", Saat: " + reservation.time);
    }

    static void showReservations() {
        if (reservations.isEmpty()) {
            System.out.println("Rezervasyon Bulunamadı.");
        } else {
            System.out.println("Rezervasyonlarınız:");
            for (Reservation reservation : reservations) {
                System.out.println("------------------------------------------");
                System.out.println(reservation);
                System.out.println("------------------------------------------");
            }
        }
    }

    static void aboutUs() {
        System.out.println("       \n\t\t\t\t\t\t\t<<<Hakkımızda>>>");
        System.out.println("<<<---İSTANBUL'DAKİ KALİTELİ YEMEKLERİ EN İYİ LEZZETLERLERLE SİZE SUNUYORUZ--->>>");
        System.out.println("    ---Şubelerimiz---                 ---İletişim---");
        System.out.println("   İstanbul/Küçükçekmece-----------> +90 (123)-456-789");
        System.out.println("   İstanbul/Beşiktaş---------------> +90 (123)-456-789");
        System.out.println("   İstanbul/Sultangazi-------------> +90 (123)-456-789");
        System.out.println("   İstanbul/Başakşehir-------------> +90 (123)-456-789");
        System.out.println("   İstanbul/Tuzla------------------> +90 (123)-456-789);

    }


    static void orderFood() {
        int choice;
        do {
            System.out.println("Kategori Seçiniz:");
            displayCategories();
            System.out.println("9) Geri Dön");
            System.out.print("Seçiminiz: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Dummy read to clear the buffer

            if (choice >= 1 && choice <= getCategoryCount()) {
                int categoryIndex = choice - 1;
                System.out.println("Menü:");
                displayMenuForCategory(categoryIndex);

                System.out.print("Sipariş vermek istediğiniz ürünün numarasını girin (9: Geri Dön): ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Dummy read to clear the buffer

                if (choice >= 1 && choice <= getMenuCountForCategory(categoryIndex)) {
                    MenuItem selectedMenuItem = getMenuItemAt(categoryIndex, choice - 1);
                    System.out.print("Kaç adet istersiniz? Stokta " + stock.get(selectedMenuItem) + " adet var: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Dummy read to clear the buffer

                    if (quantity > 0 && quantity <= stock.get(selectedMenuItem)) {
                        // Seçilen yemeği ve adet sayısını cart listesine ekleyin
                        for (int i = 0; i < quantity; i++) {
                            cart.add(selectedMenuItem);
                        }
                        System.out.println(quantity + " adet " + selectedMenuItem.getName() + " sepete eklendi.");
                        stock.put(selectedMenuItem, stock.get(selectedMenuItem) - quantity); // Stoktan düş
                    } else {
                        System.out.println("Geçersiz miktar!");
                    }
                }
            }
        } while (choice != 9);
    }



    static void showCart() {
        System.out.println("Sepetinizdeki Ürünler:");
        if (cart.isEmpty()) {
            System.out.println("Sepetiniz boş.");
        } else {
            double totalCost = 0; // Toplam tutarı takip etmek için bir değişken
            System.out.println("--------------------------------------------------");
            System.out.printf("%-4s%-25s%-10s%-10s%n", "No", "Ürün", "Fiyat", "Adet");
            System.out.println("--------------------------------------------------");

            // Sepetteki her öğeyi bir kez saymak için bir dizi oluşturun
            Map<MenuItem, Integer> itemCounts = new HashMap<>();
            for (MenuItem item : cart) {
                itemCounts.put(item, itemCounts.getOrDefault(item, 0) + 1);
            }

            // Her bir ürün için fiyat ve adet bilgilerini göster
            int index = 1;
            for (Map.Entry<MenuItem, Integer> entry : itemCounts.entrySet()) {
                MenuItem item = entry.getKey();
                int count = entry.getValue();
                double itemPrice = item.getPrice();
                totalCost += itemPrice * count;
                System.out.printf("%-4d%-25s%-10.2f%-10d%n", index++, item.getName(), itemPrice, count);
            }

            System.out.println("--------------------------------------------------");
            System.out.printf("%-35s%-10.2f%n", "Toplam Tutar:", totalCost);
        }
    }


    static void removeItemFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Sepetiniz zaten boş.");
        } else {
            System.out.println("Sepetinizdeki Ürünler:");
            showCart(); // Sepetteki ürünleri önce göster

            System.out.print("Silmek istediğiniz ürünün numarasını girin: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Dummy read to clear the buffer

            if (choice > 0 && choice <= cart.size()) {
                MenuItem selectedCartItem = cart.get(choice - 1);
                int currentQuantity = getItemCount(selectedCartItem); // Sepetteki mevcut miktarı al
                System.out.print("Stokta " + currentQuantity + " adet var. Kaç adet silmek istiyorsunuz? ");
                int quantityToRemove = scanner.nextInt();
                scanner.nextLine(); // Dummy read to clear the buffer

                if (quantityToRemove > 0 && quantityToRemove <= currentQuantity) {
                    // Belirtilen miktarda ürünü sepetten kaldır
                    for (int i = 0; i < quantityToRemove; i++) {
                        cart.remove(selectedCartItem);
                    }
                    System.out.println(quantityToRemove + " adet " + selectedCartItem.getName() + " sepetten silindi.");
                } else {
                    System.out.println("Geçersiz miktar!");
                }
            } else {
                System.out.println("Geçersiz seçim!");
            }
        }
    }

    // Belirli bir ürünün sepet içindeki adetini döndüren yardımcı bir metod
    static int getItemCount(MenuItem item) {
        int count = 0;
        for (MenuItem cartItem : cart) {
            if (cartItem.equals(item)) {
                count++;
            }
        }
        return count;
    }

    static void FAQ() {
        System.out.println("----------------------Sıkça Sorulan Sorular----------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("1) Nasıl üye olabilirim?");
        System.out.println("2) Daha önce üye olmuştum nasıl giriş yapabilirim?");
        System.out.println("3) Nasıl sipariş verebilirim");
        System.out.println("4) Rezervasyon yapmak istiyorum nasıl yapapabilirim?");
        System.out.println("5) Sepetimde ürünleri ve sipariş tutarımı nasıl görebilirim");
        System.out.println("6) Sepetime yanlış ürün ekledim nasıl silebilirim?");
        System.out.println("7) Ödeme seçenekleri neler ve nasıl ödeme yapabilirim?");
        System.out.println("8) Bilgilerimi doğru girdiğimden emin değilim nasıl kontrol edebilirim?");
        System.out.println("9) Saat kaça kadar sipariş verebilirim?");
        System.out.println("10) Kart bilgimi vermek istemediğim durumda ödememi nasıl yapabilirim?");
        System.out.println("11) Siparişimle ilgili sorun yaşıyorum sizlere nasıl ulaşabilirim?");
        while (true) {
            System.out.print("Lütfen cevabını görmek istediğiniz soru seçimini yapınız (0 ile çıkınız): ");
            int question = scanner.nextInt();

            if (question == 0)
                break;

            switch (question) {
                case 1:
                    System.out.println("Kodu açtığınızda karşınıza çıkan ilk ekranda 1'i tuşlayarak isim, soyisim, kullanıcı adı, şifre bilgilerinizi girerek başarılı bir şekilde kayıt olabilirsiniz.\n");
                    break;
                case 2:
                    System.out.println("Giriş ekranında 2'yi tuşlayıp kullanıcı adınız ve şifrenizi yazdığınızda giriş yapacaktır.\n");
                    break;
                case 3:
                    System.out.println("Hesap oluşturup ya da kayıt olduktan sonra çıkan ekranda 2'yi tuşlayarak sipariş verme ekranını görebilirsiniz. Daha sonra sizden istenen sipariş bilgilerinizi girdiğinizde siparişiniz oluşturulmaktadır.\n");
                    break;
                case 4:
                    System.out.println("Hesap oluşturduktan ya da hesabınız varsa giriş yaptıktan sonra çıkan ekranda 6'yı tuşlayarak rezervasyon yaptırabilirsiniz. Rezervasyon bilgilerinizi eksiksiz ve doğru girdiğinizden emin olmalısınız.\n");
                    break;
                case 5:
                    System.out.println("Sipariş verdikten sonra sipariş ekranından çıkış yapıp 3'ü tuşladığınızda sipariş tutarınzı ve sepetinizdeki ürünler görüntülenebilmektedir.\n");
                    break;
                case 6:
                    System.out.println("Sipariş verdikten sonra 4'ü tuşlayarak sepetten ürün seçme bölümüne gidebilirsiniz. Buradan silmek istediğiniz ürünü tuşlama yöntemiyle başarılı bir şekilde silebilirsiniz.\n");
                    break;
                case 7:
                    System.out.println("Ödeme ekranına 8'i tuşlayarak gidebilirsiniz. Ödeme seçeneklerimiz ise nakit, kredi kartı ve kripto paradır. Doğru tuşlama yaptığınızdan emin olun.\n");
                    break;
                case 8:
                    System.out.println("Giriş yaptıktan ya da kayıt olduktan sonra bilgilerinizi doğru girdiğinizden emin değilseniz giriş menüsünde 1'i tuşlayabilirsiniz.\n");
                    break;
                case 9:
                    System.out.println("Restoranımız 'Her insanın her an acıkmaya hakkı vardır.' prensibinden yola çıkarak haftanın tüm günleri 7/24 açıktır.\n");
                    break;
                case 10:
                    System.out.println("Sipariş verdikten sonra ödeme kısmında nakit ödemeyi seçerseniz kart bilgilerinize gerek kalmadan ödeme yapabilirsiniz.\n");
                    break;
                case 11:
                    System.out.println("Ana menüdeki seçeneklerden 9'u yani 'Hakkımızda' kısmını tuşladığınız takdirde şubelerimizin iletişim numaraları görüntülenebilmektedir.\n");
                    break;
                default:
                    System.out.println("Hatalı tuşlama! Lütfen doğru tuşlama yaptığınızdan emin olunuz!\n");
            }
        }
    }

    static void showPopularMenus() {
        Map<MenuItem, Integer> orderCounts = new HashMap<>(); // Menülerin sipariş sayılarını tutacak bir harita

        // Tüm siparişleri tarayarak sipariş edilen menülerin sayısını hesapla
        for (MenuItem item : cart) {
            orderCounts.put(item, orderCounts.getOrDefault(item, 0) + 1);
        }

        // Sipariş sayısına göre menüleri sırala
        List<Map.Entry<MenuItem, Integer>> popularMenus = new ArrayList<>(orderCounts.entrySet());
        popularMenus.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Popüler menülerin listesini göster
        System.out.println("Popüler Menüler:");
        System.out.println("--------------------------------------------------");
        System.out.printf("%-4s%-25s%-20s%n", "Sıra", "Menü", "Sipariş Sayısı");
        System.out.println("--------------------------------------------------");
        for (int i = 0; i < Math.min(3, popularMenus.size()); i++) { // En fazla 3 popüler menüyü göster
            MenuItem menuItem = popularMenus.get(i).getKey();
            int orderCount = popularMenus.get(i).getValue();
            System.out.printf("%-4d%-25s%-20d%n", (i + 1), menuItem.getName(), orderCount);
        }
        System.out.println("--------------------------------------------------");
    }

    static void makePayement() {
        System.out.println("1) Nakit ödeme");
        System.out.println("2) Kredi kartı");
        System.out.println("3) Kripto para");
        System.out.println("4) Geri dön");
        System.out.print("Lütfen Ödeme Türünüzü seçiniz: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                handleCashPayment();
                break;
            case 2:
                CreditCartPayment();
                break;
            case 3:
                CryptoPayment();
                break;
            case 4:
                System.out.println("Ana menüye dönülüyor...");
                return;
            default:
                System.out.println("Geçersiz seçim. Lütfen geçerli bir seçim seçiniz");
        }
    }

    static void handleCashPayment() {
        System.out.println("Nakit ödeme seçildi");
    }

    static void CreditCartPayment() {
        System.out.println("Kredi kartı ile ödeme seçildi");
    }

    static void CryptoPayment() {
        System.out.println("Kripto para adreslerimiz.");
        System.out.println("Bitcoin adresimiz: 1N5KUkUHhUuJ6RgVXa3s2Q3xbzGh8XxQh8");
        System.out.println("Ethereum adresmiz: 0x8a03b9Cc34eC3B2AEd0b18Cec3Eba7e1c2A859F2");
    }

    static void displayCategories() {
        for (int i = 0; i < menus.size(); i++) {
            System.out.println((i + 1) + ") " + getCategoryName(i));
        }
    }

    static void displayMenuForCategory(int categoryIndex) {
        List<MenuItem> category = menus.get(categoryIndex);
        for (int j = 0; j < category.size(); j++) {
            MenuItem item = category.get(j);
            System.out.println("\t" + (j + 1) + ". " + item.getName() + " - " + item.getPrice() + " TL");
        }
    }

    static int getCategoryCount() {
        return menus.size();
    }

    static int getMenuCountForCategory(int categoryIndex) {
        return menus.get(categoryIndex).size();
    }

    static String getCategoryName(int index) {
        switch (index) {
            case 0:
                return "Kahvaltıklar";
            case 1:
                return "Kebaplar";
            case 2:
                return "Çorbalar";
            case 3:
                return "Mezeler";
            case 4:
                return "Salatalar";
            case 5:
                return "Ara Sıcaklar";
            case 6:
                return "Tatlılar";
            case 7:
                return "İçecekler";
            default:
                return "Bilinmeyen Kategori";
        }
    }

    static MenuItem getMenuItemAt(int categoryIndex, int menuItemIndex) {
        return menus.get(categoryIndex).get(menuItemIndex);
    }
}

class User {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate;
    private String ccv;
    private String address;
    private String apartmentName;
    private String apartmentNumber;

    public User(String name, String surname, String username, String password) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}

class MenuItem {
    private String name;
    private double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
