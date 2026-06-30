public class Book {
    private String title;

    private Book(String title){
        this.title= title;
    }

    public static Book titleof(String title){
        return new Book(title);
    }
}
