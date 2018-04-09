package br.com.alexandrenavarro.store_mvvm_live.data.source.remote;

import java.util.List;

import br.com.alexandrenavarro.store_mvvm_live.data.App;

public class Result {

    public Responses responses;

    public class Responses{

        public ListApp listApps;

        public class ListApp{
            public DataSet datasets;
        }
    }

    public class All{
        public Data data;
    }

    public class Data{
        public List<App> list;
    }

    public class DataSet{
        public All all;
    }
}