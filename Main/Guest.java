public class Guest extends User{

    public Guest(){
        setAdmin(false);
    }
    public static User login(String email,String password,Boolean isAdmin){
        for (User u : User.getUsers()){
            if(u.getEmail().equals(email)  && !isAdmin && !u.isAdmin())
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
    public void signup (String email,String name,String password,String phoneNumber) throws IllegalArgumentException{
        for (User u : User.getUsers()){
            if(u.getEmail().equals(email))
            {
                System.out.println("Email already exist");
                throw new IllegalArgumentException("email already exist");
            }
        }
        setEmail(email);
        setName(name);
        setPassword(password);
        setPhoneNumber(phoneNumber);
        User.addUser(this);
        System.out.println("Signup successful");
    }
    public void data(){
        System.out.println(getName()+" "+getEmail());
    }
}