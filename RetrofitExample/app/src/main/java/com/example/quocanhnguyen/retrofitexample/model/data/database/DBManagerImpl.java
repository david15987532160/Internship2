package com.example.quocanhnguyen.retrofitexample.model.data.database;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.quocanhnguyen.retrofitexample.model.data.prefs.SharedPrefs;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiClient;
import com.example.quocanhnguyen.retrofitexample.model.data.rest.ApiInterface;
import com.example.quocanhnguyen.retrofitexample.model.detail.MovieDetails;
import com.example.quocanhnguyen.retrofitexample.model.movie.Movie;
import com.example.quocanhnguyen.retrofitexample.model.movie.MoviesResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DBManagerImpl implements DBManager {

    private List<Movie> movies;
    private MovieDetails movieDetails;
    private List<MovieDetails> list = new ArrayList<>();

    @Override
    public void Login(final String username, final String password, final onLoginFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(username)) {
                    listener.onUsernameError();
                    error = true;
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    error = true;
                    return;
                }
                if (username.equals(SharedPrefs.USERNAME_SIGNUP) && password.equals(SharedPrefs.PASSWORD_SIGNUP)) {
                    listener.onSuccess();
                } else {
                    listener.onIncorrectUsernameorPassword();
                    error = true;
                    return;
                }
                if (!error)
                    listener.onSuccess();
            }
        }, 2000);
    }

    @Override
    public void SignUp(final String username, final String password, final String confirm, final onSignUpFinished listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean error = false;
                if (TextUtils.isEmpty(username)) {
                    listener.onUsernameError();
                    error = true;
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                    error = true;
                    return;
                }
                if (TextUtils.isEmpty(confirm)) {
                    listener.onConfirmError();
                    error = true;
                    return;
                }
                if (!confirm.equals(password)) {
                    listener.onConfirmNotMatch();
                    error = true;
                    return;
                }
                if (!error) {
                    listener.onSuccess();
                }
            }
        }, 2000);
    }

    @Override
    public void findTop_ratedMovieItems(final onFinishedTop_ratedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getTopRatedMovies(SharedPrefs.API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        movies = response.body().getResults();
                        listener.onFinishedTop_rated(movies);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e("Error!!!", t.toString());
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void findUpcomingMovieItems(final onFinishedUpcomingListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getUpcomingMovies(SharedPrefs.API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        movies = response.body().getResults();
                        listener.onFinishedUpcoming(movies);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e("Error!!!", t.toString());
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void findPopularMovieItems(final onFinishedPopularListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesResponse> call = apiInterface.getPopularMovies(SharedPrefs.API_KEY);
                call.enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        movies = response.body().getResults();
                        listener.onFinishedPopular(movies);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        Log.e("Error!!!", t.toString());
                    }
                });
            }
        }, 2000);
    }

    @Override
    public void findFavoriteMovieItems(final onFinishedFavoriteListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (SharedPrefs.ID.isEmpty()) {
//                    for (int i = 0; i < ID.size(); ++i) {
//                        SharedPrefs.ID.add(new String(ID.get(i)));
//                    }
//                }
                final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                for (int i = 0; i < SharedPrefs.ID.size(); ++i) {
                    Call<MovieDetails> detailsCall = apiInterface.getMovie_Details(Integer.parseInt(SharedPrefs.ID.get(i)), SharedPrefs.API_KEY);
                    detailsCall.enqueue(new Callback<MovieDetails>() {
                        @Override
                        public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                            movieDetails = response.body();
                            list.add(movieDetails);
                            listener.onFinishedFavorite(list);
                            writeToFile(String.valueOf(movieDetails.getId()));
                        }

                        @Override
                        public void onFailure(Call<MovieDetails> call, Throwable t) {
                            Log.e("Error!", t.toString());
                        }
                    });
                }
            }
        }, 2000);
//        for (int i = 0; i < SharedPrefs.ID.size(); ++i) {
//            ID.add(new String(SharedPrefs.ID.get(i)));
//        }
    }

    @Override
    public void writeToFile(String data) {
        try {
            File f = new File("D:\\QuocAnhPham\\ID.txt");
            if (!f.exists())
                f.mkdirs();
            FileWriter fileWriter = new FileWriter(f);

            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            Log.e("Error!!!", "Fail to write file: " + e.toString());
        }
//        try {
//            FileOutputStream fos = new FileOutputStream("D:\\QuocAnhPham\\MiloNescau\\RetrofitExample\\app\\src\\main\\assets\\ID.txt");
//            DataOutputStream dos = new DataOutputStream(fos);
//
//            dos.write(Integer.parseInt(data));
//
//            fos.close();
//            dos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e("Error!!!", "Fail to write file: " + e.toString());
//        }
        /*File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!path.exists())
            path.mkdirs();
        File file = new File(path, "ID.txt");

        try {
            file.createNewFile();
            FileOutputStream fout = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fout);
            outputStreamWriter.write(data);
            outputStreamWriter.close();

            fout.flush();
            fout.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error!!!", "Fail to write file: " + e.toString());
        }*/
//        try {
//            OutputStreamWriter outputStream;
//            outputStream = new OutputStreamWriter(context.openFileOutput("ID.txt", Context.MODE_PRIVATE));
//            outputStream.write(data);
//            outputStream.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            Log.e("Error!!!", "Fail to write file: " + e.toString());
//        }
    }

    @Override
    public String readFromFile(Context context) {
        String result = "";

        try {
            InputStream inputStream = context.openFileInput("ID.txt");

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferReader = new BufferedReader(inputStreamReader);
                String content = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((content = bufferReader.readLine()) != null) {
                    stringBuilder.append(content);
                }
                inputStream.close();
                result = stringBuilder.toString();
            }
        } catch (FileNotFoundException e) {
            Log.e("Error!!!", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("Error!!!", "Can not read file: " + e.toString());
        }

        return result;
    }
}