/**
 * Pet class for CSC212 :: hw3p1
 *
 * @author      Devon Blandin
 * @contact     dblandin@gmail.com
 * @date        7/3/12
 */

public class Pet
{
    private String name;
    private char gender;
    private int age;

    // default constructor.
    // sets 'name' to "--", 'gender' to 'U', and age to -1.
    public Pet()
    {
        this.setName("--");
        this.setGender('U');
        this.setAge(-1);
    }

    // convenience constructor.
    // sets 'name' to name, gender to 'U', and age to -1.
    public Pet(String name)
    {
        this.setName(name);
        this.setGender('U');
        this.setAge(-1);
    }

    // full constructor.
    // sets 'name' to name, 'gender' to gender, and 'age' to age.
    public Pet(String name, char gender, int age)
    {
        this.setName(name);
        this.setGender(gender);
        this.setAge(age);
    }

    // copy constructor.
    // copies name, gender, and age values from pet to new object.
    public Pet(Pet pet)
    {
        this.setName(pet.getName());
        this.setGender(pet.getGender());
        this.setAge(pet.getAge());
    }

    // ACCESSORS

    // returns String name.
    public String getName()
    {
        return this.name;
    }

    // returns char gender.
    public char getGender()
    {
        return this.gender;
    }

    // returns int age.
    public int getAge()
    {
        return this.age;
    }

    // MUTATORS

    // sets 'name' to name if name is not null and not empty,
    // set to "--" otherwise.
    public void setName(String name)
    {
        if (name == null || name.isEmpty())
        {
            this.name = "--";
        }
        else
        {
            this.name = name;
        }
    }

    // sets 'gender' to gender if gender is 'M' or 'F',
    // set to 'U' otherwise.
    public void setGender(char gender)
    {
        if (gender != 'M' && gender != 'F' )
        {
            this.gender = 'U';
        }
        else
        {
            this.gender = gender;
        }
    }

    // sets 'age' to age if age is between 0 and 100 inclusive,
    // set to -1 otherwise.
    public void setAge(int age)
    {
        if (age < 0 || age > 100)
        {
            this.age = -1;
        }
        else
        {
            this.age = age;
        }
    }

    // returns a string representation of the state of the calling Pet object.
    // 10 characters of field name + a colon on left side.
    // 10 characters of the field value on the right side.
    public String toString()
    {
        String s = this.getName() + "\t" + this.getGender() + "\t" + this.getAge();
        return s;
    }

    // returns true if name, gender, and age of pet
    // are equal to name, gender, and age of calling object.
    // returns false otherwise.
    public boolean equals(Pet pet)
    {
        if (this.getName()   == pet.getName() &&
                this.getGender() == pet.getGender() &&
                this.getAge()    == pet.getAge())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    // returns a copy of the calling Pet object
    public Pet clone()
    {
    	Pet pet = new Pet(this.getName(), this.getGender(), this.getAge());
    	return pet;
    }
}