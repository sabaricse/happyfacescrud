package org.happyfaces.security;

import java.util.ArrayList;
import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Ignas
 *
 */
public class CustomUserDetailsService implements UserDetailsService {
    
    private SessionFactory sessionFactory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.happyfaces.domain.User user = (org.happyfaces.domain.User)getSessionFactory().getCurrentSession().createQuery("from User where username=?").setParameter("username", username).uniqueResult();
        if (user != null) {
            UserDetails userDetails = new User(user.getUsername(), user.getPassword(), (Collection<? extends GrantedAuthority>) new ArrayList<GrantedAuthority>());
            return userDetails;
        } else {
            return null;
        }
    }
    
    /**
     * Get Hibernate Session Factory
     * 
     * @return SessionFactory - Hibernate Session Factory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Set Hibernate Session Factory
     * 
     * @param SessionFactory
     *            - Hibernate Session Factory
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
