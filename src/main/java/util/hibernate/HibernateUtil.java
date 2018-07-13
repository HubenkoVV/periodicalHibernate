package util.hibernate;

import com.mysql.jdbc.Driver;
import model.entity.Article;
import model.entity.Payment;
import model.entity.Periodical;
import model.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.MySQL5Dialect;

import java.util.Properties;

/**
 * Created by Vladyslava_Hubenko on 7/12/2018.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory = buildSessionfactiry();

    private static SessionFactory buildSessionfactiry(){
        Properties properties = new Properties();
        properties.put(AvailableSettings.DRIVER, Driver.class.getCanonicalName());
        properties.put(AvailableSettings.URL, "jdbc:mysql://localhost:3306/project_db");
        properties.put(AvailableSettings.USER, "root");
        properties.put(AvailableSettings.PASS, "");
        properties.put(AvailableSettings.DIALECT, MySQL5Dialect.class.getCanonicalName());

        Configuration configuration = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Article.class)
                .addAnnotatedClass(Payment.class)
                .addAnnotatedClass(Periodical.class)
                .addAnnotatedClass(User.class);

        configuration.addAttributeConverter(ConverterEnum.class);
        return configuration.buildSessionFactory();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
