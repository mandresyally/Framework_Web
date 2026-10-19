package main.java.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

public class MySession {
    private final HttpSession session;

    public MySession(HttpSession session) {
        this.session = session;
    }

    public void add(String cle, Object valeur) {
        session.setAttribute(cle, valeur);
    }

    public Object get(String cle) {
        return session.getAttribute(cle);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String cle, Class<T> type) {
        return (T) session.getAttribute(cle);
    }

    public void delete(String cle) {
        session.removeAttribute(cle);
    }

    public Map<String, Object> getAll() {
        Map<String, Object> valeurs = new HashMap<>();
        Enumeration<String> noms = session.getAttributeNames();
        while (noms.hasMoreElements()) {
            String nom = noms.nextElement();
            valeurs.put(nom, session.getAttribute(nom));
        }
        return valeurs;
    }

    public void invalidate() {
        session.invalidate();
    }
}
