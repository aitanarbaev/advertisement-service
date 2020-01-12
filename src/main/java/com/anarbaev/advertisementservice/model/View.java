package com.anarbaev.advertisementservice.model;

import java.io.Serializable;

public class View implements Serializable {
    public interface MainFields {
    }

    public interface MainFieldsWithDesc extends MainFields {
    }

    public interface MainFieldsWithPhotos extends MainFields {
    }

    public interface AllFields extends MainFieldsWithDesc {
    }
}
