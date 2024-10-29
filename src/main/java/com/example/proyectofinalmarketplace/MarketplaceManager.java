package com.example.proyectofinalmarketplace;

public class MarketplaceManager {
    private static Marketplace marketplaceInstance;

    private MarketplaceManager() {
        // Constructor privado para prevenir la instanciación
    }

    public static Marketplace getMarketplaceInstance() {
        if (marketplaceInstance == null) {
            marketplaceInstance = new Marketplace("Eso tilinplace"); // Inicializa con los datos necesarios
        }
        return marketplaceInstance;
    }

    public static void setMarketplaceInstance(Marketplace marketplace) {
        marketplaceInstance = marketplace;
    }
}

