package com.siolabs.ashutoshsingh.p1_popularmovies.comms.bus;

/**
 * Created by ashutoshsingh on 16-03-2016.
 */

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;


public final class BusProvider {

    //to run on any thred
    private static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance(){
        return BUS;
    }

    private BusProvider(){}


}