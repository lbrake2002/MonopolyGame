package cosa.models;

import com.j256.ormlite.field.DatabaseField;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public class User {

    @DatabaseField(canBeNull = false)
    @Size(min = 2, max = 20, message = "The users name must be between 2 and 20 characters long")
    private String name; //the user's name
    @DatabaseField(canBeNull = false)
    @Min(value = 18, message = "User must be at least 18 years old.")
    @Max(value = 120, message = "You may have input the wrong age. Please enter an age 120 or less.")
    private int age; //the user's age

    @DatabaseField(generatedId = true)
    private int id; // the auto incremented id of the User from the database


    public User() {
    }

    /**
     * Set all values of the model since we can not do this in the constructor
     *
     * @param name - name of the User
     * @param age  - age of the User
     */
    public void setAllValues(String name, int age) {
        this.setName(name);
        this.setAge(age);
    }

    /***
     * This is workaround to copy values from one object to another
     * @param user - the User object to clone values from
     */
    public void cloneValues(User user) {
        this.setName(user.getName());
        this.setAge(user.getAge());
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

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

