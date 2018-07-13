package model.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpayment")
    private int id;
    @Column(name = "pricePay")
    private int price;

    @ManyToOne
    @JoinColumn(name = "iduser")
    private User idUser;

    @ManyToMany(mappedBy = "payments", fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
    private List<Periodical> periodicals;

    public Payment() {
    }

    public int getId() {
        return id;
    }

    public Payment setId(int id) {
        this.id = id;
        return this;
    }

    public int getPrice() {
        return price;
    }

    public Payment setPrice(int price) {
        this.price = price;
        return this;
    }

    public User getIdUser() {
        return idUser;
    }

    public Payment setIdUser(User idUser) {
        this.idUser = idUser;
        return this;
    }

    public List<Periodical> getPeriodicals() {
        return periodicals;
    }

    public Payment setPeriodicals(List<Periodical> periodicals) {
        this.periodicals = periodicals;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Payment payment = (Payment) o;

        return id == payment.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + price;
        result = 31 * result + periodicals.hashCode();
        return result;
    }
}
