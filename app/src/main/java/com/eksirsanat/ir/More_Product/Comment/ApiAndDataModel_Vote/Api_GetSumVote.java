package com.eksirsanat.ir.More_Product.Comment.ApiAndDataModel_Vote;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eksirsanat.ir.Action.Request_Volley;
import com.eksirsanat.ir.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Api_GetSumVote implements Config {

    public static final  void GetApiVote_Sum(final Context context, String idproduct, final List<DataModel_GetVote> listName, final GetList_Vote_Star getList_vote_star){

        final List<Float> EachStar=new ArrayList<>();
        String url=urlHome+"get_avrage_vote.php?idproduct="+idproduct;

        Log.i("urlVote",url);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    double  getAllStar;
                    int  allUser;

                    if (response.has("status")){




                        getAllStar=response.getDouble("avrage-all-stars");
                        allUser=response.getInt("count-all-user");

                   /* if (!response.isNull("avrage-all-stars")  ){
                    }

                    if (!response.isNull("count-all-user")){
                    }*/

                        JSONObject vote2=response.getJSONObject("vote2");

                        for (int i = 0; i <listName.size() ; i++) { //because get name

                            JSONArray nameoject=vote2.getJSONArray(listName.get(i).getName());
                            float count=nameoject.length();
                            float sumCount=0;

                            for (int j = 0; j <nameoject.length() ; j++) {
                                JSONObject voteobject=nameoject.getJSONObject(j);
                                int changeToInt=Integer.parseInt(voteobject.getString("vote"));
                                sumCount+=changeToInt;

                            }
                            sumCount=sumCount/count;
                            EachStar.add(sumCount);

                        }


                        getList_vote_star.ListVoteStar(EachStar);
                        getList_vote_star.getStarAndCountUser(allUser,getAllStar);

                    }else if (response.has("error")){
                        getList_vote_star.ListVoteStar(null);
                        getList_vote_star.getStarAndCountUser(0,0);
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);
    }




    public static final  void GetCommentAllUser(final Context context, String idproduct, final getCommentAllUser getCommentAllUser){

        String url=urlHome+"get_avrage_vote.php?idproduct="+idproduct;
        final List<DataModel_AllCommentUser> list=new ArrayList<>();

        Log.i("urlVote",url);

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (response.has("status")) {

                        JSONArray all_Vote = response.getJSONArray("all-vote");
                        if (all_Vote.length() > 0) {

                            for (int i = 0; i <all_Vote.length() ; i++) {
                                JSONObject js=all_Vote.getJSONObject(i);
                                DataModel_AllCommentUser model=new DataModel_AllCommentUser();
                                model.setIdVote(js.getString("id"));
                                model.setIdUser(js.getString("iduser"));
                                model.setStars(js.getDouble("stars"));
                                model.setDate(js.getString("date"));

                                if (!js.isNull("mosbat") && !js.isNull("manfi") ){
                                    model.setMosbat(js.getString("mosbat"));
                                    model.setManfi(js.getString("manfi"));
                                }

                                if (!js.isNull("usercomment")){
                                    model.setComment(js.getString("usercomment"));
                                }
                                if (!js.isNull("name")){
                                    model.setName(js.getString("name"));
                                }

                                list.add(model);
                                getCommentAllUser.getAllComment(list);

                            }
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);
    }








    public static final  void GetComment_MoreUser(final Context context, String idproduct, String idComment, final GetMore_CommentUser getMore_commentUser){

        String url=urlHome+"get_avrage_vote.php?idproduct="+idproduct+"&idcomment="+idComment;

        final List<Float> listStarUser=new ArrayList<>();


        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(0, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    if (response.has("OneUserStar")) {

                        JSONArray stars = response.getJSONArray("OneUserStar");
                        if (stars.length() > 0) {

                            for (int i = 0; i <stars.length() ; i++) {
                                JSONObject js=stars.getJSONObject(i);
                                double vote=js.getDouble("vote");
                                //float voteFloat=(float)vote;
                                float voteFloat2=Float.parseFloat(String.valueOf(vote));
                                listStarUser.add(voteFloat2);
                            }

                            getMore_commentUser.getVote(listStarUser);
                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //Volley.newRequestQueue(context).add(jsonObjectRequest);
        Request_Volley.getInstance(context).add(jsonObjectRequest);
    }





    public interface GetList_Vote_Star{
        void ListVoteStar(List<Float> EachStar);
        void  getStarAndCountUser(int CountUser,double AllStar);
    }

    public interface getCommentAllUser{
        void getAllComment(List<DataModel_AllCommentUser> list);
    }


    public interface GetMore_CommentUser{
        void getVote(List<Float> listStarUser);
    }



}
