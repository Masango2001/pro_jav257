package com.stockvente.controller;

import com.stockvente.dao.StatisticsDao;
import com.stockvente.utils.DatabaseConnect;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatisticsController {
    private static final Logger LOGGER = Logger.getLogger(StatisticsController.class.getName());
    private final StatisticsDao statsDao;

    public StatisticsController() {
        try {
            Connection connection = DatabaseConnect.getConnection();
            this.statsDao = new StatisticsDao(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'initialisation de StatisticsController : {0}", e.getMessage());
            throw new RuntimeException("Erreur lors de l'initialisation de StatisticsController : " + e.getMessage(), e);
        }
    }

    public int getTotalUtilisateurs() {
        try {
            return statsDao.getTotalUtilisateurs();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur dans getTotalUtilisateurs : {0}", e.getMessage());
            return 0;
        }
    }

    public int getTotalProduits() {
        try {
            return statsDao.getTotalProduits();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur dans getTotalProduits : {0}", e.getMessage());
            return 0;
        }
    }
    
    public int getApprovisionnementsRecents() {
        return statsDao.getApprovisionnementsRecents();
    }

    public int getProduitsEnAlerte() {
        return statsDao.getProduitsEnAlerte();
    }

    public int getTotalApprovisionnementsCeMois() {
        return statsDao.getTotalApprovisionnementsCeMois();
    }

    public int getQuantiteTotaleEnStock() {
        try {
            return statsDao.getQuantiteTotaleEnStock();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur dans getQuantiteTotaleEnStock : {0}", e.getMessage());
            return 0;
        }
    }

    public int getNombreTotalVentes() {
        try {
            return statsDao.getNombreTotalVentes();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur dans getNombreTotalVentes : {0}", e.getMessage());
            return 0;
        }
    }

    public String getNomVendeurLePlusActif() {
        try {
            return statsDao.getNomVendeurLePlusActif();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur dans getNomVendeurLePlusActif : {0}", e.getMessage());
            return "Erreur";
        }
    }

    public String getMagasinierLePlusActif() {
        try {
            return statsDao.getMagasinierLePlusActif();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur dans getMagasinierLePlusActif : {0}", e.getMessage());
            return "Erreur";
        }
    }
}