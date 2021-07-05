package com.geemi.facelibrary.ui.ar;

public class ARBean {
    String name;
    String path;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ARBean{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
