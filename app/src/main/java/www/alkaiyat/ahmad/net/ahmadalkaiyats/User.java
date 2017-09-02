package www.alkaiyat.ahmad.net.ahmadalkaiyats;

/**
 * Created by Ahmad Alkaiyat on 1/22/2016.
 */
public class User {
    String username,password,message,first_name,last_name,email_address,phone_number,country;
    int success,status,user_id;

    public User(String username,String password){
        this.username=username;
        this.password = password;
        this.user_id = user_id;
    }
    public User(String username,String password,String first_name,
                String last_name,String email_address,String phone_number,String country,int status){
        this.username=username;
        this.password = password;
        this.first_name=first_name;
        this.last_name=last_name;
        this.email_address=email_address;
        this.phone_number=phone_number;
        this.country=country;
        this.status=status;
        this.user_id = user_id;
    }
}
