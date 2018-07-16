package model.entity;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iduser")
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private int money;

    @OneToMany(mappedBy = "idUser")
    private List<Payment> payments;
    @ManyToMany(mappedBy = "users")
    private List<Periodical> periodicals;

    public User() {
    }

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public User setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserRole getRole() {
        return role;
    }

    public User setRole(UserRole role) {
        this.role = role;
        return this;
    }

    public int getMoney() {
        return money;
    }

    public User setMoney(int money) {
        this.money = money;
        return this;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public User setPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }

    @Transactional
    public List<Periodical> getPeriodicals() {
        return periodicals;
    }

    public User setPeriodicals(List<Periodical> periodicals) {
        this.periodicals = periodicals;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + surname.hashCode();
        result = 31 * result + login.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + role.hashCode();
        result = 31 * result + money;
        result = 31 * result + (payments != null ? payments.hashCode() : 0);
        result = 31 * result + (periodicals != null ? periodicals.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", money=" + money +
                '}';
    }
}
