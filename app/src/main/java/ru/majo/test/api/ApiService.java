package ru.majo.test.api;


import java.util.ArrayList;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.majo.test.api.model.User;
import rx.Observable;

/**
 * Created by Majo on 28.10.2017.
 */

public interface ApiService {

    @GET("users.json")
    Observable<ArrayList<User>> getUsers();

    @POST("users.json")
    Observable<User> createUser(@Body User user);

    @PATCH("users/{id}.json")
    Observable<User> editUser(@Path("id") long id, @Body User user);
}
