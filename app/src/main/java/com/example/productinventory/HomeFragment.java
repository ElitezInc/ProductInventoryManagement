package com.example.productinventory;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    public static final String TAG ="TAG";
    RecyclerView post_list;

    PostsAdapter adapter;
    GridLayoutManager manager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity.posts = new ArrayList<>();

        post_list = view.findViewById(R.id.post_list);

        extractPosts("/products");

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            manager = new GridLayoutManager(requireContext(), 3);
        else
            manager = new GridLayoutManager(requireContext(), 2);
        post_list.setLayoutManager(manager);
        adapter = new PostsAdapter(MainActivity.posts, position -> {
            Log.d("Debug", "on click: " + position);

            //back to Stack from bottom
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,
                    new Details(MainActivity.posts.get(position))).addToBackStack("Details").commit();
        });
        post_list.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void extractPosts(String endpoint){
        //use volley to extract the date

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MainActivity.getOauth1Url(endpoint, "per_page=100", "GET", requireContext()), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "onResponse: " + response.toString());
                for (int i = 0; i < response.length(); i++) {

                    try {
                        Post p = new Post();

                        JSONObject jsonObjectData = response.getJSONObject(i);

                        //extract the data
                        p.setDate(jsonObjectData.getString("date_created"));

                        //extract the title
                        p.setTitle(jsonObjectData.getString("name"));

                        //extract the content
                        p.setContent(jsonObjectData.getString("description"));

                        //extract feature image

                        JSONArray arr =  jsonObjectData.getJSONArray("images");
                        List<String> list = new ArrayList<>();
                        for(int x = 0; x < arr.length(); x++){
                            list.add(arr.getJSONObject(x).getString("src"));
                        }
                        p.setImages(list.toArray(new String[] {}));

                        MainActivity.posts.add(p);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, error ->  error.printStackTrace());

        queue.add(request);
    }
}