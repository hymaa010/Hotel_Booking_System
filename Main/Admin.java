class Admin extends User{
    static{
        User.addUser(new Admin("2200748@eng.asu.edu.eg","Mazen Mohamed","1234"));
        User.addUser(new Admin("2200502@eng.asu.edu.eg","Ziad Tamer","1234"));
        User.addUser(new Admin("2200614@eng.asu.edu.eg","Ibrahim Atef","1234"));
    }
    private Admin(String email,String name,String password){
        setAdmin(true);
        setEmail(email);
        setName(name);
        setPassword(password);
    }
    public static User login(String email,String password,Boolean isAdmin){
        for (User u : User.getUsers()){
            if(u.getEmail().equals(email) && isAdmin  && u.isAdmin() )
            {
                if(u.getPassword().equals(password))
                {
                    System.out.println("Welcome "+u.getName());
                    return u;
                }
                else{
                    System.out.println("Wrong password try again");
                    return null;
                }
            }
        }
        System.out.println("email not exist");
        return null;
    }

    public void roomPrice(String type, double price) {
        Room room ;
        switch (type) {
            case "Single":
                room = new SingleRoom();
                room.setPrice(price);
                room.addRoom();
                break;
            case "Double":
                room = new DoubleRoom();
                room.setPrice(price);
                room.addRoom();
                break;
            case "Suite":
                room = new Suite();
                room.setPrice(price);
                room.addRoom();
                break;
        }
    }
    public void data(){
        Booking.printAllBookings();
    }
    public static void addPromoCode(String code, Double value) {
        Offer.promoCodes.put(code, value);
    }
    public static void removePromoCode(String code){
        Offer.promoCodes.remove(code);
    }
}