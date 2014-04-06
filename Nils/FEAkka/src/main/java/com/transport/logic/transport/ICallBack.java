package com.transport.logic.transport;


import com.nils.entities.transport.Response;
import com.nils.entities.transport.Error;

/**
 * Created by uri.silberstein on 4/2/14.
 */
public interface ICallBack {

    void onResponse(Response response);

    void onError(Error error);

}
