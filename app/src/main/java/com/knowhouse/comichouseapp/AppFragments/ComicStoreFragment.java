package com.knowhouse.comichouseapp.AppFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.knowhouse.comichouseapp.Connectivity.MySingleton;
import com.knowhouse.comichouseapp.Data.Characters;
import com.knowhouse.comichouseapp.Data.ComicSeries;
import com.knowhouse.comichouseapp.Data.Comics;
import com.knowhouse.comichouseapp.Data.Marvel;
import com.knowhouse.comichouseapp.Interfaces.RecyclerViewClickInterface;
import com.knowhouse.comichouseapp.R;
import com.knowhouse.comichouseapp.RecyclerViews.CategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComicStoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComicStoreFragment extends Fragment implements RecyclerViewClickInterface {

    private ArrayList<Marvel> previewList;
    private ArrayList<Marvel> seriesList;
    private ArrayList<Marvel> storyList;
    private RecyclerView linearLayoutRecycler;
    private ArrayList<ArrayList<Marvel>> category;
    private String storyUrl;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ComicStoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComicStoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComicStoreFragment newInstance(String param1, String param2) {
        ComicStoreFragment fragment = new ComicStoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_comic_store, container, false);
        linearLayoutRecycler = view.findViewById(R.id.linearlayout_recycler);
        category = new ArrayList<>();
        updateMyOperation();
        return view;
    }

    private void updateMyOperation() {

        Date date = new Date();
        String url = "http://gateway.marvel.com/";
        String publicKey ="29efec1ad7d18b56c048a30bcf81fc05";
        String privateKey = "ace16bdfbf2ddd0a9f0a7c42900bafb95b13205a";
        String timeStamp = String.valueOf(date.getTime());
        String hashUrl = timeStamp + privateKey + publicKey;
        String hash = hashString(hashUrl);
        String testUrl = url+"v1/public/comics?ts="+timeStamp+"&apikey="+publicKey+"&hash="+hash;
        final String comicSeriesUrl = url+"v1/public/series?ts="+timeStamp+"&apikey="+publicKey+"&hash="+hash;
        storyUrl = url+"v1/public/characters?ts="+timeStamp+"&apikey="+publicKey+"&hash="+hash;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, testUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        retrieveData(response);
                        comicSeriesJsonRequest(comicSeriesUrl);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        jsonObjectRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);

            }

    private void comicSeriesJsonRequest(String comicSeriesUrl){
        JsonObjectRequest comicSeriesJsonRequest = new JsonObjectRequest
                (Request.Method.GET, comicSeriesUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        retrieveComicSeries(response);
                        characterJsonRequest(storyUrl);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        comicSeriesJsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(comicSeriesJsonRequest);

    }

    private void characterJsonRequest(String comicStoryUrl){
        JsonObjectRequest comicSeriesJsonRequest = new JsonObjectRequest
                (Request.Method.GET, comicStoryUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        retrieveStories(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        comicSeriesJsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {
                error.printStackTrace();
            }
        });

        MySingleton.getInstance(getContext()).addToRequestQueue(comicSeriesJsonRequest);
    }

    private String hashString(String md5){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (byte b : array) {
                sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return null;
    }

    private void retrieveData(JSONObject response){
        try {
            String status = response.getString("status");
            if(status.equals("Ok")){
                JSONObject data = response.getJSONObject("data");
                JSONArray results = data.getJSONArray("results");
                String resourceUrl = null;
                int count = data.getInt("count");
                previewList = new ArrayList<>();
                for(int i = 0; i<count;i++){
                    JSONObject arrayObj = results.getJSONObject(i);
                    JSONArray url = arrayObj.getJSONArray("urls");
                    JSONObject arraylist = url.getJSONObject(0);
                    resourceUrl = arraylist.getString("url");

                    String id = arrayObj.getString("id");
                    String title = arrayObj.getString("title");
                    JSONObject thumbnail = arrayObj.getJSONObject("thumbnail");
                    String path = thumbnail.getString("path");
                    String extension = "."+thumbnail.getString("extension");
                    String imageVariant = "/"+"portrait_incredible";
                    String imageUrl = path+imageVariant+extension;
                    Marvel preview = new Comics(id,imageUrl,title,resourceUrl);
                    previewList.add(preview);
                }
                category.add(previewList);
                populateView();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retrieveComicSeries(JSONObject response){
        try {
            String status = response.getString("status");
            if(status.equals("Ok")){
                JSONObject data = response.getJSONObject("data");
                JSONArray results = data.getJSONArray("results");
                String resourceUrl = null;
                int count = data.getInt("count");
                seriesList = new ArrayList<>();
                for(int i = 0; i<count;i++){
                    JSONObject arrayObj = results.getJSONObject(i);
                    JSONArray url = arrayObj.getJSONArray("urls");
                    JSONObject arraylist = url.getJSONObject(0);
                    resourceUrl = arraylist.getString("url");
                    String id = arrayObj.getString("id");
                    String title = arrayObj.getString("title");
                    JSONObject thumbnail = arrayObj.getJSONObject("thumbnail");
                    String path = thumbnail.getString("path");
                    String extension = "."+thumbnail.getString("extension");
                    String imageVariant = "/"+"portrait_incredible";
                    String imageUrl = path+imageVariant+extension;
                    Marvel series = new ComicSeries(id,imageUrl,title,resourceUrl);
                    seriesList.add(series);
                }

                category.add(seriesList);
                populateView();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void retrieveStories(JSONObject response){
        try {
            String status = response.getString("status");
            String resourceUrl = null;
            if(status.equals("Ok")){
                JSONObject data = response.getJSONObject("data");
                JSONArray results = data.getJSONArray("results");
                int count = data.getInt("count");
                storyList = new ArrayList<>();
                for(int i = 0; i<count;i++){
                    JSONObject arrayObj = results.getJSONObject(i);
                    JSONArray url = arrayObj.getJSONArray("urls");
                    JSONObject arraylist = url.getJSONObject(0);
                    resourceUrl = arraylist.getString("url");
                    String id = arrayObj.getString("id");
                    String title = arrayObj.getString("name");
                    JSONObject thumbnail = arrayObj.getJSONObject("thumbnail");
                    String path = thumbnail.getString("path");
                    String extension = "."+thumbnail.getString("extension");
                    String imageVariant = "/"+"portrait_incredible";
                    String imageUrl = path+imageVariant+extension;
                    Marvel characters = new Characters(id,imageUrl,title,resourceUrl);
                    storyList.add(characters);
                }

                category.add(storyList);
                populateView();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void populateView(){

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        CategoryAdapter categoryAdapter = new CategoryAdapter(category,getActivity(), this);
        linearLayoutRecycler.setAdapter(categoryAdapter);
        linearLayoutRecycler.setLayoutManager(layoutManager);
    }

    @Override
    public void onItemClick(int position, String url, View view) {
        Bundle bundle = new Bundle();
        bundle.putString("comicUrl",url);
        Navigation.findNavController(view).navigate(R.id.webViewFragment,bundle);
    }
}