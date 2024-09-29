package com.example.proyectofinalmarketplace;

import java.util.logging.Logger;

public class Utilities {
    // Instancia única de la clase (para Singleton)
    private static Utilities instanciaUnica = null;

    public static Utilities getInstance() {
        if (instanciaUnica == null) {
            synchronized (Utilities.class) {
                if (instanciaUnica == null) {
                    instanciaUnica = new Utilities();
                }
            }
        }
        return instanciaUnica;
    }

    // Logger que manejará los mensajes
    private static final Logger logger = Logger.getLogger(Utilities.class.getName());
    // Métodos para escribir en el log con diferentes niveles de severidad

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logWarning(String message) {
        logger.warning(message);
    }

    public void logSevere(String message) {
        logger.severe(message);
    }

}
