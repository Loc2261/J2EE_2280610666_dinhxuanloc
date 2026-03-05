import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Book> listBook = new ArrayList<>();
        Scanner x = new Scanner(System.in);
        String msg = """
                Chuong trinh quan ly sach
                    1. Them 1 cuon sach
                    2. Xoa 1 cuon sach
                    3. Thay doi sach
                    4. Xuat thong tin
                    5. Tim sach lap trinh
                    6. Loc sach theo gia toi da va gioi han so luong
                    7. Tim sach theo tac gia
                    0. Thoat
                    Chon chuc nang: """;

        int chon = 0;
        do {
            System.out.print(msg);
            try {
                chon = Integer.parseInt(x.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui long nhap so.");
                continue;
            }

            switch (chon) {
                case 1 -> {
                    Book newBook = new Book();
                    newBook.input();
                    listBook.add(newBook);
                }
                case 2 -> {
                    System.out.print("Nhap ma sach can xoa: ");
                    int bookId = Integer.parseInt(x.nextLine());
                    listBook.removeIf(b -> b.getId() == bookId);
                    System.out.println("Da xoa sach (neu ton tai).");
                }
                case 3 -> {
                    System.out.print("Nhap ma sach can thay doi: ");
                    int bookId = Integer.parseInt(x.nextLine());
                    Optional<Book> found = listBook.stream().filter(b -> b.getId() == bookId).findFirst();
                    found.ifPresent(Book::input);
                }
                case 4 -> {
                    System.out.println("Danh sach sach:");
                    listBook.forEach(Book::output);
                }
                case 5 -> {
                    listBook.stream()
                            .filter(b -> b.getTitle().toLowerCase().contains("lap trinh"))
                            .forEach(Book::output);
                }
                case 6 -> {
                    try {
                        System.out.print("Nhap gia toi da: ");
                        long p = Long.parseLong(x.nextLine());

                        System.out.print("Nhap so luong toi da: ");
                        int k = Integer.parseInt(x.nextLine());

                        listBook.stream()
                                .filter(b -> b.getPrice() <= p)
                                .limit(k)
                                .forEach(Book::output);
                    } catch (NumberFormatException e) {
                        System.out.println("Nhap sai dinh dang so.");
                    }
                }
                case 7 -> {
                    System.out.print("Nhap cac tac gia (cach nhau boi dau phay): ");
                    String input = x.nextLine();
                    Set<String> authorSet = Arrays.stream(input.split(","))
                            .map(String::trim)
                            .map(String::toLowerCase)
                            .collect(Collectors.toSet());

                    listBook.stream()
                            .filter(b -> authorSet.contains(b.getAuthor().toLowerCase()))
                            .forEach(Book::output);
                }
            }
        } while (chon != 0);
    }
}