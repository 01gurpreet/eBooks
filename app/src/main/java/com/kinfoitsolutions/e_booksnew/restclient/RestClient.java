package com.kinfoitsolutions.e_booksnew.restclient;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kinfoitsolutions.e_booksnew.response.AllSearchDataSuccess.AllSearchDataSuccess;
import com.kinfoitsolutions.e_booksnew.response.CategoryResponse.CategorySuccess;
import com.kinfoitsolutions.e_booksnew.response.FilterResponse.FilterSuccess;
import com.kinfoitsolutions.e_booksnew.response.GetAllBooksReponse.GetAllBooksSuccess;

import com.kinfoitsolutions.e_booksnew.response.GetFilterData.GetFilterDataSuccess;
import com.kinfoitsolutions.e_booksnew.response.LibraryResponse.AddBookLibraryRes;
import com.kinfoitsolutions.e_booksnew.response.LibraryResponse.GetBookLibraryResponse;
import com.kinfoitsolutions.e_booksnew.response.LoginResponse.LoginResponse;
import com.kinfoitsolutions.e_booksnew.response.Logout.LogoutResponse;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;

import java.util.HashMap;


public class RestClient {

    private static GitApiInterface gitApiInterface;

    private static String baseUrl = "http://112.196.85.179:9083/ebook/api/";

    public static GitApiInterface getClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        //The logging interceptor will be added to the http client

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();


        gitApiInterface = retrofit.create(GitApiInterface.class);

        return gitApiInterface;



    }


    public interface GitApiInterface {

        @POST("users/logout")
        Call<LogoutResponse> logout(@Body HashMap<String, String> hashMap);

        @POST("users/login")
        Call<LoginResponse> login(@Body HashMap<String, String> hashMap);

        @POST("books/book_by_category")
        Call<CategorySuccess> getBooksByCat(@Body HashMap<String, String> hashMap);


        @POST("books/add_book_library")
        Call<AddBookLibraryRes> addbookslib(@Body HashMap<String, String> hashMap);

        @POST("books/get_user_library")
        Call<GetBookLibraryResponse> getbookslib(@Body HashMap<String, String> hashMap);


        @POST("books/get_filter_data")
        Call<GetFilterDataSuccess> getFilterData(@Body HashMap<String, String> hashMap);


        @POST("books/filter_books")
        Call<FilterSuccess> filter_books(@Body HashMap<String, String> hashMap);

        //Search All Data Api
        @POST("books/search")
        Call<AllSearchDataSuccess> searchAllData(@Body HashMap<String, String> hashMap);



    /*    @POST("users/login")
        Call<LoginResponse> login(@Body HashMap<String, String> hashMap);

        @POST("users/register")
        Call<RegisterResponse> register(@Body HashMap<String, String> hashMap);



        @GET("users/get_profile")
        Call<GetProfileResponse> getprofile(@Query("token") String token);

        @Multipart
        @POST("users/update_profile")
        Call<UpdateProfileResponse> updateProfile(@Part("token") RequestBody token,
                                                  @Part("name") RequestBody name,
                                                  @Part("email") RequestBody email,
                                                  @Part("phone") RequestBody phone,
                                                  @Part MultipartBody.Part image);

        @POST("users/reset_password_request")
        Call<ForgetResponse> forget_password(@Body HashMap<String, String> hashMap);


        @POST("users/verify_otp")
        Call<VerifyOtpResponse> verifyOTp(@Body HashMap<String, String> hashMap);


        @POST("users/reset_password")
        Call<ResetPasswordResponse> reset_password(@Body HashMap<String, String> hashMap);*/


        @POST("books/get_books")
        Call<GetAllBooksSuccess> get_books(@Body HashMap<String, String> hashMap);

/*


        //Search Author Api
        @POST("books/search")
        Call<SearchAuthorsSuccess> searchAuthor(@Body HashMap<String, String> hashMap);

        @POST("books/latest_books")
        Call<LatestBooksSuccess> getLatestBooks(@Body HashMap<String, String> hashMap);


        @POST("books/book_by_author")
        Call<GetAuthorsBooksSuccess> getBooksByAuthors(@Body HashMap<String, String> hashMap);

        @POST("books/book_by_category")
        Call<CategorySuccess> getBooksByCat(@Body HashMap<String, String> hashMap);

        //Search Category Api
        @POST("books/search")
        Call<SearchCatSuccess> searchCategory(@Body HashMap<String, String> hashMap);


        //Get Authors and Category Api
        @POST("books/books_by_filter")
        Call<FilterBooksCatSuccess> booksByFilter(@Body HashMap<String, String> hashMap);


*/

    }
}

