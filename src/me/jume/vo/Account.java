package me.jume.vo;


import me.jume.annotation.Column;
import me.jume.annotation.Id;
import me.jume.annotation.Table;

@Table("Account")
public class Account {

    @Id(name="id")
    private int id;
    @Column(name="uname")
    private String uname;
    @Column(name = "password")
    private String password;
    @Column(name="address")
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Account() {
    }

    public Account(String uname, String password, String address) {
        this.id =id;
        this.uname = uname;
        this.password = password;
        this.address = address;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

}
