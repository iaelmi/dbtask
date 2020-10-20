package sample;

import java.util.UUID;

public class Patient {
    UUID id;
    String name;
    int age;
    String gender;
    String b_type;
    double weight;
    double height;


    public Patient(UUID id, String name, int age, String gender, String b_type, double weight, double height) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.b_type = b_type;
        this.weight = weight;
        this.height = height;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getB_type() {
        return b_type;
    }

    public void setB_type(String b_type) {
        this.b_type = b_type;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", b_type='" + b_type + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                '}';
    }
}
