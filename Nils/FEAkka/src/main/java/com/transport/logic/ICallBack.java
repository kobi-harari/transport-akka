package com.transport.logic;


import com.nils.entities.transport.Response;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface ICallBack {

    void onResponse(Response response);

    void onError(Error error);

}
