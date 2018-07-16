package model.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "periodical")
public class Periodical implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idperiodical")
    private int id;
    @Column(name = "namePer")
    private String name;
    @Column(name = "short_description")
    private String shortDescription;
    @Column(name = "pricePer")
    private int price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_has_periodical",
            joinColumns = { @JoinColumn(name = "idperiodical") },
            inverseJoinColumns = { @JoinColumn(name = "iduser") }
    )
    private List<User> users;

    @OneToMany(mappedBy = "periodicals")
    private List<Payment> payments;

    @OneToMany(mappedBy = "idPeriodical")
    private List<Article> articles;

    public Periodical() {
    }

    public int getId() {
        return id;
    }

    public Periodical setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Periodical setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Periodical setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Periodical setPrice(int price) {
        this.price = price;
        return this;
    }

    public List<User> getUsers() {
        return users;
    }

    public Periodical setUsers(List<User> users) {
        this.users = users;
        return this;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public Periodical setPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public Periodical setArticles(List<Article> articles) {
        this.articles = articles;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Periodical that = (Periodical) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + shortDescription.hashCode();
        result = 31 * result + price;
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (payments != null ? payments.hashCode() : 0);
        result = 31 * result + (articles != null ? articles.hashCode() : 0);
        return result;
    }
}
