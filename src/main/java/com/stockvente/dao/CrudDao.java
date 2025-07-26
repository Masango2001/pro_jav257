/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stockvente.dao;

import java.util.List;

/**
 *
 * @author masan
 * @param <T>
 */
public interface CrudDao<T>  {
    void save(T t);
    void update(T t);
    void delete(int id);
    List<T> afficherTous();
}
