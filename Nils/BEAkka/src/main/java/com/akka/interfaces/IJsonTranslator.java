package com.akka.interfaces;

/**
 * Created by kobi on 4/7/14.
 */
public interface IJsonTranslator<ST>{

    ST encode(Object source);

    /**
     * Decodes the target format into a CouchbaseDocument
     *
     * @param source the source formatted document.
     * @return a properly populated document to work with.
     */

    Object decode(ST source, Class clazz);


}
