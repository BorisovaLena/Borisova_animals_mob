package com.example.borisova_animals;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Animal implements Parcelable {

    private int ID;
    private String Title;
    private String Kingdom;
    private String Type;
    private String Class;
    private String Detachment;
    private String Family;
    private String Image;

    public Animal(int ID, String title, String kingdom, String type, String clas, String detachment, String family, String image)
    {
        this.ID = ID;
        this.Title = title;
        this.Kingdom = kingdom;
        this.Type = type;
        this.Class = clas;
        this.Detachment = detachment;
        this.Family = family;
        this.Image = image;
    }

    protected Animal(Parcel in)
    {
        ID = in.readInt();
        Title = in.readString();
        Kingdom = in.readString();
        Type = in.readString();
        Class = in.readString();
        Detachment = in.readString();
        Family = in.readString();
        Image = in.readString();
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
        }
    };

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setKingdom(String kingdom) {
        Kingdom = kingdom;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setClass(String clas) {
        Class = clas;
    }

    public void setDetachment(String detachment) {
        Detachment = detachment;
    }

    public void setFamily(String family) {
        Family = family;
    }

    public void setImage(String image) {
        Image = image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeInt(ID);
        parcel.writeString(Title);
        parcel.writeString(Kingdom);
        parcel.writeString(Type);
        parcel.writeString(Class);
        parcel.writeString(Detachment);
        parcel.writeString(Family);
        parcel.writeString(Image);
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return Title;
    }

    public String getKingdom() {
        return Kingdom;
    }

    public String getType() {
        return Type;
    }

    public String getClas() {
        return Class;
    }

    public String getDetachment() {
        return Detachment;
    }

    public String getFamily() {
        return Family;
    }

    public String getImage() {
        return Image;
    }
}
