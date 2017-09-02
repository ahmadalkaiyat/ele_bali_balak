package Retrofit;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetCategoriesResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetContactUsDetails;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetGeneralresponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopBreanchesResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopItemsResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopSectionsResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetTrindingResponse;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetShopContactDetails;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.Model.GetUserShopRate;
import www.alkaiyat.ahmad.net.ahmadalkaiyats.User;

/**
 * Created by Ahmad Alkaiyat on 1/30/2016.
 */
public interface ActivitiesRestInterface {

    @Multipart
    @POST("/Register.php")
    void register(@Part("username") String username, @Part("password") String password,
                  @Part("first_name") String first_name, @Part("last_name") String last_name,
                  @Part("email_address") String email_address, @Part("phone_number") String phone_number,
                  @Part("country") String country, @Part("status") int status

            , Callback<User> callback);

    @Multipart
    @POST("/login.php")
    void loginUser(@Part("username") String username, @Part("password") String password, Callback<User> callback);

    /*added on 26122016 for the categories */


    @GET("/categories.PHP")
    void categories(Callback<GetCategoriesResponse> cb);

    @Multipart
    @POST("/Categories_with_name.PHP")
    void get_Categories_withname(@Part("CatName") String CatName, Callback<GetCategoriesResponse> cb);

    @Multipart
    @POST("/trending.PHP")
    void get_trending(@Part("user_id") String user_id, Callback<GetTrindingResponse> cb);


    @Multipart
    @POST("/trending_with_name.PHP")
    void get_trending_withname(@Part("user_id") String user_id,@Part("Shopname") String Shopname, Callback<GetTrindingResponse> cb);

    @Multipart
    @POST("/my_list.PHP")
    void get_my_list(@Part("user_id") String user_id, Callback<GetTrindingResponse> cb);

    @Multipart
    @POST("/my_list_with_name.PHP")
    void get_my_list_withname(@Part("user_id") String user_id, @Part("Shopname") String Shopname, Callback<GetTrindingResponse> cb);

    @Multipart
    @POST("/add_to_my_list.PHP")
    void add_to_my_list(@Part("user_id") String user_id, @Part("shop_id") String shop_id, Callback<GetGeneralresponse> cb);

    @Multipart
    @POST("/delete_from_my_list.PHP")
    void delete_from_my_list(@Part("user_id") String user_id, @Part("shop_id") String shop_id, Callback<GetGeneralresponse> cb);

    @Multipart
    @POST("/insideCaegory.PHP")
    void insideCaegory(@Part("user_id") String user_id,@Part("CatId") String CatId,Callback<GetTrindingResponse> cb);


    @Multipart
    @POST("/insideCaegory_WithName.PHP")
    void insideCaegory_WithName(@Part("user_id") String user_id,@Part("Shopname") String Shopname,@Part("CatId") String CatId, Callback<GetTrindingResponse> cb);

    @Multipart
    @POST("/get_shop_sections.PHP")
    void getShopSections(@Part("shop_id") String shop_id,Callback<GetShopSectionsResponse> cb);

    @Multipart
    @POST("/get_shop_details.PHP")
    void getshopAbout(@Part("Shop_id") String Shop_id,Callback<GetShopContactDetails> cb);

    @Multipart
    @POST("/add_contactus_details.php")
    void add_contact_us(@Part("shop_id") String shop_id
            , @Part("user_name") String user_name, @Part("user_number") String user_number
            , @Part("user_email") String user_email, @Part("c_message") String c_message,
                        Callback<GetGeneralresponse> cb);



    @GET("/get_ContactUs.PHP")
    void getContactUsDetails(Callback<GetContactUsDetails> cb);

    @Multipart
    @POST("/add_contactus_details.php")
    void ContactUs(
             @Part("user_name") String user_name, @Part("user_number") String user_number
            , @Part("user_email") String user_email, @Part("c_message") String c_message,
                        Callback<GetGeneralresponse> cb);


    @Multipart
    @POST("/get_user_shop_rate.PHP")
    void get_user_shop_rate (@Part("Shop_id") String Shop_id,@Part("User_id") String User_id,Callback<GetUserShopRate> cb);

    @Multipart
    @POST("/rate_shop_from_user.PHP")
    void rate_shop_from_user (@Part("Shop_id") String Shop_id,@Part("User_id") String User_id,@Part("rate") String rate
            ,@Part("rate_note") String rate_note,Callback<GetGeneralresponse> cb);

    @Multipart
    @POST("/get_shop_branches.PHP")
    void get_shop_branches(@Part("Shop_id") String Shop_id,Callback<GetShopBreanchesResponse> cb);

    @Multipart
    @POST("/getShopItems.PHP")
    void get_shopItems(@Part("shop_id") String shop_id,@Part("section_id") String section_id, Callback<GetShopItemsResponse> cb);

}
