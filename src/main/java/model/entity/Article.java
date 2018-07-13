package model.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idarticle")
    private int id;
    @Column(name = "nameArt")
    private String name;
    private String text;

    @ManyToOne
    @JoinColumn(name = "idperiodical")
    private Periodical idPeriodical;
    private LocalDate date;

    public Article() {
    }

    public int getId() {
        return id;
    }

    public Article setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Article setName(String name) {
        this.name = name;
        return this;
    }

    public String getText() {
        return text;
    }

    public Article setText(String text) {
        this.text = text;
        return this;
    }

    public Periodical getIdPeriodical() {
        return idPeriodical;
    }

    public Article setIdPeriodical(Periodical idPeriodical) {
        this.idPeriodical = idPeriodical;
        return this;
    }

    public LocalDate getDate() {
        return date;
    }

    public Article setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        return id == article.id;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + text.hashCode();
        result = 31 * result + date.hashCode();
        return result;
    }
}
