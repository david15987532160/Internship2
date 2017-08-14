package com.example.quocanhnguyen.sqliteimage;

public class Item {
    private int Id;
    private String Name;
    private String Description;
    private byte[] Image;

    public Item(int id, String name, String description, byte[] image) {
        Id = id;
        Name = name;
        Description = description;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
}
